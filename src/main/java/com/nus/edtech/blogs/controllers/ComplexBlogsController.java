package com.nus.edtech.blogs.controllers;

import com.nus.edtech.blogs.dao.ComplexBlogs;
import com.nus.edtech.blogs.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/complexblogs/")
public class ComplexBlogsController {
    @Autowired
    private BlogsService blogsService;

    @GetMapping("author/{author}/{blogId}")
    public List<ComplexBlogs> getBlogIdByAuthor(@PathVariable(value = "author") String author, @PathVariable(value = "blogId") String blogId) {
        return blogsService.findComplexBlogsByAuthorAndId(author, blogId);
    }
}
