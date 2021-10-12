package com.nus.edtech.blogs.controllers;

import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.mappers.MapperFacadeFactory;
import com.nus.edtech.blogs.models.Blogs;
import com.nus.edtech.blogs.services.BlogsService;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class BlogsControllerTests {

    @Mock
    private BlogsService blogsService;

    @InjectMocks
    private BlogsController blogsController;

     @Spy
    private MapperFacade mapperFacade = new MapperFacadeFactory().getObject();

    private BlogsEntity blogsEntity;

    private List<BlogsEntity> blogsList;

    private List<String> blogIds;

    public BlogsControllerTests() throws Exception {
    }

    @Before
    public void init(){
        blogsList = new ArrayList<BlogsEntity>();
        blogIds = new ArrayList<String>();
        blogsEntity = new BlogsEntity();
        blogsEntity.setBlogId("1234");
        blogsEntity.setBlogAuthor("u1");
        blogsEntity.setBlogTitle("Java Basics");
        blogsEntity.setBlogText("Java is a high-level, general-purpose, object-oriented, and secure programming language developed" +
                " by James Gosling at Sun Microsystems, Inc. in 1991. It is formally known as OAK. In 1995," +
                " Sun Microsystem changed the name to Java. In 2009, Sun Microsystem takeover by Oracle Corporation.");
        Set<String> tags = new HashSet<String>();
        tags.add("Java");
        tags.add("OOP");
        blogsEntity.setBlogTags(tags);
        blogsEntity.setBlogCreationDate("11-11-2021");
        blogsList.add(blogsEntity);
        blogIds.add("1234");
    }

    @Test
    public void getAllBlogs_isSuccess(){
        Mockito.when(blogsService.getAllBlogs()).thenReturn(blogsList);
        List<Blogs> blogsResponse = blogsController.getAllBlogs();

        Assert.assertEquals(blogsList.size(),blogsResponse.size());
        Assert.assertEquals(blogsList.get(0).getBlogId(),blogsResponse.get(0).getBlogId());
        Assert.assertEquals(blogsList.get(0).getBlogAuthor(),blogsResponse.get(0).getBlogAuthor());
        Assert.assertEquals(blogsList.get(0).getBlogTitle(),blogsResponse.get(0).getBlogTitle());
        Assert.assertEquals(blogsList.get(0).getBlogText(),blogsResponse.get(0).getBlogText());
        Assert.assertEquals(blogsList.get(0).getBlogTags(),blogsResponse.get(0).getBlogTags());
        Assert.assertEquals(blogsList.get(0).getBlogCreationDate(),blogsResponse.get(0).getBlogCreationDate());
    }

    @Test
    public void getAllBlogs_isEmpty() {
        List<BlogsEntity> emptyBlogsEntity = new ArrayList<BlogsEntity>();
        Mockito.when(blogsService.getAllBlogs()).thenReturn(emptyBlogsEntity);
        List<Blogs> blogItemsActual = blogsController.getAllBlogs();
        Assert.assertEquals(new ArrayList() {}, blogItemsActual);
        Assert.assertEquals(0,blogItemsActual.size());
    }

    @Test
    public void getBlogIdsByAuthor_isSuccess() throws Exception {

        Mockito.when(blogsService.findBlogsByAuthor("u1")).thenReturn(blogIds);
        List<String> blogIdsActual = blogsController.getBlogIdsByAuthor("u1");

        Assert.assertEquals(blogIds.size(),blogIdsActual.size());
        Assert.assertEquals(blogIds.get(0),blogIdsActual.get(0));
    }

    @Test
    public void getBlogIdsByAuthor_isEmpty() throws Exception {

        List<String> emptyList = new ArrayList<String>();
        Mockito.when(blogsService.findBlogsByAuthor("u1_nonexist")).thenReturn(emptyList);
        List<String> blogIdsActual = blogsController.getBlogIdsByAuthor("u1_nonexist");

        Assert.assertEquals(new ArrayList(){},blogIdsActual);
        Assert.assertEquals(0,blogIdsActual.size());
    }
}
