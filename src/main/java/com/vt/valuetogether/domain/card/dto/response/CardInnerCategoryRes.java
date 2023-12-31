package com.vt.valuetogether.domain.card.dto.response;

import com.vt.valuetogether.domain.worker.dto.response.WorkerGetRes;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardInnerCategoryRes {
    private Long cardId;
    private String name;
    private String description;
    private String fileUrl;
    private Double sequence;
    private String deadline;
    private List<WorkerGetRes> workers;

    @Builder
    private CardInnerCategoryRes(
            Long cardId,
            String name,
            String description,
            String fileUrl,
            Double sequence,
            String deadline,
            List<WorkerGetRes> workers) {
        this.cardId = cardId;
        this.name = name;
        this.description = description;
        this.fileUrl = fileUrl;
        this.sequence = sequence;
        this.deadline = deadline;
        this.workers = workers;
    }
}
