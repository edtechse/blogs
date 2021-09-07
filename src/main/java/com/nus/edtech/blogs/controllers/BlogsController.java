package com.nus.edtech.blogs.controllers;

import com.nus.edtech.blogs.common.utils.BlogsValidator;
import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.models.Blogs;
import com.nus.edtech.blogs.services.BlogsService;
import com.sun.jdi.request.InvalidRequestStateException;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blogs")

public class BlogsController {

    @Autowired
    private BlogsService blogsService;

    @Autowired
    private BlogsValidator blogsValidator;

    @Autowired
    private MapperFacade mapperFacade;

    @PostMapping("/author/{authorid}")
    public Blogs postBlogs(@PathVariable (value = "authorid") String authorid, @RequestBody Blogs blogs){
        if(blogs == null || authorid == null) {
            throw new InvalidRequestStateException
                    ("Blogs record is null ");
        }
        try{
            blogsValidator.validateInputBlogs(blogs);
            BlogsEntity requestBlogsEntity = mapperFacade.map(blogs, BlogsEntity.class);
            BlogsEntity responseBlogsEntity = blogsService.postBlogs(requestBlogsEntity);
            Blogs responseBlogs = mapperFacade.map(responseBlogsEntity, Blogs.class);
            return responseBlogs;

        }catch (Exception ex){
            throw ex;
        }


    }

}
