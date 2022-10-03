package com.henzmontera.cap102_plantapp;

public class ListComment {

    private String COMMENTID;
    private String POSTID;
    private String COMMENTER_USERID;
    private String COMMENTER_USERNAME;
    private String COMMENTER_USERPROFILE;
    private String TEXT;
    private String CREATED_AT;
    private String EDITED_AT;

    public ListComment(String COMMENTID, String POSTID, String COMMENTER_USERID, String COMMENTER_USERNAME, String COMMENTER_USERPROFILE, String TEXT, String CREATED_AT, String EDITED_AT) {
        this.COMMENTID = COMMENTID;
        this.POSTID = POSTID;
        this.COMMENTER_USERID = COMMENTER_USERID;
        this.COMMENTER_USERNAME = COMMENTER_USERNAME;
        this.COMMENTER_USERPROFILE = COMMENTER_USERPROFILE;
        this.TEXT = TEXT;
        this.CREATED_AT = CREATED_AT;
        this.EDITED_AT = EDITED_AT;
    }

    public String getCOMMENTID() {
        return COMMENTID;
    }

    public String getPOSTID() {
        return POSTID;
    }

    public String getCOMMENTER_USERID() {
        return COMMENTER_USERID;
    }

    public String getCOMMENTER_USERNAME() {
        return COMMENTER_USERNAME;
    }

    public String getCOMMENTER_USERPROFILE() {
        return COMMENTER_USERPROFILE;
    }

    public String getTEXT() {
        return TEXT;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public String getEDITED_AT() {
        return EDITED_AT;
    }
}

