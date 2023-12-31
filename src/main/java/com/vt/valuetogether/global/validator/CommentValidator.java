package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.ACCESS_DENY;
import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_COMMENT;

import com.vt.valuetogether.domain.comment.entity.Comment;
import com.vt.valuetogether.global.exception.GlobalException;

public class CommentValidator {
    public static void validate(Comment comment) {
        if (isNullComment(comment)) {
            throw new GlobalException(NOT_FOUND_COMMENT);
        }
    }

    public static void checkCommentUser(String author, String accessor) {
        if (!author.equals(accessor)) {
            throw new GlobalException(ACCESS_DENY);
        }
    }

    private static boolean isNullComment(Comment comment) {
        return comment == null;
    }
}
