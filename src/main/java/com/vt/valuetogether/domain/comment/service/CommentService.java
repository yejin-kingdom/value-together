package com.vt.valuetogether.domain.comment.service;

import com.vt.valuetogether.domain.comment.dto.request.CommentDeleteReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.response.CommentDeleteRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;

public interface CommentService {

    CommentSaveRes saveComment(CommentSaveReq commentSaveReq);

    CommentDeleteRes deleteComment(CommentDeleteReq commentDeleteReq);
}
