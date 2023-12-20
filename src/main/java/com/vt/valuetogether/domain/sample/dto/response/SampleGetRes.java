package com.vt.valuetogether.domain.sample.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleGetRes {

    private Long sampleId;
    private String title;
    private String text;
}
