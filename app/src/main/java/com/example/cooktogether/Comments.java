package com.example.cooktogether;

public class Comments {
    private String whoCommentName;
    private String comment;
    public Comments(String whoCommentName,String comment){
        this.comment=comment;
        this.whoCommentName=whoCommentName;
    }

    public String getComment() {
        return comment;
    }

    public String getWhoCommentUid() {
        return whoCommentName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setWhoCommentUid(String whoCommentUid) {
        this.whoCommentName = whoCommentUid;
    }
}
