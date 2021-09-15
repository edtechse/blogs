package com.nus.edtech.blogs.controllers;

import com.nus.edtech.blogs.common.utils.BlogsValidator;
import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.dao.ComplexBlogs;
import com.nus.edtech.blogs.models.Blogs;
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
    public boolean postBlogByAuthor(@PathVariable (value = "author") String author, @RequestBody Blogs requestBlog) throws Exception {
        if(requestBlog == null || author == null) {
            throw new Exception("Input blog or author is null");
        }
        try{
            requestBlog.setAuthor(author);
            blogsValidator.validateInputBlogs(requestBlog);
            BlogsEntity requestBlogsEntity = mapperFacade.map(requestBlog, BlogsEntity.class);
            blogsService.postBlogByAuthor(requestBlogsEntity);
            return true;

        }catch (Exception ex){
            throw ex;
        }
    }

    @GetMapping("author/{author}")
    public List<String> getBlogIdByAuthor(@PathVariable(value = "author") String author) {
        return blogsService.findBlogsByAuthor(author);
    }

    @DeleteMapping("author/{author}")
    public boolean deleteBlogsByAuthor(@PathVariable (value = "author") String author){
        try {
            blogsService.deleteBlogsByAuthor(author);
        }catch (Exception ex){
            throw ex;
        }
        return true;
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

    @GetMapping("complex/{author}/{blogId}")
    public List<ComplexBlogs> getComplexBlogByAuthorAndId(@PathVariable(value = "author") String author, @PathVariable(value = "blogId") String blogId) {
        return blogsService.findComplexBlogsByAuthorAndId(author, blogId);
    }

    @GetMapping("complex/author/{author}")
    public List<ComplexBlogs> getComplexBlogByAuthor(@PathVariable(value = "author") String author) {
        return blogsService.findComplexBlogsByAuthor(author);
    }

    @PostMapping("complex/{author}")
    public boolean postComplexBlogByAuthor(@PathVariable (value = "author") String author, @RequestBody ComplexBlogs requestBlog) throws Exception {
        if(requestBlog == null || author == null) {
            throw new Exception("Input blog or author is null");
        }
        try{
            requestBlog.setAuthor(author);
            blogsService.postComplexBlogByAuthor(requestBlog);
            return true;

        }catch (Exception ex){
            throw ex;
        }
    }

    //query index
    @GetMapping("index/author/{author}")
    public void queryIndex(@PathVariable(value = "author") String author) {
         blogsService.queryIndex(author);
    }
}
