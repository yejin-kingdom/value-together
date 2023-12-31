package com.vt.valuetogether.domain.card.dto.response;

import com.vt.valuetogether.domain.checklist.dto.response.ChecklistGetRes;
import com.vt.valuetogether.domain.comment.dto.response.CommentGetRes;
import com.vt.valuetogether.domain.worker.dto.response.WorkerGetRes;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardGetRes {
    private Long cardId;
    private String name;
    private String description;
    private String fileUrl;
    private Double sequence;
    private String deadline;
    private List<ChecklistGetRes> checklists;
    private List<WorkerGetRes> workers;
    private List<CommentGetRes> comments;

    @Builder
    private CardGetRes(
            Long cardId,
            String name,
            String description,
            String fileUrl,
            Double sequence,
            String deadline,
            List<ChecklistGetRes> checklists,
            List<WorkerGetRes> workers,
            List<CommentGetRes> comments) {
        this.cardId = cardId;
        this.name = name;
        this.description = description;
        this.fileUrl = fileUrl;
        this.sequence = sequence;
        this.deadline = deadline;
        this.checklists = checklists;
        this.workers = workers;
        this.comments = comments;
    }
}
