package com.henzmontera.cap102_plantapp;

public class ListPost {

    private String USERID;
    private String POSTID;

    private String USERNAME;
    private String POSTDESC;
    private String POSTTIME;
    private String COMMENTC;
    private String LIKEC;

    private int PROFILEPIC;
    private int POSTIMAGES;

    public ListPost(String USERID, String POSTID, String USERNAME, String POSTDESC, String POSTTIME, String COMMENTC, String LIKEC, int PROFILEPIC, int POSTIMAGES) {
        this.USERID = USERID;
        this.POSTID = POSTID;
        this.USERNAME = USERNAME;
        this.POSTDESC = POSTDESC;
        this.POSTTIME = POSTTIME;
        this.COMMENTC = COMMENTC;
        this.LIKEC = LIKEC;
        this.PROFILEPIC = PROFILEPIC;
        this.POSTIMAGES = POSTIMAGES;
    }

    public String getUSERID() {
        return USERID;
    }

    public String getPOSTID() {
        return POSTID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPOSTDESC() {
        return POSTDESC;
    }

    public String getPOSTTIME() {
        return POSTTIME;
    }

    public String getCOMMENTC() {
        return COMMENTC;
    }

    public String getLIKEC() {
        return LIKEC;
    }

    public int getPROFILEPIC() {
        return PROFILEPIC;
    }

    public int getPOSTIMAGES() {
        return POSTIMAGES;
    }
}
