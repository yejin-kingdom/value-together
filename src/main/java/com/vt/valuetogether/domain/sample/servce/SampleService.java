package com.vt.valuetogether.domain.sample.servce;

import com.vt.valuetogether.domain.sample.dto.SampleSaveReq;
import com.vt.valuetogether.domain.sample.dto.SampleSaveRes;
import com.vt.valuetogether.domain.sample.entity.SampleEntity;
import com.vt.valuetogether.domain.sample.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleSaveRes saveSample(SampleSaveReq sampleSaveReq) {
        sampleRepository.save(SampleEntity.builder()
            .title(sampleSaveReq.getTitle())
            .text(sampleSaveReq.getText())
            .build());
        return new SampleSaveRes();
    }
}
