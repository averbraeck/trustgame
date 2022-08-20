package org.transsonic.trustgame;

import java.io.Serializable;

/**
 * Order info record.
 */
public class OrderInfo implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;
    
    private String show = "none";
    private String header = "";
    private String body = "";
    
    public String getShow() {
        return show;
    }
    public void setShow(String show) {
        this.show = show;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

}
