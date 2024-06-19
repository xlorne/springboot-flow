package com.example.flow.controller;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.repository.FlowWorkQuery;
import com.codingapi.springboot.framework.dto.request.SearchRequest;
import com.codingapi.springboot.framework.dto.response.MultiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flow")
@AllArgsConstructor
public class FlowController {

    private final FlowWorkQuery flowWorkQuery;

    @GetMapping("/list")
    public MultiResponse<FlowWork> list(SearchRequest request) {
        return MultiResponse.of(flowWorkQuery.list(request));
    }
}
