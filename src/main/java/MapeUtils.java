
public class MapeUtils {

    public boolean compare (int value, int metric, int function) {

        switch (function) {

            // grater than
            case 1:     if (metric > value) {
                            return true;
                        } else {
                            // do something
                        } break;

            // smaller than
            case 2:     if (metric < value) {
                            return true;
                        } else {
                            // do something
                        } break;

            // equals
            case 3:     if (metric == value) {
                            return true;
                        } else {
                            // do something
                        } break;
        }
        return false;
    }
}