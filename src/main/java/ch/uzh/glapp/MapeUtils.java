package ch.uzh.glapp;

import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import ch.uzh.glapp.model.Violation;
import ch.uzh.glapp.model.sails.MdpTriggerObject;
import ch.uzh.glapp.model.sails.cellinfo.Cell;
import ch.uzh.glapp.model.sails.hostinfo.Host;
import ch.uzh.glapp.model.sails.ruleinfo.Organ;
import ch.uzh.glapp.model.sails.ruleinfo.Rule;
import ch.uzh.glapp.PrometheusRetriever.MetricNotFoundException;

import static ch.uzh.glapp.mdp.MapeWorld.*;

public class MapeUtils {
	
	private static List<Host> hosts = null;
	private static double lastGetHostInfo = 0;
	private static final int MIN_POLLING_INTERVAL = 30;

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
	 * Function to check if the new tier is higher than the old tier
	 * @param oldTier is the old tier of the host
	 * @param newTier is the new tier of the host
	 * @return true if the new tier is higher than the old tier and false otherwise.
	 */
	public static boolean isNewTierHigher(String oldTier, String newTier) {
		if (oldTier.equals(TIER1) && (newTier.equals(TIER2) || newTier.equals(TIER3))) {
			return true;
		} else if (oldTier.equals(TIER2) && newTier.equals(TIER3)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Function to check if the new tier is higher than or equal to the old tier
	 * @param oldTier is the old tier of the host
	 * @param newTier is the new tier of the host
	 * @return true if the new tier is higher than the old tier and false otherwise.
	 */
	public static boolean isNewTierHigherOrEqual(String oldTier, String newTier) {
		if (oldTier.equals(TIER1)) {
			return true;
		} else if (oldTier.equals(TIER2) && (newTier.equals(TIER2) || newTier.equals(TIER3))) {
			return true;
		} else if (oldTier.equals(TIER3) && (newTier.equals(TIER3))){
			return true;
		} else {
			return false;
		}
	}
	
    /**
	 * Function to check if the new tier is lower than the old tier
	 * @param oldTier is the old tier of the host
	 * @param newTier is the new tier of the host
	 * @return true if the new tier is lower than the old tier and false otherwise.
	 */
	public static boolean isNewTierLower(String oldTier, String newTier) {
		if (oldTier.equals(TIER3) && (newTier.equals(TIER2) || newTier.equals(TIER1))) {
			return true;
		} else if (oldTier.equals(TIER2) && newTier.equals(TIER1)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Function to check if a host from given cloud provider, region and tier is available
	 * @param provider is the new provider
	 * @param region
	 * @param tier
	 * @return true if the host is available and false otherwise
	 */
	public static boolean isHostAvailable(String provider, String region, String tier) {
		double currentTime = System.currentTimeMillis()/1000;
		
		// Retrieve information from sails at most once every 30 seconds
		if (hosts == null || ((currentTime - lastGetHostInfo) > MIN_POLLING_INTERVAL)) {
			SailsRetriever sa = new SailsRetriever();
			hosts = sa.getHostInfo();
			lastGetHostInfo = currentTime;
		}
		
		for (Host host : hosts) {
			if (host.getLabels().getProvider().equals(provider) && host.getLabels().getRegion().equals(region) && host.getLabels().getTier().equals(tier)){
//				System.out.println("Host at " + provider + ", " + region + ", " + tier + " is available");
				return true;
			}
		}
		
//		System.out.println("Host at " + provider + ", " + region + ", " + tier + " is not available");
		return false;
	}
	
	/**
	 * Find a server of which the tier is lower than the current tier
	 * @param currentTier
	 * @return a Host object containing the information of the server
	 */
	public static Host findLowerTierServer(String currentTier) {
		double currentTime = System.currentTimeMillis()/1000;
		
		// Retrieve information from sails at most once every 30 seconds
		if (hosts == null || ((currentTime - lastGetHostInfo) > MIN_POLLING_INTERVAL)) {
			SailsRetriever sa = new SailsRetriever();
			hosts = sa.getHostInfo();
			lastGetHostInfo = currentTime;
		}
		
		for (Host host : hosts) {
			if (isNewTierLower(currentTier, host.getLabels().getTier())) {
				return host;
			}
		}
		
		return null;
	}
	
	/**
	 * Find a host in a specified region
	 * @param region is the region that the host need to reside on
	 * @return a Host object containing the information of the host
	 */
	public static Host findHostInRegion(String region) {
		double currentTime = System.currentTimeMillis()/1000;
		
		// Retrieve information from sails at most once every 30 seconds
		if (hosts == null || ((currentTime - lastGetHostInfo) > MIN_POLLING_INTERVAL)) {
			SailsRetriever sa = new SailsRetriever();
			hosts = sa.getHostInfo();
			lastGetHostInfo = currentTime;
		}
		
		for (Host host : hosts) {
			if (host.getLabels().getRegion().equals(region) && host.getLabels().getProvider().equals(DO)) {
				return host;
			}
		}
		
		System.out.println("MapeUtils: findHostInRegion(): cannot find any host in the specified region.");
		return null;
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
	 * Get the list of IDs of the cells belonging to a given organ. Proxy cells are excluded.
	 * @param cells is the list of cells in the environment
	 * @param organID is the ID of the organ to which the cells belong  
	 * @return a list of container IDs of the cells belonging to a given organ. (without proxy cells)
	 */
	public static List<String> getContainerIDs(List<Cell> cells, String organID) {
		List<String> containerIDs = new ArrayList<String>();

		for (Cell cell : cells) {
			if (cell.getOrganId().getId().equals(organID) && !cell.getIsProxy()) {
				containerIDs.add(cell.getContainerId());
//				System.out.println("cell.getContainerId(): " + cell.getContainerId());
			}
		}
		
		return containerIDs;
	}
	
	/**
	 * Compute the healthiness value of an application
	 * @param appId is the ID of the application
	 * @param range is the range of time (in seconds) which data are averaged for each metric data point.
	 * @param duration is the duration of time (in seconds) of the data points.
	 * @param step is the time step in seconds that the duration will be broken down.
	 * @param wait is true if MAPE has to wait for Prometheus gathering data.
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
		HashMap<String, String> containerIDtoProvider = new HashMap<String, String>();
		HashMap<String, String> containerIDtoRegion = new HashMap<String, String>();
		HashMap<String, String> containerIDtoTier = new HashMap<String, String>();
		for (Cell cell : cells) {
			containerIDtoCellID.put(cell.getContainerId(), cell.getId());
			containerIDtoOrganID.put(cell.getContainerId(), cell.getOrganId().getId());
			containerIDtoProvider.put(cell.getContainerId(), cell.getHost().getLabels().getProvider());
			containerIDtoRegion.put(cell.getContainerId(), cell.getHost().getLabels().getRegion());
			containerIDtoTier.put(cell.getContainerId(), cell.getHost().getLabels().getTier());
		}
		
		List<Rule> ruleList;
		
    	ruleList = sa.getRules(appId);
    	double totalRuleHealthiness = 0;
    	double appHealthiness;
    	double totalWeight = 0;

		List <Violation> overallViolationList = new ArrayList<Violation>();
		MdpTriggerObject mdpTriggerObj = null;
    	
		if (!ruleList.isEmpty()) {
	    	// Compute the healthiness value for each rule
			for (Rule rule : ruleList) {
				double ruleHealthiness = 0;
				double totalCellHealthiness = 0;
				int numOfCellsInRule = 0; // number of cells applicable to a specific rule
				int numOfViolatedCellsInRule = 0; // number of violated cells applicable to a specific rule
				double ruleViolationRatio = 0; // indicate ratio of the cells that violated a specific rule
				boolean costViolation = false;
				
				List <Violation> ruleViolationList = new ArrayList<Violation>();
				
				String metricName = rule.getMetric();
				List<String> containerIDs = new ArrayList<String>();
				
				System.out.println("Processing rule (Rule ID: " + rule.getId() + ")");
				
				// get the organ that current rule is applicable to
				List<Organ> organs = rule.getOrgans();
	    		for (Organ organ : organs) {
//	    			System.out.println("Applicable organ(s) (Organ ID: " + organ.getId() + ")");
	    			
	    			// get the ID of corresponding container that belongs to an organ specified by organ ID.
	    			// Each GLA cell is a Docker container.
	    			containerIDs.addAll(MapeUtils.getContainerIDs(cells, organ.getId()));
	    		}
	    		
	    		numOfCellsInRule = containerIDs.size();
	    		
	    		// check if the number of cells is 0 (i.e. the list "containerIDs" is empty)
//	    		if (numOfCellsInRule == 0) {
//	    			System.out.println("MapeUtils.healthiness(): number of cells in rule is 0.");
//	    		}
	    		
//	    		System.out.println("Applicable cell(s) and corresponding container ID(s):");
//				for (String containerID : containerIDs) {
//					System.out.println(containerID);
//				}
//				System.out.println();

				double thresholdValue = Double.parseDouble(rule.getValue());
				int function = Integer.parseInt(rule.getOperator()); // 1 = greater than, 2 = smaller than, 3 = equal
				double weight = Double.parseDouble(rule.getWeight()); // weight of the current rule
				totalWeight += weight;
	
				System.out.println("Rule: Metric: "+ metricName + ", Function: " + function + " (1 = greater than, 2 = smaller than, 3 = equal), Threshold: " + thresholdValue + ", Weight: " + weight + ", Total weight: " + totalWeight);
				
				if (metricName.equals("cost")) {
					double totalCost = 0;
					
					for (int j = 0; j < containerIDs.size(); ++j) {
						String containerID = containerIDs.get(j);
						
//						System.out.println("Querying cost metric for cell (container ID: " + containerID + ")");
						
						try {
							totalCost += prometheusRetriever.getCostMetric(getPrometheusMetricName(containerIDtoProvider.get(containerID), containerIDtoRegion.get(containerID), containerIDtoTier.get(containerID)));
							
							Violation violation = new Violation(containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), 
									appId, rule.getId(), metricName, 0);
							ruleViolationList.add(violation);
						} catch (MetricNotFoundException e) {
							e.printStackTrace();
						}
					}
					
					System.out.println("Total cost: " + totalCost + " / threshold: " + thresholdValue);
					
					ruleHealthiness = (thresholdValue - totalCost) / thresholdValue;
					if (ruleHealthiness < 0) {
						costViolation = true;
						
						for (Violation v : ruleViolationList) {
							v.setWeightedHealthiness(ruleHealthiness*weight);
						}
						
						// if there is a cost violation, add all applicable cells into the overall violation list
						overallViolationList.addAll(ruleViolationList);
					} else {
						costViolation = false;
					}
				} else if (metricName.substring(0, 11).equals("click_count")) { // processing click count metric
//					Properties clickCountTestInput = new Properties();
//				    try {
//					    FileInputStream clickCountTestInputFile = new FileInputStream("clickCount.txt");
//					    clickCountTestInput.load(clickCountTestInputFile);
//					    clickCountTestInputFile.close();
//				    } catch (Exception e) {
//					    e.printStackTrace();
//				    }
					
					try {
						float clickCountUS = prometheusRetriever.getCustomMetric(metricName.concat("_us"), 60, 60, 60);
						float clickCountEU = prometheusRetriever.getCustomMetric(metricName.concat("_eu"), 60, 60, 60);
						
						// Load the value from an input file for testing
//						if (clickCountTestInput.getProperty("clickCountUS") != null) {
//							clickCountUS = Integer.parseInt(clickCountTestInput.getProperty("clickCountUS"));
//						}
//						if (clickCountTestInput.getProperty("clickCountEU") != null) {
//							clickCountEU = Integer.parseInt(clickCountTestInput.getProperty("clickCountEU"));
//						}
						
						int numOfCellsUS = 0;
						int numOfCellsEU = 0;
						
						for (String containerID : containerIDs) {
							if (containerIDtoRegion.get(containerID).equals(US)) {
								numOfCellsUS++;
							} else if (containerIDtoRegion.get(containerID).equals(EU)) {
								numOfCellsEU++;
							}
						}
						
						double ratioUS = 0;
						double ratioEU = 0;
						
						if (numOfCellsUS == 0 && clickCountUS > 0) {
							// if there no cell in a region and there is more than 0 click, ratio is positive infinity (regarded as a violation)
							ratioUS = Double.POSITIVE_INFINITY;
						} else if (numOfCellsUS > 0) {
							ratioUS = clickCountUS / numOfCellsUS;
						}
						if (numOfCellsEU == 0 && clickCountEU > 0) {
							// if there no cell in a region and there is more than 0 click, ratio is positive infinity (regarded as a violation)
							ratioEU = Double.POSITIVE_INFINITY;
						} else if (numOfCellsEU > 0) {
							ratioEU = clickCountEU / numOfCellsEU;
						}
						
						System.out.println("US: new clicks: " + clickCountUS + " / number of cells: " + numOfCellsUS + " = ratio: " + ratioUS);
						System.out.println("EU: new clicks: " + clickCountEU + " / number of cells: " + numOfCellsEU + " = ratio: " + ratioEU);

						if (ratioUS == Double.POSITIVE_INFINITY) {
							int i = 0;
							while (ruleViolationList.isEmpty()) {
								String containerID = containerIDs.get(i);
								
								if (containerIDtoRegion.get(containerID).equals(EU)) {
									Violation violation = new Violation(containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), 
											appId, rule.getId(), metricName, -1, ratioUS);
									ruleViolationList.add(violation);
								}
								++i;
							}
						} else if ((ratioUS == 0 && numOfCellsUS > 0 && numOfCellsEU > 0) || ratioUS > 500 || (ratioUS < 100 && numOfCellsUS > 1)) {
							for (int j = 0; j < containerIDs.size(); ++j) {
								String containerID = containerIDs.get(j);
								
								if (containerIDtoRegion.get(containerID).equals(US)) {
									Violation violation = new Violation(containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), 
											appId, rule.getId(), metricName, -1, ratioUS);
									ruleViolationList.add(violation);
								}
							}
						}
						
						if (ratioEU == Double.POSITIVE_INFINITY) {
							int i = 0;
							while (ruleViolationList.isEmpty()) {
								String containerID = containerIDs.get(i);
								
								if (containerIDtoRegion.get(containerID).equals(US)) {
									Violation violation = new Violation(containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), 
											appId, rule.getId(), metricName, -1, ratioEU);
									ruleViolationList.add(violation);
								}
								++i;
							}
						} else if ((ratioEU == 0 && numOfCellsUS > 0 && numOfCellsEU > 0) || ratioEU > 500 || (ratioEU < 100 && numOfCellsEU > 1)) {
							for (int j = 0; j < containerIDs.size(); ++j) {
								String containerID = containerIDs.get(j);
								
								if (containerIDtoRegion.get(containerID).equals(EU)) {
									Violation violation = new Violation(containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), 
											appId, rule.getId(), metricName, -1, ratioEU);
									ruleViolationList.add(violation);
								}
							}
						}
						
						overallViolationList.addAll(ruleViolationList);
						
//						printViolation(overallViolationList);
						
					} catch (MetricNotFoundException e) {
						e.printStackTrace();
					}
				}
				else {
					// Compute the healthiness value for each cell (Docker container)
					for (int j = 0; j < containerIDs.size(); ++j) {
						String containerID = containerIDs.get(j);
						System.out.println("Computation for cell (container ID: " + containerID + ") started.");
						float metricValue = 0;
						try {
							// Retrieve Prometheus metrics
	
							// computation for "memory_utilization"
							if (metricName.equals("memory_utilization")) {
								metricValue = prometheusRetriever.getMetric(containerID, "container_memory_rss", range, duration, step)
										/ prometheusRetriever.getMetric(containerID, "container_spec_memory_limit_bytes", range, duration, step);
							} else { // for other metrics
								metricValue = prometheusRetriever.getMetric(containerID, metricName, range, duration, step);
							}
//							System.out.println("Query result (cell metric value): " + metricValue);
							
							// For "container_cpu_usage_seconds_total" metric, the threshold is converted from percentage representation to a number in [0,1]
//							if (metricName.equals("container_cpu_usage_seconds_total")) {
//								thresholdValue/=100;
//							}
	
							// e.g. a rule specifying threshold = 50% means when the utilization is at 70%,
							// it is (70%-50%)/50% difference above the threshold.
							// degree of healthiness = difference / threshold = 0.2 / 0.5 = 0.4
							// a positive value means healthy and a negative value means unhealthy
							double cellHealthiness = cellHealthiness(thresholdValue, metricValue, function);
							totalCellHealthiness += cellHealthiness;
	
							System.out.print("Comparison result: ");
							if (cellHealthiness < 0) {
								// increment the counter if a cell if found to be violating a specific rule
								numOfViolatedCellsInRule += 1;
	
								System.out.println("Not compliant, current number of violating cell: " + numOfViolatedCellsInRule);
								Violation violation = new Violation(
										containerIDtoCellID.get(containerID), containerID, containerIDtoOrganID.get(containerID), 
										appId, rule.getId(), metricName, cellHealthiness*weight
										);
								ruleViolationList.add(violation);
							} else {
								System.out.println("Compliant, proceed to next cell/rule.");
							}
						} catch (MetricNotFoundException e) {
							e.printStackTrace();
						}
	
						System.out.println();
					}
					
	//				System.out.println("totalCellHealthiness: " + totalCellHealthiness + " number of cells: " + cellIDs.size());
					ruleHealthiness = totalCellHealthiness/containerIDs.size();
					
					ruleViolationRatio = numOfViolatedCellsInRule/numOfCellsInRule;
					System.out.println("ruleViolationRatio: " + ruleViolationRatio);
					
					// determine if a rule is violated based on the number of violating cells among all the cells   
					// define the threshold to determine if a rule is violated (percentage of cells violated the rule)
					// using a pre-defined threshold of 0.25 (i.e. more than 25% of applicable cells violated the rule)
					// e.g. if 0.25 (25%) of the cells violated the rule, the rule is not considered violated
					if (ruleViolationRatio > 0.25) {
						// add the violation list of a specific rule to the overall violation list
						overallViolationList.addAll(ruleViolationList);
					}
				}
	
				System.out.println("ruleHealthiness: " + ruleHealthiness);
				totalRuleHealthiness += ruleHealthiness * weight;
	
				System.out.println();
			}
			
			// application healthiness value which is an weighted average of healthiness value of all rules.
			appHealthiness = totalRuleHealthiness / totalWeight;
			System.out.println("Total weight: " + totalWeight);
			System.out.println("Application healthiness value (weighted): " + appHealthiness);
			System.out.println();

//			System.out.println(overallViolationList.size());

			if (overallViolationList.size() > 0) {
				mdpTriggerObj = new MdpTriggerObject(overallViolationList, appHealthiness, true);
//				System.out.println("here");
			} else {
				mdpTriggerObj = new MdpTriggerObject(null, appHealthiness, false);
			}
			
			return mdpTriggerObj;
		} else {
			// update: when deleting all rule in the voting app it should be checked in MainLoop
			// line 88: if (sa.getRules(appId).isEmpty())
			// it should not be able to reach this part
			// in addition,
			// Exception in thread "main" java.lang.NullPointerException
			// at ch.uzh.glapp.mdp.MapeEnvironment.executeAction(MapeEnvironment.java:121)
			// refers to the following line (master branch, commit 0937797): MapeUtils mapeUtils = new MapeUtils();
			
			// TODO: when deleting all rules in the voting app it enters in this else.
			// This results in a NULL pointer exception:
			// Exception in thread "main" java.lang.NullPointerException
			// at ch.uzh.glapp.mdp.MapeEnvironment.executeAction(MapeEnvironment.java:121)
			// at burlap.behavior.singleagent.learning.tdmethods.QLearning.runLearningEpisode(QLearning.java:449)
			// at ch.uzh.glapp.mdp.BasicBehaviorMape.MyQLearningFunc(BasicBehaviorMape.java:55)
			// at ch.uzh.glapp.MainLoop.main(MainLoop.java:136)

			System.out.println("MapeUtils.healthiness(): No rule defined.");
			return null;
		}
	}
	
	/**
	 * Return the string representation of the cost metric of a specified cloud server
	 * @param provider is the cloud provider of the server
	 * @param region is the region that the server resides
	 * @param tier is the tier of the server
	 * @return the string representation of the cost metric of a specified cloud server
	 */
	private static String getPrometheusMetricName(String provider, String region, String tier) {
		String prometheusMetricName = "cost_" + provider + "_" + region + "_tier" + tier;
		return prometheusMetricName;
	}
	
	/**
	 * Merge sort the violations according to its healthiness value
	 * @param violations is the input violation list
	 * @return violations sorted in ascending order of its healthiness value
	 */
	public static List<Violation> mergeSort(List<Violation> violations) {
		if (violations.size() <= 1) {
			return violations;
		}
		
		List<Violation> first = new ArrayList<Violation>(violations.subList(0, violations.size() / 2));
		List<Violation> second = new ArrayList<Violation>(violations.subList(violations.size() / 2, violations.size()));
		
		mergeSort(first);
		mergeSort(second);
		
		merge(first, second, violations);
		return violations;
	}
	
	/**
	 * Merge function of the merge sort
	 * @param first is the first half of the list to be merge
	 * @param second is the first second of the list to be merge
	 * @param result is the merged list
	 */
	private static void merge(List<Violation> first, List<Violation> second, List<Violation> result) {
		int iFirst = 0;
		int iSecond = 0;
		int iMerged = 0;
		
		while (iFirst < first.size() && iSecond < second.size()) {
			if (first.get(iFirst).getWeightedHealthiness() < second.get(iSecond).getWeightedHealthiness()) {
				result.set(iMerged, first.get(iFirst));
				++iFirst;
			} else {
				result.set(iMerged, second.get(iSecond));
				++iSecond;
			}
			++iMerged;
			
			result.subList(iMerged, result.size()).clear();
			
			if (iFirst < first.size()) {
				result.addAll(iMerged, first.subList(iFirst, first.size()));
			}
			
			if (iSecond < second.size()) {
				result.addAll(iMerged, second.subList(iSecond, second.size()));
			}
		}
	}
	
	/**
	 * Output the list of violations
	 * @param violations
	 */
	public static void printViolation(List<Violation> violations) {
		System.out.println("\nList of violations:");
		
		int count = 0;
		
		if (violations.size() > 0) {
			for (Violation violation : violations) {
				System.out.println("====================");
				System.out.println("Violation " + count);
				System.out.println("--------------------");
				System.out.println("Organ ID: " + violation.getOrganId() + "\nCell ID: " + violation.getCellId() + "\nContainer ID: " + violation.getContainerId() + "\nMetric: " + violation.getMetric());
				System.out.println("====================");
				count++;
			}
			System.out.println();
		} else {
			System.out.println("List of violation is empty.\n");
		}
	}
	
	public static HashMap<String, Double> getAllCost() {
		PrometheusRetriever prometheusRetriever = new PrometheusRetriever(MainLoop.prometheusServerIP, MainLoop.prometheusServerPort);
		
		HashMap<String, Double> costMap = new HashMap<String, Double>();
		
		for (String provider : PROVIDER_LIST) {
			for (String region : REGION_LIST) {
				for (String tier : TIER_LIST) {
					String metricName = getPrometheusMetricName(provider, region, tier);
					try {
						double cost = prometheusRetriever.getCostMetric(metricName);
						costMap.put(metricName, cost);
					} catch (MetricNotFoundException e) {
						costMap.put(metricName, null);
					}
				}
			}
		}
	
//		for (Entry<String, Double> entry : costMap.entrySet()) {
//			System.out.println(entry.getKey() + ": " + entry.getValue());
//		}
		
		return costMap;
	}
	
	public static String findCheapestServer(String currentProvider, String currentRegion, String currentTier) {
		PrometheusRetriever prometheusRetriever = new PrometheusRetriever(MainLoop.prometheusServerIP, MainLoop.prometheusServerPort);
		
		HashMap<String, Double> costMap = new HashMap<String, Double>();
		
		String cheapestServer = null;
		
		try {
			// initialize the cheapest cost with current cost
			double cheapestCost = prometheusRetriever.getCostMetric(getPrometheusMetricName(currentProvider, currentRegion, currentTier));
			
			for (String provider : PROVIDER_LIST) {
				for (String region : REGION_LIST) {
					for (String tier : TIER_LIST) {
						if (isHostAvailable(provider, region, tier) && ((!provider.equals(currentProvider) || !region.equals(currentRegion) || !tier.equals(currentTier)))) {
							String metricName = getPrometheusMetricName(provider, region, tier);
							double cost = prometheusRetriever.getCostMetric(metricName);
	
							if (cost < cheapestCost) {
								cheapestCost = cost;
								cheapestServer = metricName;
							}
						}
					}
				}
			}
		} catch (MetricNotFoundException e) {
		}
	
//		for (Entry<String, Double> entry : costMap.entrySet()) {
//			System.out.println(entry.getKey() + ": " + entry.getValue());
//		}
		
		return cheapestServer;
	}
	
	/**
	 * Get the organ ID of a specified cell
	 * @param cellID is the cell ID of the specified cell
	 * @return organ ID of the specified cell
	 */
	public static String cellIDToOrganID(String cellID) {
		SailsRetriever sa = new SailsRetriever();
		List<Cell> cells = sa.getCellInfo();
		
		for (Cell cell : cells) {
			if (cell.getId().equals(cellID)) {
				return cell.getOrganId().getId();
			}
		}
		
		System.out.println("MapeUtils: cellIDToOrganID(): cannot find the specified cell.");
		return null;
	}
}