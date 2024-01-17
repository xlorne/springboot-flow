package com.example.flow.controller;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.service.FlowQuery;
import com.codingapi.flow.service.FlowService;
import com.codingapi.springboot.framework.dto.response.MultiResponse;
import com.codingapi.springboot.framework.dto.response.SingleResponse;
import com.example.flow.domain.User;
import com.example.flow.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/flow")
@AllArgsConstructor
public class FlowController {

    private final FlowService flowService;
    private final FlowQuery flowQuery;
    private final UserRepository userRepository;

    @GetMapping("/todo")
    public MultiResponse<FlowRecord> todo(HttpServletRequest request) {
        String name = request.getParameter("name");
        long processId = Long.parseLong(request.getParameter("processId"));
        User user = userRepository.findByName(name);
        return MultiResponse.of(flowQuery.todos(processId, user));
    }

    @GetMapping("/process")
    public MultiResponse<FlowRecord> process(HttpServletRequest request) {
        String name = request.getParameter("name");
        long processId = Long.parseLong(request.getParameter("processId"));
        User user = userRepository.findByName(name);
        return MultiResponse.of(flowQuery.process(processId, user));
    }

    @GetMapping("/start")
    public SingleResponse<Long> start(HttpServletRequest request) {
        String name = request.getParameter("name");
        long workId = Long.parseLong(request.getParameter("workId"));
        User user = userRepository.findByName(name);
        return SingleResponse.of(flowService.createFlow(workId, user));
    }

}
