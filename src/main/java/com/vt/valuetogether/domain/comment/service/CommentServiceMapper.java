package com.vt.valuetogether.domain.comment.service;

import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;
import com.vt.valuetogether.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentServiceMapper {
    CommentServiceMapper INSTANCE = Mappers.getMapper(CommentServiceMapper.class);

    CommentSaveRes toCommentSaveRes(Comment comment);
}
