package uk.co.compendiumdev.javafortesters.domain.http.linkchecker;

/**
 * a link to check via http
 */
public class LinkToCheck {

    public static final String STATE_UNKNOWN = "UNKNOWN";
    public static final String STATE_EXISTS = "EXISTS";
    public static final String STATE_NOT_EXISTS = "NOT_EXISTS";

    private final String url;
    private String exists_state;
    private int status=0;

    public LinkToCheck(String aUrl) {
        this.url = aUrl;
        exists_state = STATE_UNKNOWN;
    }

    public String getUrl() {
        return url;
    }

    public String exists() {
        return exists_state;
    }

    public void setExists(boolean exists) {
        if(exists){
            this.exists_state = STATE_EXISTS;
        }else{
            this.exists_state = STATE_NOT_EXISTS;
        }
    }

    public void setExistsFromStatus(int status) {

        this.status = status;

        if(status==200){
            setExists(true);
            return;
        }

        if(status==404){
            setExists(false);
            return;
        }
    }

    public String getState() {
        return exists_state;
    }

    public void setUnknown() {
        this.exists_state = STATE_UNKNOWN;
    }
}
