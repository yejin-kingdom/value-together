package com.vt.valuetogether.domain.comment.repository;

import com.vt.valuetogether.domain.comment.entity.Comment;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository {
    Comment save(Comment comment);

    Comment findByCommentId(Long commentId);

    void delete(Comment comment);
}
