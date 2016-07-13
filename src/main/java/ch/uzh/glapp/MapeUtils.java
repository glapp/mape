package ch.uzh.glapp;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ch.uzh.glapp.model.sails.cellinfo.Cell;

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
    public double cellHealthiness (double thresholdValue, float metricValue, int function) {
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
	
}