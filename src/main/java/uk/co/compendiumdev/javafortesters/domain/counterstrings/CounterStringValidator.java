package uk.co.compendiumdev.javafortesters.domain.counterstrings;

public class CounterStringValidator {
    private final String counterString;

    public CounterStringValidator(String counterString) {
        this.counterString = counterString;
    }

    // a very crude algorithm, can be easily fooled e.g. 1*******
    public boolean isValid() {

        String[] values = counterString.split("\\*");

        for(String position : values){
            int positionVal = 0;
            if(position.length()>0){
                positionVal=Integer.parseInt(position);
                positionVal = positionVal-1;
            }
            if(counterString.charAt(positionVal)!='*'){
                System.out.println("Error validating: " + counterString);
                System.out.println("Position " + position + " is not a delimter it is " + counterString.charAt(positionVal));
                return false;
            }
        }
        return true;
    }
}
