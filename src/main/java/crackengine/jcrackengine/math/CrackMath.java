package crackengine.jcrackengine.math;

public class CrackMath {
    public static double clamp(final double value, final double min, final double max) {
        return Math.min(Math.max(value, min), max);
    }
    public static int clamp(final int value, final int min, final int max) {
        return Math.min(Math.max(value, min), max);
    }
}
