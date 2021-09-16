package com.nus.edtech.blogs.models;

import com.nus.edtech.blogs.dao.BlogsEntity;

import java.util.List;
import java.util.Set;

public class Blogs {
        private String id;
        private String blogAuthor;
        private String blogTitle;
        private String blogCreationDate;
        private String blogText;
        private Set<String> blogTags;
        private String likesCount;
        private String rating;
        private boolean reportSpam;
        private List<BlogsEntity.Comments> comments;
        private Set<String> interactionIds;


        public String getId() { return id;}
        public void setId(String id) {this.id = id;}

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

        public List<BlogsEntity.Comments> getComments() { return comments; }
        public void setComments(List<BlogsEntity.Comments> comments) { this.comments = comments; }

        public Set<String> getInteractionIds() { return interactionIds; }
        public void setInteractionIds(Set<String> interactionIds) { this.interactionIds = interactionIds; }

        @Override
        public String toString() {
            return "Blog [id=" + id + ", blogTitle=" + blogTitle + ", createDate=" + blogCreationDate
                    + ", blogText=" + blogText + ", author=" + blogAuthor + ", blogTags=" + blogTags + "]";
        }
}