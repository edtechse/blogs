package com.nus.edtech.blogs.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nus.edtech.blogs.dao.BlogsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BlogsRepository  {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public BlogsEntity findBlogById(String id) {
        return dynamoDBMapper.load(BlogsEntity.class, id);
    }

    public List<BlogsEntity> findBlogsByAuthor(String author) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

        eav.put(":val", new AttributeValue().withS(author));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("blogauthor = :val").withExpressionAttributeValues(eav);
        List<BlogsEntity> scanResult = dynamoDBMapper.scan(BlogsEntity.class, scanExpression);

        return scanResult;
    }

    public void saveBlog(BlogsEntity blogsEntity){
        dynamoDBMapper.save(blogsEntity);
    }

    public void deleteBlog(BlogsEntity blogsEntity) {
        dynamoDBMapper.delete(blogsEntity);
    }
}
