package uk.co.compendiumdev.javafortesters.domain.binarychopifier;

/**
 * Created by Alan on 27/11/2016.
 */
public class BinaryChopReporter {
    private final BinaryChopResults binaryChop;

    public BinaryChopReporter(BinaryChopResults binaryChop) {
        this.binaryChop = binaryChop;
    }

    public String getStringReport() {
        StringBuilder theReport = new StringBuilder();

        theReport.append(String.format("Start: %d%n", binaryChop.getStart()));
        theReport.append(String.format("End: %d%n", binaryChop.getEnd()));
        theReport.append(String.format("%n", binaryChop.getEnd()));
        theReport.append(String.format("Chop: value (diff)%n"));
        theReport.append(String.format("------------------%n"));

        for(int x=1; x<=binaryChop.countChopPoints();x++) {
            theReport.append(String.format("%d: %d (%d)%n", x, binaryChop.getChopPoint(x), binaryChop.getChopPointDiff(x)));
        }

        return theReport.toString();
    }
}
