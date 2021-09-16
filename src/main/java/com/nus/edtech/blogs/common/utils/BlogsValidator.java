package com.nus.edtech.blogs.common.utils;

import com.nus.edtech.blogs.dao.BlogsEntity;
import com.nus.edtech.blogs.exceptions.InvalidRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlogsValidator {

    public void validateInputBlogs(BlogsEntity requestBlog){
        if(StringUtils.isEmpty(requestBlog.getBlogTitle()))
                    throw new InvalidRequestException("Invalid Blog Title");
        if(StringUtils.isEmpty(requestBlog.getBlogText()))
            throw new InvalidRequestException("Invalid Blog Text");
        if(StringUtils.isEmpty(requestBlog.getBlogTags()))
            throw new InvalidRequestException("Invalid Blog Tag");
        if(StringUtils.isEmpty(requestBlog.getBlogAuthor()))
            throw new InvalidRequestException("Invalid Blog Author");
        if(StringUtils.isEmpty(requestBlog.getBlogCreationDate()))
            throw new InvalidRequestException("Invalid Blog created date");
    }

    public BlogsEntity updateBlogsEntity(BlogsEntity requestBlogsEntity, BlogsEntity originalBlogEntity) {

        BlogsEntity.Comments commentTemp = new BlogsEntity.Comments();
        List<BlogsEntity.Comments> comments = new ArrayList<BlogsEntity.Comments>();

        if (requestBlogsEntity.getBlogTitle() != null)
            originalBlogEntity.setBlogTitle(requestBlogsEntity.getBlogTitle());
        if(requestBlogsEntity.getBlogText() != null)
            originalBlogEntity.setBlogText(requestBlogsEntity.getBlogText());
        if(requestBlogsEntity.getBlogTags() != null)
            originalBlogEntity.setBlogTags(requestBlogsEntity.getBlogTags());
        /**if(requestBlogsEntity.getComments() != null){
            commentTemp.setCommentAuthor(originalBlogEntity.getComments().get(0).getCommentAuthor());
            commentTemp.setAuthorComments(requestBlogsEntity.getComments().get(0).getAuthorComments());
            comments.add(commentTemp);
            if(originalBlogEntity.getComments() != null && originalBlogEntity.getComments().size() > 0){
                List<BlogsEntity.Comments> commentsExisting = originalBlogEntity.getComments();
                for (BlogsEntity.Comments comment: commentsExisting) {
                    if (comment.getCommentAuthor() == commentTemp.getCommentAuthor()) {
                        comment.getAuthorComments().add((BlogsEntity.AuthorComments) requestBlogsEntity.getComments().get(0).getAuthorComments());
                        break;
                    }
                }

            }
            else
                originalBlogEntity.setComments(comments);
        }**/

        if(requestBlogsEntity.getLikeInteractionIds() != null)
            originalBlogEntity.setLikeInteractionIds(requestBlogsEntity.getLikeInteractionIds());
        if(requestBlogsEntity.getLikesCount() != null)
            originalBlogEntity.setLikesCount(requestBlogsEntity.getLikesCount());
        if(requestBlogsEntity.getRating() != null)
            originalBlogEntity.setRating(requestBlogsEntity.getRating());
        if(requestBlogsEntity.isReportSpam() != originalBlogEntity.isReportSpam())
            originalBlogEntity.setReportSpam(!originalBlogEntity.isReportSpam());
        return originalBlogEntity;
    }

    public BlogsEntity addCommentToBlog(BlogsEntity.Comments comments, BlogsEntity originalBlogEntity) {

        if(comments.getCommentAuthor() != null && comments.getCommentText() != null &&
                comments.getCommentCreationDate() != null && comments.getCommentId() != null) {
            List<BlogsEntity.Comments> commentListToAdd = new ArrayList<BlogsEntity.Comments>();
            commentListToAdd.add(comments);

            if (originalBlogEntity.getComments().size() > 0)
                originalBlogEntity.getComments().add(comments);
            else
                originalBlogEntity.setComments(commentListToAdd);
        }
        return originalBlogEntity;
    }

    /**

     public BlogsEntity addCommentToBlog(BlogsEntity.Comments comments, BlogsEntity originalBlogEntity) {

     boolean flag = false;
     BlogsEntity.Comments commentToAdd = new BlogsEntity.Comments();
     List<BlogsEntity.Comments> commentListToAdd = new ArrayList<BlogsEntity.Comments>();
     List<BlogsEntity.AuthorComments> commentsList = new ArrayList<BlogsEntity.AuthorComments>();

     commentsList.add(comment);
     commentToAdd.setCommentAuthor(comment.getCommentAuthor());
     commentToAdd.setAuthorComments(commentsList);
     commentListToAdd.add(commentToAdd);

     if (originalBlogEntity.getComments() != null && originalBlogEntity.getComments().size() > 0) {
     List<BlogsEntity.Comments> commentsExisting = originalBlogEntity.getComments();
     for (BlogsEntity.Comments commentTemp : commentsExisting) {
     if (commentTemp.getCommentAuthor() == comment.getCommentAuthor()) {
     commentTemp.getAuthorComments().add(comment);
     flag = true;
     break;
     }
     }
     if (!flag) {
     originalBlogEntity.getComments().add(commentToAdd);
     }
     } else
     originalBlogEntity.setComments(commentListToAdd);
     return originalBlogEntity;
     }
     **/
}
