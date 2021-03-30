package me.clockclap.tct.api;

public final class Range {

    private final double min;
    private final double max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMinimum() {
        return this.min;
    }

    public double getMaximum() {
        return this.max;
    }

    public boolean isIncluded(double value) {
        if(value >= this.min && value <= this.max) {
            return true;
        }
        return false;
    }

}
