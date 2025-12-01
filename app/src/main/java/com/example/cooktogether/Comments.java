package com.example.cooktogether;

public class Comments {
    private String whoCommentName;
    private String comment;
    private String rid;
    private String keyId;
    public Comments(String whoCommentName,String comment,String Rid){
        this.comment=comment;
        this.whoCommentName=whoCommentName;
        this.rid =Rid;
    }
    public Comments(Comments other){
        this.whoCommentName = other.whoCommentName;
        this.comment = other.comment;
        this.rid =other.rid;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Comments(){

    }
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }


    public void setWhoCommentName(String whoCommentName) {
        this.whoCommentName = whoCommentName;
    }

    public String getComment() {
        return comment;
    }

    public String getWhoCommentName() {
        return whoCommentName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
