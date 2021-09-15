package com.nus.edtech.blogs.repositories;

//import com.amazon.dax.client.dynamodbv2.AmazonDaxClientBuilder;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.dao.ComplexBlogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class BlogsRepository  {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    //private static final String region = EC2MetadataUtils.getEC2InstanceRegion();


    public BlogsEntity findBlogById(String id) {
        return dynamoDBMapper.load(BlogsEntity.class, id);
    }

    public List<BlogsEntity> findBlogsByAuthor(String author) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(author));
/**
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("author = :val").withExpressionAttributeValues(eav);

        List<BlogsEntity> queryResult = dynamoDBMapper.query(BlogsEntity.class,queryExpression);
        return queryResult;
**/
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("author = :val").withExpressionAttributeValues(eav);

        List<BlogsEntity> scanResult = dynamoDBMapper.scan(BlogsEntity.class, scanExpression);

        return scanResult;
    }

    public void saveBlog(BlogsEntity blogsEntity){
        dynamoDBMapper.save(blogsEntity);
    }

    public void deleteBlog(BlogsEntity blogsEntity) {
        dynamoDBMapper.delete(blogsEntity);
    }

    public List<ComplexBlogs> findComplexBlogsByAuthorAndId(String author, String blogId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":author_a", new AttributeValue().withS(author));
        eav.put(":blogId_b", new AttributeValue().withS(blogId));

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
            .withKeyConditionExpression("author = :author_a and blogId = :blogId_b").withExpressionAttributeValues(eav);

        List<ComplexBlogs> queryResult = dynamoDBMapper.query(ComplexBlogs.class,queryExpression);
        return queryResult;
    }

    public void saveComplexBlog(ComplexBlogs blogsEntity){
        dynamoDBMapper.save(blogsEntity);
    }

    public List<ComplexBlogs> findComplexBlogsByAuthor(String author) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":author_a", new AttributeValue().withS(author));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("author = :author_a").withExpressionAttributeValues(eav);

        List<ComplexBlogs> queryResult = dynamoDBMapper.scan(ComplexBlogs.class,scanExpression);
        return queryResult;
    }

    public void queryIndex(String indexName){
         AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
         DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("blog-t1");
        Index index = table.getIndex("commentId-blogId-index");
        ItemCollection<QueryOutcome> items = null;
        QuerySpec querySpec = new QuerySpec();

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":commentId_a", new AttributeValue().withS("c111"));

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("commentId-blogId-index").withConsistentRead(false)
                .withKeyConditionExpression("commentId = :commentId_a")
                .withExpressionAttributeValues(eav);
        List<ComplexBlogs> queryResult = dynamoDBMapper.query(ComplexBlogs.class,queryExpression);

        querySpec.withKeyConditionExpression("commentId = :commentId_a")
                .withValueMap(new ValueMap().withString(":commentId_a", "c111"));
        items = index.query(querySpec);
        Iterator<Item> iterator = items.iterator();

        System.out.println("Query: printing results...");

        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJSONPretty());
        }
    }
}
