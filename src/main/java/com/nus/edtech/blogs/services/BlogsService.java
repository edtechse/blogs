package com.nus.edtech.blogs.services;

import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogsService {

    @Autowired
    private BlogsRepository blogsRepository;

    public BlogsEntity postBlogs(BlogsEntity blogsEntity){
        blogsRepository.save(blogsEntity);
    }
}
