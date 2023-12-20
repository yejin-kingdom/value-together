package com.vt.valuetogether.domain.sample.repository;

import com.vt.valuetogether.domain.sample.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

    SampleEntity findBySampleId(Long sampleId);
}
