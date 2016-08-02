package ch.uzh.glapp;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.uzh.glapp.model.Violation;
import ch.uzh.glapp.model.sails.MdpTriggerObject;
import ch.uzh.glapp.model.sails.cellinfo.Cell;
import ch.uzh.glapp.model.sails.ruleinfo.Organ;
import ch.uzh.glapp.model.sails.ruleinfo.Rule;
import ch.uzh.glapp.PrometheusRetriever.MetricNotFoundException;

public class MapeUtils {

    // metric is an array with the values of the last time period
    // 1 = greater than, 2 = smaller than, 3 = equal
    public boolean compareArray (int value, int[] metric, int function) {

        int count = 0;
        int goodCount = 0;

        switch (function) {

            // grater than
            case 1:
                for (int i=0; i<metric.length; i++) {
                    if (metric[i] > value) {
                        count ++;
                        goodCount++;
                    } else {
                        count++;
                    }
                }
                if (goodCount/count < 0.8) {
                    return true;
                } else {
                    return false;
                }

            // smaller than
            case 2:
                for (int i=0; i<metric.length; i++) {
                    if (metric[i] < value) {
                        count ++;
                        goodCount++;
                    } else {
                        count++;
                    }
                }
                if (goodCount/count < 0.8) {
                    return true;
                } else {
                    return false;
                }

            // equals
            case 3:
                for (int i=0; i<metric.length; i++) {
                    if (metric[i] == value) {
                        count ++;
                        goodCount++;
                    } else {
                        count++;
                    }
                }
                if (goodCount/count < 0.8) {
                    return true;
                } else {
                    return false;
                }
        }
        return false;
    }


    /**
     * Compute the healthiness value for a given metric value.
     * @param thresholdValue is the threshold value of the rule.
     * @param metricValue is the current value of the metric.
     * @param function is the comparison function (Currently supported: 1 = greater than, 2 = smaller than, 3 = equal)
     * @return the healthiness value. A positive value indicate the current value of the metric is within the defined threshold. 
     */
    public static double cellHealthiness (double thresholdValue, float metricValue, int function) {
    	double degreeOfHealthiness = 0.0;
    	
    	// Formatter for showing degree of healthiness in terms of percentage
    	NumberFormat percentFormatter = NumberFormat.getPercentInstance();
    	percentFormatter.setMaximumFractionDigits(2);

        switch (function) {

            // greater than
            case 1:
            	degreeOfHealthiness = (metricValue - thresholdValue) / thresholdValue;
                if (metricValue > thresholdValue) {
                    System.out.println("Comparison: " + metricValue + " is greater than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                } else {
                    System.out.println("Comparison: " + metricValue + " is smaller than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                }
                return degreeOfHealthiness;

            // smaller than
            case 2:
            	degreeOfHealthiness = -1 * (metricValue - thresholdValue) / thresholdValue;
                if (metricValue < thresholdValue) {
                    System.out.println("Comparison: " + metricValue + " is smaller than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                } else {
                    System.out.println("Comparison: " + metricValue + " is greater than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                }
                return degreeOfHealthiness;

            // equals
            case 3:
                if (metricValue == thresholdValue) {
                    System.out.println("Comparison: " + metricValue + " is equal " + thresholdValue);
                    return 1;
                } else {
                    System.out.println("Comparison: " + metricValue + " is not equal " + thresholdValue);
                    return -1;
                }
        }
        
        return degreeOfHealthiness;
    }


	/**
	 * Get the list of IDs of the cells belonging to a given organ
	 * @param cells is the list of cells in the environment
	 * @param organID is the ID of the organ to which the cells belong  
	 * @return a list of container IDs of the cells belonging to a given organ.
	 */
	public static List<String> getContainerIDs(List<Cell> cells, String organID) {
		List<String> containerIDs = new ArrayList<String>();

		for (Cell cell : cells) {
			if (cell.getOrganId().getId().equals(organID)) {
				containerIDs.add(cell.getContainerId());
//				System.out.println("cell.getContainerId(): " + cell.getContainerId());
			}
		}
		
		return containerIDs;
	}
	
	/**
	 * Compute the healthiness value of an application
	 * @param appId
	 * @param range is the range of time (in seconds) which data are averaged for each metric data point.
	 * @param duration is the duration of time (in seconds) of the data points.
	 * @param step is the time step in seconds that the duration will be broken down.
	 * @param wait is true if mape has to wait for prometheus gathering data.
	 * @return healthiness value of the application specified by appId
	 */
	public static MdpTriggerObject healthiness(String appId, int range, int duration, int step, boolean wait) {

		// waiting a bit for prometheus getting the data of the new container.
		if (wait) {
			try {
				System.out.println("Sleep 15 secs.");
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		SailsRetriever sa = new SailsRetriever();
		PrometheusRetriever prometheusRetriever = new PrometheusRetriever(MainLoop.prometheusServerIP, MainLoop.prometheusServerPort);
		
		// get the cell information
		List<Cell> cells = sa.getCellInfo();
		
		// create maps of container IDs to cell IDs and organ IDs
		HashMap<String, String> containerIDtoCellID = new HashMap<String, String>();
		HashMap<String, String> containerIDtoOrganID = new HashMap<String, String>();
		for (Cell cell : cells) {
			containerIDtoCellID.put(cell.getContainerId(), cell.getId());
			containerIDtoOrganID.put(cell.getContainerId(), cell.getOrganId().getId());
		}
		
		List<Rule> ruleList;
		
    	ruleList = sa.getRules(appId);
    	double totalRuleHealthiness = 0;
    	double appHealthiness;
    	double totalWeight = 0;

		List <Violation> violationList = new ArrayList<Violation>();
		MdpTriggerObject mdpTriggerObj = null;
    	
    	// Compute the healthiness value for each rule
		for (Rule rule : ruleList) {
			double ruleHealthiness = 0;
			double totalCellHealthiness = 0;
			
			String metricName = rule.getMetric();
			List<String> containerIDs = new ArrayList<String>();
			
			System.out.println("Processing rule (ID: " + rule.getId() + ")");
			
			// get the organ that current rule is applicable to
			List<Organ> organs = rule.getOrgans();
    		for (Organ organ : organs) {
    			System.out.println("Applicable organ(s) (ID: " + organ.getId() + ")");
    			
    			// get the ID of corresponding container that belongs to an organ specified by organ ID. Each GLA cell is a Docker container.
    			containerIDs.addAll(MapeUtils.getContainerIDs(cells, organ.getId()));
    		}
    		
    		System.out.println("Applicable cells and corresponding cell IDs:");
			for (String containerID : containerIDs) {
				System.out.println(containerID);
			}
			System.out.println();
			
			double thresholdValue = Double.parseDouble(rule.getValue());
			int function = Integer.parseInt(rule.getOperator()); // 1 = greater than, 2 = smaller than, 3 = equal
			double weight = Double.parseDouble(rule.getWeight());
			totalWeight += weight;

			System.out.println("Rule: Metric: "+ metricName + ", Function: " + function + " (1 = greater than, 2 = smaller than, 3 = equal), Threshold: " + thresholdValue + ", Weight: " + weight + ", Total weight: " + totalWeight);

			// Compute the healthiness value for each cell (Docker container)
			for (int j = 0; j < containerIDs.size(); ++j) {
				// TODO: do not include Proxies
				System.out.println("Computation for cell (container ID: " + containerIDs.get(j) + ") started.");
				float metricValue = 0;
				try {
	    			// Retrieve Prometheus metrics
	    			// NOTE: consider other computation of metric value that may be meaningful
					
					// computation for "memory_utilization"
					if (metricName.equals("memory_utilization")) {
						metricValue = prometheusRetriever.getMetric(containerIDs.get(j), "container_memory_rss", range, duration, step)
								/ prometheusRetriever.getMetric(containerIDs.get(j), "container_spec_memory_limit_bytes", range, duration, step);
					} else { // for other metrics
						metricValue = prometheusRetriever.getMetric(containerIDs.get(j), metricName, range, duration, step);
					}
					System.out.println("Query result (cell metric value): " + metricValue);

					// e.g. a rule specifying threshold = 50% means when the utilization is at 70%, it is (70%-50%)/50% difference above the threshold.
					// degree of healthiness = difference / threshold = 0.2 / 0.5 = 0.4
					double cellHealthiness = cellHealthiness(thresholdValue, metricValue, function);
					totalCellHealthiness += cellHealthiness;

					System.out.print("Comparison result: ");
					if (cellHealthiness < 0) {
						System.out.println("Not compliant, will trigger MDP.");
						String containerID = containerIDs.get(j);
						Violation violation = new Violation(
								containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), appId, rule.getId(), metricName
						);
						violationList.add(violation);

					} else {
						System.out.println("Compliant, proceed to next cell/rule.");
					}
				} catch (MetricNotFoundException e) {
					e.printStackTrace();
				}

    			// TODO: determine if a rule is violated based on the number of violating cells among all the cells   
    			
    			System.out.println();
			}
			
//			System.out.println("totalCellHealthiness: " + totalCellHealthiness + " number of cells: " + cellIDs.size());
			ruleHealthiness = totalCellHealthiness/containerIDs.size();
//			System.out.println("ruleHealthiness: " + ruleHealthiness);
			totalRuleHealthiness += ruleHealthiness * weight;
			
			System.out.println();
		}

        // application healthiness value which is an weighted average of healthiness value of all rules.
		appHealthiness = totalRuleHealthiness / totalWeight;
		System.out.println("Total weight: " + totalWeight);
		System.out.println("Application healthiness value (weighted): " + appHealthiness);
		System.out.println();

		if (violationList.size() > 0) {
			mdpTriggerObj = new MdpTriggerObject(violationList, appHealthiness);
		} else {
			mdpTriggerObj = new MdpTriggerObject(null, appHealthiness);
		}
		
		return mdpTriggerObj;
	}
	
}