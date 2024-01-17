package com.example.flow.controller;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.service.FlowQuery;
import com.codingapi.flow.service.FlowService;
import com.codingapi.springboot.framework.dto.response.MultiResponse;
import com.codingapi.springboot.framework.dto.response.Response;
import com.codingapi.springboot.framework.dto.response.SingleResponse;
import com.example.flow.domain.User;
import com.example.flow.pojo.FlowSearchRequest;
import com.example.flow.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/flow")
@AllArgsConstructor
@Transactional
public class FlowController {

    private final FlowService flowService;
    private final FlowQuery flowQuery;
    private final UserRepository userRepository;

    @GetMapping("/todo")
    public MultiResponse<FlowRecord> todo(FlowSearchRequest request) {
        User user = userRepository.findByName(request.getName());
        return MultiResponse.of(flowQuery.todos(request.toPage(), user));
    }

    @GetMapping("/process")
    public MultiResponse<FlowRecord> process(FlowSearchRequest request) {
        User user = userRepository.findByName(request.getName());
        return MultiResponse.of(flowQuery.process(request.toPage(), user));
    }

    @GetMapping("/start")
    public SingleResponse<Long> start(HttpServletRequest request) {
        String name = request.getParameter("name");
        long workId = Long.parseLong(request.getParameter("workId"));
        User user = userRepository.findByName(name);
        return SingleResponse.of(flowService.createFlow(workId, user));
    }

    @GetMapping("/pass")
    public Response pass(HttpServletRequest request) {
        String name = request.getParameter("name");
        String option = request.getParameter("option");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.findByName(name);
        flowService.pass(recordId, option, user);
        return Response.buildSuccess();
    }

    @GetMapping("/reject")
    public Response reject(HttpServletRequest request) {
        String name = request.getParameter("name");
        String option = request.getParameter("option");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.findByName(name);
        flowService.reject(recordId, option, user);
        return Response.buildSuccess();
    }

    @GetMapping("/recall")
    public Response recall(HttpServletRequest request) {
        String name = request.getParameter("name");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.findByName(name);
        flowService.recall(recordId, user);
        return Response.buildSuccess();
    }

    @GetMapping("/back")
    public Response back(HttpServletRequest request) {
        String name = request.getParameter("name");
        String option = request.getParameter("option");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.findByName(name);
        flowService.back(recordId, option, user);
        return Response.buildSuccess();
    }

}
