package ch.uzh.glapp;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
	 * @return a list of cell IDs of the cells belonging to a given organ. Cell IDs refer to container IDs in Docker.
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
	 * @return healthiness value of the application specified by appId
	 */
	public static double healthiness(String appId, int range, int duration) {
		SailsRetriever sa = new SailsRetriever();
		PrometheusRetriever prometheusRetriever = new PrometheusRetriever(MainLoop.prometheusServerIP, MainLoop.prometheusServerPort);
		
		List<Rule> ruleList;
    	ruleList = sa.getRules(appId);
    	double totalRuleHealthiness = 0;
    	double appHealthiness;
    	double totalWeight = 0;
    	
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
    			List<Cell> cells = sa.getCellInfo();
    			
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

			System.out.println("Rule: Metric: "+ metricName + ", Function: " + function + " (1 = greater than, 2 = smaller than, 3 = equal), Threshold: " + thresholdValue);

			// Compute the healthiness value for each cell (Docker container)
			for (int j = 0; j < containerIDs.size(); ++j) {
				System.out.println("Computation for cell (container ID: " + containerIDs.get(j) + ") started.");
				float metricValue = 0;
				try {
	    			// Retrieve Prometheus metrics
	    			// NOTE: consider other computation of metric value that may be meaningful
					metricValue = prometheusRetriever.getMetric(containerIDs.get(j), metricName, range, duration);
				} catch (MetricNotFoundException e) {
					e.printStackTrace(); // TODO: handle exception
				}  
    			System.out.println("Query result (cell metric value): " + metricValue);

    			// e.g. a rule specifying threshold = 50% means when the utilization is at 70%, there will be a 20% difference above threshold.
    			// degree of healthiness = difference / threshold = 0.2 / 0.5 = 0.4
    			double cellHealthiness = cellHealthiness(thresholdValue, metricValue, function);
    			totalCellHealthiness += cellHealthiness;
    			
    			System.out.print("Comparison result: ");
    			if (cellHealthiness < 0) {
    				System.out.println("Not compliant, will trigger MDP.");
    				MainLoop.ruleViolated = true;
    			} else {
    				System.out.println("Compliant, proceed to next cell/rule.");
    			}
    			
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
		System.out.println("Application healthiness value (weighted): " + appHealthiness);
		System.out.println();
		
		return appHealthiness;
	}
	
}