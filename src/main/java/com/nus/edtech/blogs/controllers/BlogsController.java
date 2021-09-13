package com.nus.edtech.blogs.controllers;

import com.nus.edtech.blogs.common.utils.BlogsValidator;
import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.services.BlogsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/blogs/")

public class BlogsController {

    @Autowired
    private BlogsService blogsService;

    @Autowired
    private BlogsValidator blogsValidator;

    @Autowired
    private MapperFacade mapperFacade;

    @PostMapping("author/{author}")
    public boolean postBlogByAuthor(@PathVariable (value = "author") String author, @RequestBody BlogsEntity requestBlogsEntity) throws Exception {
        if(requestBlogsEntity == null || author == null) {
            throw new Exception("Input blog or author is null");
        }
        try{
            requestBlogsEntity.setAuthor(author);
            blogsValidator.validateInputBlogs(requestBlogsEntity);
            //BlogsEntity requestBlogsEntity = mapperFacade.map(blogs, BlogsEntity.class);
            blogsService.postBlogByAuthor(requestBlogsEntity);
            //Blogs responseBlogs = mapperFacade.map(responseBlogsEntity, Blogs.class);
            return true;

        }catch (Exception ex){
            throw ex;
        }
    }

    @GetMapping("author/{author}")
    public List<String> getBlogIdByAuthor(@PathVariable(value = "author") String author) {
        return blogsService.findQuestionIdByAuthor(author);
    }

    @GetMapping("/blog/{id}")
    public BlogsEntity getBlogById(@PathVariable (value = "id") String id){
        return blogsService.getBlogById(id);
    }

    @PutMapping("blog/{id}")
    public boolean updateBlogById(@PathVariable(value = "id") String id, @RequestBody BlogsEntity requestBlogsEntity) throws Exception {
        try {
            if(id == null)
                throw new Exception("Empty id sent for updateBlog");
            requestBlogsEntity.setId(id);
            blogsService.updateBlog(requestBlogsEntity);
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    @DeleteMapping("blog/{id}")
    public boolean deleteBlogById(@PathVariable(value = "id") String id) throws Exception {
        try {
            if(id == null)
                throw new Exception("Empty id sent for delete");
            blogsService.deleteBlogById(id);
            return true;
        } catch (Exception ex) {
            throw new Exception("deleteBlogById:: Failed to delete blog due to " + ex.getMessage());
        }
    }
}
