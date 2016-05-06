package ch.uzh.glapp;

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
    public boolean compareInt (double value, float metric, int function) {

        switch (function) {

            // grater than
            case 1:
                if (metric > value) {
                    System.out.println("Comparison: "+metric+ " is grater than "+value);
                    return true;
                } else {
                    System.out.println("Comparison: "+metric+ " is smaller than "+value);
                    return false;
                }

                // smaller than
            case 2:
                if (metric < value) {
                    System.out.println("Comparison: "+metric+ " is smaller than "+value);
                    return true;
                } else {
                    System.out.println("Comparison: "+metric+ " is grater than "+value);
                    return false;
                }

                // equals
            case 3:
                if (metric == value) {
                    System.out.println("Comparison: "+metric+ " is equal "+value);
                    return true;
                } else {
                    System.out.println("Comparison: "+metric+ " is not equal "+value);
                    return false;
                }
        }
        return false;
    }

}