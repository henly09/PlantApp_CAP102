package com.henzmontera.cap102_plantapp;

public class ListPost {

    private String POSTID;
    private String POSTUSERID;

    private String USERNAME;
    private String PROFILEPIC;
    private String POSTDESCRIPTION;
    private String POSTIMAGES;
    private String POSTTIME;

    private String COMMENTCOUNT;
    private String LIKECOUNT;

    public ListPost(String POSTID, String POSTUSERID, String USERNAME, String PROFILEPIC, String POSTDESCRIPTION, String POSTIMAGES, String POSTTIME, String COMMENTCOUNT, String LIKECOUNT) {
        this.POSTID = POSTID;
        this.POSTUSERID = POSTUSERID;
        this.USERNAME = USERNAME;
        this.PROFILEPIC = PROFILEPIC;
        this.POSTDESCRIPTION = POSTDESCRIPTION;
        this.POSTIMAGES = POSTIMAGES;
        this.POSTTIME = POSTTIME;
        this.COMMENTCOUNT = COMMENTCOUNT;
        this.LIKECOUNT = LIKECOUNT;
    }

    public String getPOSTID() {
        return POSTID;
    }

    public String getPOSTUSERID() {
        return POSTUSERID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPROFILEPIC() {
        return PROFILEPIC;
    }

    public String getPOSTDESCRIPTION() {
        return POSTDESCRIPTION;
    }

    public String getPOSTIMAGES() {
        return POSTIMAGES;
    }

    public String getPOSTTIME() {
        return POSTTIME;
    }

    public String getCOMMENTCOUNT() {
        return COMMENTCOUNT;
    }

    public String getLIKECOUNT() {
        return LIKECOUNT;
    }
}
