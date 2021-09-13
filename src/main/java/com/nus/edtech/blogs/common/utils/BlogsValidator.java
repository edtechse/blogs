package com.nus.edtech.blogs.common.utils;

import com.nus.edtech.blogs.exceptions.InvalidRequestException;
import com.nus.edtech.blogs.models.Blogs;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BlogsValidator {

    public void validateInputBlogs(Blogs requestBlog){
        if(StringUtils.isEmpty(requestBlog.getBlogTitle()))
                    throw new InvalidRequestException("Invalid Blog Title");
        if(StringUtils.isEmpty(requestBlog.getBlogText()))
            throw new InvalidRequestException("Invalid Blog Text");
        if(StringUtils.isEmpty(requestBlog.getBlogTag()))
            throw new InvalidRequestException("Invalid Blog Tag");
        if(StringUtils.isEmpty(requestBlog.getAuthor()))
            throw new InvalidRequestException("Invalid Blog Author");
        if(StringUtils.isEmpty(requestBlog.getCreateDate()))
            throw new InvalidRequestException("Invalid Blog created date");
    }
}
