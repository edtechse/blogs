package com.nus.edtech.blogs.controllers;

import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.models.Blogs;
import com.nus.edtech.blogs.models.Comments;
import com.nus.edtech.blogs.models.Interaction;
import com.nus.edtech.blogs.services.BlogsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
//import com.edtech.qaservice.model.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/blogs/")

public class BlogsController {

    private static final String QNA_SERVICE = "qnaService";

    @Autowired
    private BlogsService blogsService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("questions/all")
    @CircuitBreaker(name=QNA_SERVICE, fallbackMethod= "qnaFallback")
    public String getMyOrder(){
        try {
            return restTemplate.getForObject
                    ("http://localhost:9003/v1/order",
                            String.class);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public String qnaFallback(Exception e){
        return "test error";
    }

    @GetMapping("all")
    public List<Blogs> getAllBlogs(){
        try {
            List<BlogsEntity> responseBlogList = blogsService.getAllBlogs();
            return mapperFacade.mapAsList(responseBlogList,Blogs.class);
        } catch (Exception ex) {
            throw ex;
        }

    }
    @PostMapping("author/{author}")
    public boolean postBlogByAuthor(@PathVariable(value = "author") String author, @RequestBody Blogs requestBlog) throws Exception {
        if (requestBlog == null || author == null)
            throw new Exception("Input blog or author is null");
        try {
            requestBlog.setBlogAuthor(author);
            BlogsEntity requestBlogsEntity = mapperFacade.map(requestBlog, BlogsEntity.class);
            blogsService.postBlogByAuthor(requestBlogsEntity);
            return true;

        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("author/{author}")
    public List<String> getBlogIdsByAuthor(@PathVariable(value = "author") String author) throws Exception {
        if (author == null)
            throw new Exception("Input author is null");
        return blogsService.findBlogsByAuthor(author);
    }

    @DeleteMapping("author/{author}")
    public boolean deleteBlogsByAuthor(@PathVariable(value = "author") String author) throws Exception {
        if (author == null)
            throw new Exception("Input author is null");
        try {
            blogsService.deleteBlogsByAuthor(author);
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    @GetMapping("/blog/{blogid}")
    public BlogsEntity getBlogById(@PathVariable(value = "blogid") String blogId) throws Exception {
        if (blogId == null)
            throw new Exception("Input blogId is null");
        return blogsService.getBlogById(blogId);
    }

    @PutMapping("blog/{id}")
    public boolean updateBlogById(@PathVariable(value = "id") String id, @RequestBody Blogs requestBlog) throws Exception {
        try {
            if (id == null)
                throw new Exception("Empty id sent for updateBlog");
            requestBlog.setBlogId(id);
            BlogsEntity requestBlogsEntity = mapperFacade.map(requestBlog, BlogsEntity.class);
            blogsService.updateBlog(requestBlogsEntity);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("blog/{id}")
    public boolean deleteBlogById(@PathVariable(value = "id") String id) throws Exception {
        try {
            if (id == null)
                throw new Exception("Empty id sent for delete");
            blogsService.deleteBlogById(id);
            return true;
        } catch (Exception ex) {
            throw new Exception("deleteBlogById:: Failed to delete blog due to " + ex.getMessage());
        }
    }

    @PutMapping("blog/{blogid}/comment")
    public boolean addCommentToBlog(@PathVariable(value = "blogid") String blogId, @RequestBody Comments comments)
            throws Exception {
        try {
            if (blogId == null || comments == null)
                throw new Exception("Empty blogId or comment sent for addCommentToBlog");
            String commentId = UUID.randomUUID().toString().replaceAll("-","");
            comments.setCommentId(commentId);
            BlogsEntity.Comments commentsEntity = mapperFacade.map(comments, BlogsEntity.Comments.class);
            blogsService.addCommentToBlog(blogId, commentsEntity);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("blog/{blogid}/comment/{commentid}")
    public boolean deleteCommentById(@PathVariable(value = "blogid") String blogId,
                                     @PathVariable(value = "commentid") String commentId) throws Exception {
        try {
            if(blogId == null || commentId == null)
                throw new Exception("Input BlogId or CommentId is null for deleteCommentById");
            blogsService.deleteCommentById(blogId,commentId);
            return true;
        } catch(Exception ex){
        throw ex;
        }
    }

    @PutMapping("blog/{blogid}/interaction")
    public boolean updateInteractionToBlog(@PathVariable(value = "blogid") String blogId,
                                           @RequestBody Interaction interaction) throws Exception {
        try {
            if(blogId == null || interaction == null)
                throw new Exception("Input blogId or interaction is null for updateInteractionToBlog");
            blogsService.updateInteractionToBlog(blogId,interaction);
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }
}
