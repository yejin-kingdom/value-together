package com.vt.valuetogether.domain.sample.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleGetResList {

    private List<SampleGetRes> sampleGetReses;
    private int total;
}
