package com.example.flow.controller;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.query.FlowProcessQuery;
import com.codingapi.flow.service.FlowService;
import com.codingapi.springboot.framework.dto.response.MultiResponse;
import com.codingapi.springboot.framework.dto.response.Response;
import com.codingapi.springboot.framework.dto.response.SingleResponse;
import com.codingapi.springboot.security.gateway.TokenContext;
import com.example.flow.domain.Leave;
import com.example.flow.domain.User;
import com.example.flow.pojo.FlowSearchRequest;
import com.example.flow.repository.LeaveRepository;
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
@RequestMapping("/api/work")
@Transactional
@AllArgsConstructor
public class WorkController {

    private final FlowService flowService;
    private final FlowProcessQuery flowProcessQuery;
    private final UserRepository userRepository;
    private final LeaveRepository leaveRepository;

    @GetMapping("/todo")
    public MultiResponse<FlowRecord> todo(FlowSearchRequest request) {
        String username = TokenContext.current().getUsername();
        User user = userRepository.getByUsername(username);
        return MultiResponse.of(flowProcessQuery.todos(request.toPage(), user));
    }

    @GetMapping("/process")
    public MultiResponse<FlowRecord> process(FlowSearchRequest request) {
        String username = TokenContext.current().getUsername();
        User user = userRepository.getByUsername(username);
        return MultiResponse.of(flowProcessQuery.process(request.toPage(), user));
    }

    @GetMapping("/detail")
    public MultiResponse<FlowRecord> detail(HttpServletRequest request) {
        long processId = Long.parseLong(request.getParameter("processId"));
        return MultiResponse.of(flowProcessQuery.detail(processId));
    }

    @GetMapping("/start")
    public SingleResponse<Long> start(HttpServletRequest request) {
        String username = TokenContext.current().getUsername();
        long workId = Long.parseLong(request.getParameter("workId"));
        long leaveId = Long.parseLong(request.getParameter("leaveId"));
        User user = userRepository.getByUsername(username);
        Leave leave = leaveRepository.getLeaveById(leaveId);
        return SingleResponse.of(flowService.createFlow(workId, user, leave));
    }

    @GetMapping("/pass")
    public Response pass(HttpServletRequest request) {
        String username = TokenContext.current().getUsername();
        String option = request.getParameter("option");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.getByUsername(username);
        flowService.pass(recordId, option, user);
        return Response.buildSuccess();
    }

    @GetMapping("/reject")
    public Response reject(HttpServletRequest request) {
        String username = TokenContext.current().getUsername();
        String option = request.getParameter("option");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.getByUsername(username);
        flowService.reject(recordId, option, user);
        return Response.buildSuccess();
    }

    @GetMapping("/recall")
    public Response recall(HttpServletRequest request) {
        String username = TokenContext.current().getUsername();
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.getByUsername(username);
        flowService.recall(recordId, user);
        return Response.buildSuccess();
    }

    @GetMapping("/back")
    public Response back(HttpServletRequest request) {
        String username = TokenContext.current().getUsername();
        String option = request.getParameter("option");
        long recordId = Long.parseLong(request.getParameter("recordId"));
        User user = userRepository.getByUsername(username);
        flowService.back(recordId, option, user);
        return Response.buildSuccess();
    }

}
