package uk.co.compendiumdev.javafortesters.domain.strings;

public class AsciiStringGenerator {
    private final int from;
    private final int to;

    public AsciiStringGenerator(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public String create() {
        StringBuilder created = new StringBuilder();

        int step = 1;

        if(from > to){
            step = -1;
        }

        boolean generated=false;

        int nextChar = from;

        do{
            char theChar = (char)nextChar;
            created.append(theChar);
            nextChar+=step;

            if(step==-1){
                if(nextChar<to){
                    generated=true;
                }
            }else{
                if(nextChar>to){
                    generated=true;
                }
            }
        }while(!generated);

        return created.toString();
    }
}
