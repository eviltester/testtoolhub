package uk.co.compendiumdev.javafortesters.domain.counterstrings;

public class CounterStringValidator {
    private final String counterString;

    public CounterStringValidator(String counterString) {
        this.counterString = counterString;
    }

    public boolean isValid() {

        String[] values = counterString.split("\\*");

        boolean firstOnly = true;

        if(values.length==0){
            return false;
        }

        for(String position : values){
            int positionVal = -1;
            if(position.length()>0){
                positionVal=Integer.parseInt(position);
                positionVal = positionVal-1;
            }else{
                // when first char is an * then check the first char
                if(firstOnly){
                    positionVal=0;
                }
            }
            firstOnly=false;

            try {
                if (counterString.charAt(positionVal) != '*') {
                    System.out.println("Error validating: " + counterString);
                    System.out.println("Position " + position + " is not a delimter it is " + counterString.charAt(positionVal));
                    return false;
                }
            }catch(Exception e){
                System.out.println("Error validating: " + counterString);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
