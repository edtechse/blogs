package com.nus.edtech.blogs.models;

import java.util.List;
import java.util.Set;

public class Blogs {
        private String blogId;
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

        public String getBlogId() { return blogId;}
        public void setBlogId(String blogId) {this.blogId = blogId;}

        public String getBlogAuthor() { return blogAuthor;}
        public void setBlogAuthor(String blogAuthor) {this.blogAuthor = blogAuthor;}

        public String getBlogCreationDate() { return blogCreationDate;}
        public void setBlogCreationDate(String blogCreationDate) {this.blogCreationDate = blogCreationDate;}

        public String getBlogTitle() { return blogTitle;}
        public void setBlogTitle(String blogTitle) {this.blogTitle = blogTitle;}

        public String getBlogText() { return blogText; }
        public void setBlogText(String blogText) { this.blogText = blogText; }

        public Set<String> getBlogTags() { return blogTags; }
        public void setBlogTags(Set<String> blogTags) { this.blogTags = blogTags; }

        public String getLikesCount() { return likesCount; }
        public void setLikesCount(String likesCount) { this.likesCount = likesCount; }

        public String getRating() { return rating; }
        public void setRating(String rating) { this.rating = rating; }

        public boolean isReportSpam() { return reportSpam; }
        public void setReportSpam(boolean reportSpam) { this.reportSpam = reportSpam; }

        public List<Comments> getComments() { return comments; }
        public void setComments(List<Comments> comments) { this.comments = comments; }

        public Set<String> getLikeInteractionIds() { return likeInteractionIds; }
        public void setLikeInteractionIds(Set<String> likeInteractionIds) { this.likeInteractionIds = likeInteractionIds; }

        public Set<String> getReportSpamInteractionIds() { return reportSpamInteractionIds; }
        public void setReportSpamInteractionIds(Set<String> reportSpamInteractionIds) {
                this.reportSpamInteractionIds = reportSpamInteractionIds; }

        public Set<String> getRatingInteractionIds() { return ratingInteractionIds; }
        public void setRatingInteractionIds(Set<String> ratingInteractionIds) { this.ratingInteractionIds = ratingInteractionIds; }

        @Override
        public String toString() {
            return "Blog [id=" + blogId + ", blogTitle=" + blogTitle + ", createDate=" + blogCreationDate
                    + ", blogText=" + blogText + ", author=" + blogAuthor + ", blogTags=" + blogTags + "]";
        }
}