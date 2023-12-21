package com.vt.valuetogether.domain.sample.entity;

import com.vt.valuetogether.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_sample")
public class SampleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleId;

    private String title;
    private String text;

    @Builder
    private SampleEntity(Long sampleId, String title, String text) {
        this.sampleId = sampleId;
        this.title = title;
        this.text = text;
    }
}
