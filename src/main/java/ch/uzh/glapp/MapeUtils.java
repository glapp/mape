package ch.uzh.glapp;

import java.text.NumberFormat;

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


    // metric is an Integer
    public double ruleHealthiness (double thresholdValue, float metricValue, int function) {
    	double degreeOfHealthiness = 0.0;
    	
    	// Formatter for showing degree of healthiness in terms of percentage
    	degreeOfHealthiness = -1 * (metricValue - thresholdValue) / thresholdValue;
    	NumberFormat percentFormatter = NumberFormat.getPercentInstance();
    	percentFormatter.setMaximumFractionDigits(2);

        switch (function) {

            // grater than
            case 1:
            	degreeOfHealthiness = (metricValue - thresholdValue) / thresholdValue;
                if (metricValue > thresholdValue) {
                    System.out.println("Comparison: " + metricValue + " is grater than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                } else {
                    System.out.println("Comparison: " + metricValue + " is smaller than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                }
                return degreeOfHealthiness;

            // smaller than
            case 2:


                if (metricValue < thresholdValue) {
                    System.out.println("Comparison: " + metricValue + " is smaller than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
                } else {
                    System.out.println("Comparison: " + metricValue + " is grater than " + thresholdValue + " by " + percentFormatter.format(degreeOfHealthiness));
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

}