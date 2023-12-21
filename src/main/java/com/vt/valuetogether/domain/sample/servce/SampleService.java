package com.vt.valuetogether.domain.sample.servce;

import com.vt.valuetogether.domain.sample.dto.request.SampleSaveReq;
import com.vt.valuetogether.domain.sample.dto.response.SampleGetRes;
import com.vt.valuetogether.domain.sample.dto.response.SampleGetResList;
import com.vt.valuetogether.domain.sample.dto.response.SampleSaveRes;
import com.vt.valuetogether.domain.sample.entity.SampleEntity;
import com.vt.valuetogether.domain.sample.repository.SampleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleSaveRes saveSample(SampleSaveReq sampleSaveReq) {
        return SampleServiceMapper.INSTANCE.toSampleSaveRes(
                sampleRepository.save(
                        SampleEntity.builder()
                                .title(sampleSaveReq.getTitle())
                                .text(sampleSaveReq.getText())
                                .build()));
    }

    public SampleGetResList getAllSamples() {
        List<SampleGetRes> sampleGetReses =
                SampleServiceMapper.INSTANCE.toSampleGetReses(sampleRepository.findAll());
        return SampleGetResList.builder()
                .sampleGetReses(sampleGetReses)
                .total(sampleGetReses.size())
                .build();
    }

    public SampleGetRes getSample(Long sampleId) {
        // TODO ADD validator
        return SampleServiceMapper.INSTANCE.toSampleGetRes(sampleRepository.findBySampleId(sampleId));
    }

    @Mapper
    public interface SampleServiceMapper {

        SampleServiceMapper INSTANCE = Mappers.getMapper(SampleServiceMapper.class);

        SampleSaveRes toSampleSaveRes(SampleEntity sampleEntity);

        SampleGetRes toSampleGetRes(SampleEntity sampleEntity);

        List<SampleGetRes> toSampleGetReses(List<SampleEntity> sampleEntities);
    }
}
