package com.nus.edtech.blogs.models;

import java.util.Set;

public class Blogs {
        private String id;
        private String author;
        private String blogTitle;
        private String createDate;
        private String blogText;
        private Set<String> blogTag;


        public String getId() { return id;}
        public void setId(String id) {this.id = id;}

        public String getAuthor() { return author;}
        public void setAuthor(String author) {this.author = author;}

        public String getCreateDate() { return createDate;}
        public void setCreateDate(String createDate) {this.createDate = createDate;}

        public String getBlogTitle() { return blogTitle;}
        public void setBlogTitle(String blogTitle) {this.blogTitle = blogTitle;}

        public String getBlogText() { return blogText; }
        public void setBlogText(String blogText) { this.blogText = blogText; }

        public Set<String> getBlogTag() { return blogTag; }
        public void setBlogTag(Set<String> blogTag) { this.blogTag = blogTag; }

        @Override
        public String toString() {
            return "Blog [id=" + id + ", blogTitle=" + blogTitle + ", createDate=" + createDate
                    + ", blogText=" + blogText + ", author=" + author + ", blogTag=" + blogTag + "]";
        }
}