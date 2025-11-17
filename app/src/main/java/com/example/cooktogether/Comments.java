package com.example.cooktogether;

public class Comments {
    private String whoCommentUid;
    private String comment;
    public Comments(String whoCommentUid,String comment){
        this.comment=comment;
        this.whoCommentUid=whoCommentUid;
    }

    public String getComment() {
        return comment;
    }

    public String getWhoCommentUid() {
        return whoCommentUid;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setWhoCommentUid(String whoCommentUid) {
        this.whoCommentUid = whoCommentUid;
    }
}
