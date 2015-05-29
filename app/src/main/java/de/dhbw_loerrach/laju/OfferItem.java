package de.dhbw_loerrach.laju;

import java.io.Serializable;

/**
 * Created by Frederik on 29.05.2015.
 */
public class OfferItem  implements Serializable {
    private final String erdat;
    private final boolean open;
    private final boolean done;
    private final int tauschid;
    private final String title;
    private final String username;
    private final String text;
    private final String userfirstname;
    private final String userlastname;
    private final int userid;

    public OfferItem(int tauschid, String title, String text, String username, String userfirstname, String userlastname, int userid, boolean done, boolean open, String erdat) {
        this.tauschid = tauschid;
        this.title = title;
        this.text = text;
        this.username = username;
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
        this.userid = userid;
        this.done = done;
        this.open = open;
        this.erdat = erdat;
    }

    public boolean isDone() {
        return done;
    }

    public String getErdat() {
        return erdat;
    }

    public boolean isOpen() {
        return open;
    }

    public int getTauschid() {
        return tauschid;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getUserfirstname() {
        return userfirstname;
    }

    public int getUserid() {
        return userid;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public String getUsername() {
        return username;
    }
}
