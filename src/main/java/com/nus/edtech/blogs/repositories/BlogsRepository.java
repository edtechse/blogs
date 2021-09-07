package com.nus.edtech.blogs.repositories;

import com.nus.edtech.blogs.dao.BlogsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BlogsRepository  {

    @Autowired
    private DynamoDBMapper dynamoDBmapper;

    public BlogsEntity findBlogById(int blogId) {
        return dynamoDBMapper.load(BlogsEntity.class, 1, "Garfield");
    }

    public BlogsEntity save(BlogsEntity blogsEntity){
        return new BlogsEntity();
    }
}
