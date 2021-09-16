package com.nus.edtech.blogs.common.utils;

import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.exceptions.InvalidRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class BlogsValidator {

    public void validateInputBlogs(BlogsEntity requestBlog){
        if(StringUtils.isEmpty(requestBlog.getBlogTitle()))
                    throw new InvalidRequestException("Invalid Blog Title");
        if(StringUtils.isEmpty(requestBlog.getBlogText()))
            throw new InvalidRequestException("Invalid Blog Text");
        if(StringUtils.isEmpty(requestBlog.getBlogTags()))
            throw new InvalidRequestException("Invalid Blog Tag");
        if(StringUtils.isEmpty(requestBlog.getBlogAuthor()))
            throw new InvalidRequestException("Invalid Blog Author");
        if(StringUtils.isEmpty(requestBlog.getBlogCreationDate()))
            throw new InvalidRequestException("Invalid Blog created date");
    }

    public BlogsEntity updateBlogsEntity(BlogsEntity requestBlogsEntity, BlogsEntity originalBlogEntity) {
        if (requestBlogsEntity.getBlogTitle() != null)
            originalBlogEntity.setBlogTitle(requestBlogsEntity.getBlogTitle());
        if(requestBlogsEntity.getBlogText() != null)
            originalBlogEntity.setBlogText(requestBlogsEntity.getBlogText());
        if(requestBlogsEntity.getBlogTags() != null)
            originalBlogEntity.setBlogTags(requestBlogsEntity.getBlogTags());
        return originalBlogEntity;
    }

    public BlogsEntity addCommentToBlog(BlogsEntity.Comments comments, BlogsEntity originalBlogEntity) {
        if(comments.getCommentAuthor() != null && comments.getCommentText() != null &&
                comments.getCommentCreationDate() != null && comments.getCommentId() != null) {
            if (originalBlogEntity.getComments() != null && originalBlogEntity.getComments().size() > 0)
                originalBlogEntity.getComments().add(comments);
            else
                originalBlogEntity.setComments(new ArrayList<BlogsEntity.Comments>(Arrays.asList(comments)));
        }
        return originalBlogEntity;
    }
}
