package PhageEngine;

public class GameMath {

    public static double cap(double value, double min, double max){
        if(value < min){
            return min;
        }

        if(value > max){
            return max;
        }

        return value;
    }

    public static int cap(int value, int min, int max){
        if(value < min){
            return min;
        }

        if(value > max){
            return max;
        }

        return value;
    }

    public static long cap(long value, long min, long max){
        if(value < min){
            return min;
        }

        if(value > max){
            return max;
        }

        return value;
    }

    public static int polarity(int n){
        if (n > 0){
            return 1;
        } else {
            return -1;
        }
    }

    public static double polarity(double n){
        if (n > 0){
            return 1;
        } else {
            return -1;
        }
    }

    public static long polarity(long n){
        if (n > 0){
            return 1;
        } else {
            return -1;
        }
    }
}
