package uk.co.compendiumdev.javafortesters.binarychopifier;

import java.util.List;

/**
 * Created by Alan on 27/11/2016.
 */
public class BinaryChopResults {

    private final int endValue;
    private final int startValue;
    private final List<Integer> chopPoints;

    public BinaryChopResults(int startValue, int endValue, List<Integer> chopPoints) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.chopPoints = chopPoints;
    }

    public int getStart() {
        return startValue;
    }

    public int getEnd() {
        return endValue;
    }

    public int countChopPoints() {
        return chopPoints.size();
    }

    public int getChopPoint(int chopPointIndex) {
        return chopPoints.get(chopPointIndex-1);
    }

    public int getChopPointDiff(int chopPointIndex) {
        return endValue - getChopPoint(chopPointIndex);
    }
}
