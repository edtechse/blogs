package com.nus.edtech.blogs.services;

import com.nus.edtech.blogs.common.utils.BlogsValidator;
import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.models.Interaction;
import com.nus.edtech.blogs.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class BlogsService {

    @Autowired
    private BlogsRepository blogsRepository;

    @Autowired
    private BlogsValidator blogsValidator;

    public void postBlogByAuthor(BlogsEntity blogsEntity){
        blogsValidator.validateInputBlogs(blogsEntity);
         blogsRepository.saveBlog(blogsEntity);
    }

    public List<String> findBlogsByAuthor(String author) {
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
        originalBlogEntity = blogsValidator.updateBlogsEntity(requestBlogsEntity,originalBlogEntity);
        blogsRepository.saveBlog(originalBlogEntity);
    }

    public void deleteBlogById(String id) {
        BlogsEntity blogsEntity = blogsRepository.findBlogById(id);
        blogsRepository.deleteBlog(blogsEntity);
    }

    public void deleteBlogsByAuthor(String author) {
        List <BlogsEntity> blogsByAuthor = blogsRepository.findBlogsByAuthor(author);
        for (BlogsEntity blogEntity:blogsByAuthor) {
            blogsRepository.deleteBlog(blogEntity);
        }
    }

    public void addCommentToBlog(String blogId, BlogsEntity.Comments comments) {
        BlogsEntity originalBlogEntity = blogsRepository.findBlogById(blogId);
        originalBlogEntity = blogsValidator.addCommentToBlog(comments,originalBlogEntity);
        blogsRepository.saveBlog(originalBlogEntity);
    }

    public void deleteCommentById(String blogId, String commentId) {
        BlogsEntity originalBlogEntity = blogsRepository.findBlogById(blogId);
        for (BlogsEntity.Comments comment : originalBlogEntity.getComments()) {
            if(comment.getCommentId().equalsIgnoreCase(commentId)) {
                originalBlogEntity.getComments().remove(comment);
                blogsRepository.saveBlog(originalBlogEntity);
                break;
            }
        }
    }

    public void updateInteractionToBlog(String blogId, Interaction interaction) {
        BlogsEntity originalBlogEntity = blogsRepository.findBlogById(blogId);
        switch (interaction.getInteractionType()){
            case "like":  {
                if(originalBlogEntity.getLikeInteractionIds() != null)
                    originalBlogEntity.getLikeInteractionIds().add(interaction.getInteractionId());
                else
                    originalBlogEntity.setLikeInteractionIds(new HashSet<String>(Arrays.asList(interaction.getInteractionId())));
                originalBlogEntity.setLikesCount(originalBlogEntity.getLikesCount()+1);
                break;
            }
            case "rating": {
                if(originalBlogEntity.getRatingInteractionIds() != null) {
                    originalBlogEntity.getRatingInteractionIds().add(interaction.getInteractionId());
                    originalBlogEntity.setRating((originalBlogEntity.getRating()+(Integer.valueOf(interaction.getInteractionValue())))/2);
                }
                else {
                    originalBlogEntity.setRatingInteractionIds(new HashSet<String>(Arrays.asList(interaction.getInteractionId())));
                    originalBlogEntity.setRating(Integer.valueOf(interaction.getInteractionValue()));
                }
                break;
            }
            case "reportspam": {
                if(originalBlogEntity.getReportSpamInteractionIds()!= null)
                    originalBlogEntity.getReportSpamInteractionIds().add(interaction.getInteractionId());
                else
                    originalBlogEntity.setReportSpamInteractionIds(new HashSet<String>(Arrays.asList(interaction.getInteractionId())));
                originalBlogEntity.setReportSpam(true);
                break;
            }
            default: break;
        }
        blogsRepository.saveBlog(originalBlogEntity);
    }
}
