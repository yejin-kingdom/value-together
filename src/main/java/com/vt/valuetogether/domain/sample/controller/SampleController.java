package com.vt.valuetogether.domain.sample.controller;

import com.vt.valuetogether.domain.sample.dto.SampleSaveReq;
import com.vt.valuetogether.domain.sample.dto.SampleSaveRes;
import com.vt.valuetogether.domain.sample.servce.SampleService;
import com.vt.valuetogether.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PostMapping
    public RestResponse<SampleSaveRes> saveSample(@RequestBody SampleSaveReq sampleSaveReq) {
        return RestResponse.success(sampleService.saveSample(sampleSaveReq));
    }
}
