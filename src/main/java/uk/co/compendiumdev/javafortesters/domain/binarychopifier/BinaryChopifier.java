package uk.co.compendiumdev.javafortesters.domain.binarychopifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 27/11/2016.
 */
public class BinaryChopifier {
    public BinaryChopResults chop(int startValue, int endValue) {

        int diff = endValue-startValue;
        List<Integer> chopPoints = new ArrayList();

        while(diff > 0){
            diff = diff/2;
            chopPoints.add(endValue-diff);
        }

        return new BinaryChopResults(startValue, endValue, chopPoints);


    }

    public BinaryChopResults chop(int endValue) {
        return chop(endValue/2, endValue);
    }
}
