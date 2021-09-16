package com.nus.edtech.blogs.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Set;


@DynamoDBTable(tableName = "Blogs")
public class BlogsEntity {
    private String id;
    private String blogAuthor;
    private String blogTitle;
    private String blogCreationDate;
    private String blogText;
    private Set<String> blogTags;
    private String likesCount;
    private String rating;
    private boolean reportSpam;
    private List<Comments> comments;
    private Set<String> likeInteractionIds;
    private Set<String> reportSpamInteractionIds;
    private Set<String> ratingInteractionIds;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() { return id;}
    public void setId(String id) {this.id = id;}

    @DynamoDBAttribute(attributeName = "blogauthor")
    public String getBlogAuthor() { return blogAuthor;}
    public void setBlogAuthor(String blogAuthor) {this.blogAuthor = blogAuthor;}

    @DynamoDBAttribute(attributeName = "blogcreationdate")
    public String getBlogCreationDate() { return blogCreationDate;}
    public void setBlogCreationDate(String blogCreationDate) {this.blogCreationDate = blogCreationDate;}

    @DynamoDBAttribute(attributeName="blogtitle")
    public String getBlogTitle() { return blogTitle;}
    public void setBlogTitle(String blogTitle) {this.blogTitle = blogTitle;}

    @DynamoDBAttribute(attributeName = "blogtext")
    public String getBlogText() { return blogText; }
    public void setBlogText(String blogText) { this.blogText = blogText; }

    @DynamoDBAttribute(attributeName = "blogtags")
    public Set<String> getBlogTags() { return blogTags; }
    public void setBlogTags(Set<String> blogTags) { this.blogTags = blogTags; }

    @DynamoDBAttribute(attributeName = "likescount")
    public String getLikesCount() { return likesCount; }
    public void setLikesCount(String likesCount) { this.likesCount = likesCount; }

    @DynamoDBAttribute(attributeName = "rating")
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    @DynamoDBAttribute(attributeName = "reportspam")
    public boolean isReportSpam() { return reportSpam; }
    public void setReportSpam(boolean reportSpam) { this.reportSpam = reportSpam; }

    @DynamoDBAttribute(attributeName = "comments")
    public List<Comments> getComments() { return comments; }
    public void setComments(List<Comments> comments) { this.comments = comments; }

    @DynamoDBAttribute(attributeName = "likeinteractionids")
    public Set<String> getLikeInteractionIds() { return likeInteractionIds; }
    public void setLikeInteractionIds(Set<String> likeInteractionIds) { this.likeInteractionIds = likeInteractionIds; }

    @DynamoDBAttribute(attributeName = "reportspaminteractionids")
    public Set<String> getReportSpamInteractionIds() { return reportSpamInteractionIds; }
    public void setReportSpamInteractionIds(Set<String> reportSpamInteractionIds) {
        this.reportSpamInteractionIds = reportSpamInteractionIds; }

    @DynamoDBAttribute(attributeName = "ratinginteractionids")
    public Set<String> getRatingInteractionIds() { return ratingInteractionIds; }
    public void setRatingInteractionIds(Set<String> ratingInteractionIds) { this.ratingInteractionIds = ratingInteractionIds; }



    @DynamoDBDocument
    public static class Comments {

        private String commentId;
        private String commentText;
        private String commentAuthor;
        private String commentCreationDate;

        @DynamoDBAttribute(attributeName = "commentid")
        @DynamoDBAutoGeneratedKey
        public String getCommentId() { return commentId; }
        public void setCommentId(String commentId) { this.commentId = commentId; }

        @DynamoDBAttribute(attributeName = "commenttext")
        public String getCommentText() { return commentText; }
        public void setCommentText(String commentText) { this.commentText = commentText; }

        @DynamoDBAttribute(attributeName = "commentauthor")
        public String getCommentAuthor() { return commentAuthor; }
        public void setCommentAuthor(String commentAuthor) { this.commentAuthor = commentAuthor; }

        @DynamoDBAttribute(attributeName = "commentcreationdate")
        public String getCommentCreationDate() { return commentCreationDate; }
        public void setCommentCreationDate(String commentCreationDate) { this.commentCreationDate = commentCreationDate; }

    }

    @Override
    public String toString() {
        return "Blog [id=" + id + ", blogTitle=" + blogTitle + ", blogCreationDate=" + blogCreationDate
                + ", blogText=" + blogText + ", blogAuthor=" + blogAuthor + ", blogTags=" + blogTags + "]";
    }
}
