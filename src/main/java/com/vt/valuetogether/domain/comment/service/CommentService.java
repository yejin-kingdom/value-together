package com.vt.valuetogether.domain.comment.service;

import com.vt.valuetogether.domain.comment.dto.request.CommentDeleteReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentUpdateReq;
import com.vt.valuetogether.domain.comment.dto.response.CommentDeleteRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentUpdateRes;

public interface CommentService {

    CommentSaveRes saveComment(CommentSaveReq commentSaveReq);

    CommentUpdateRes updateComment(CommentUpdateReq commentUpdateReq);

    CommentDeleteRes deleteComment(CommentDeleteReq commentDeleteReq);
}
