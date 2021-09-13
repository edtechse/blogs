package com.nus.edtech.blogs.services;

import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogsService {

    @Autowired
    private BlogsRepository blogsRepository;

    public void postBlogByAuthor(BlogsEntity blogsEntity){
         blogsRepository.saveBlog(blogsEntity);
    }

    public List<String> findQuestionIdByAuthor(String author) {
        List<BlogsEntity> items = blogsRepository.findBlogsByAuthor(author);
        List<String> idList = new ArrayList<>();
        for(BlogsEntity item: items) {
            idList.add(item.getId());
        }
        return idList;
    }

    public BlogsEntity getBlogById(String id) {
        return blogsRepository.findBlogById(id);
    }

    public void updateBlog(BlogsEntity requestBlogsEntity) {
        BlogsEntity originalBlogEntity = blogsRepository.findBlogById(requestBlogsEntity.getId());
        if (requestBlogsEntity.getBlogTitle() != null)
            originalBlogEntity.setBlogTitle(requestBlogsEntity.getBlogTitle());
        if(requestBlogsEntity.getBlogText() != null)
            originalBlogEntity.setBlogText(requestBlogsEntity.getBlogText());
        if(requestBlogsEntity.getBlogTag() != null)
            originalBlogEntity.setBlogTag(requestBlogsEntity.getBlogTag());
        blogsRepository.saveBlog(originalBlogEntity);
    }

    public void deleteBlogById(String id) {
        BlogsEntity blogsEntity = blogsRepository.findBlogById(id);
        blogsRepository.deleteBlogById(blogsEntity);
    }
}
