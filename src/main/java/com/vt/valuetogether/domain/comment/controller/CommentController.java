package com.vt.valuetogether.domain.comment.controller;

import com.vt.valuetogether.domain.comment.dto.request.CommentDeleteReq;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.response.CommentDeleteRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;
import com.vt.valuetogether.domain.comment.service.CommentService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public RestResponse<CommentSaveRes> saveComment(
            @RequestBody CommentSaveReq commentSaveReq,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentSaveReq.setUsername(userDetails.getUsername());
        return RestResponse.success(commentService.saveComment(commentSaveReq));
    }

    @DeleteMapping
    public RestResponse<CommentDeleteRes> deleteComment(
            @RequestBody CommentDeleteReq commentDeleteReq,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentDeleteReq.setUsername(userDetails.getUsername());
        return RestResponse.success(commentService.deleteComment(commentDeleteReq));
    }
}
