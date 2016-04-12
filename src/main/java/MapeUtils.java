public class MapeUtils {

    // metric is an array with the values of the last time period
    public boolean compareArray (int value, int[] metric, int function) {

        switch (function) {

            // grater than
            case 1:
                for (int i=0; i<metric.length; i++) {
                    if (metric[i] > value) {
                        return true;
                    }
                }
            // smaller than
            case 2:
                for (int j=0; j<metric.length; j++) {
                    if (metric[j] < value) {
                        return true;
                    }
                }

            // equals
            case 3:
                for (int k=0; k<metric.length; k++) {
                    if (metric[k] == value) {
                        return true;
                    }
                }
        }
        return false;
    }


    // metric is an array with the values of the last time period
    public boolean compareInt (int value, int metric, int function) {

        switch (function) {

            // grater than
            case 1:
                if (metric > value) {
                    return true;
                }

                // smaller than
            case 2:
                if (metric < value) {
                    return true;
                }

                // equals
            case 3:
                if (metric == value) {
                    return true;
                }
        }
        return false;
    }

}