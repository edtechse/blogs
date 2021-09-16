package com.nus.edtech.blogs.models;

public class Comment {

    private String commentText;
    private String commentAuthor;
    private String commentCreationDate;

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public String getCommentCreationDate() {
        return commentCreationDate;
    }

    public void setCommentCreationDate(String commentCreationDate) {
        this.commentCreationDate = commentCreationDate;
    }
}
