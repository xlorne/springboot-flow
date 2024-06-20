package com.example.flow.controller;

import com.codingapi.springboot.framework.dto.response.Response;
import com.codingapi.springboot.security.gateway.TokenContext;
import com.example.flow.command.LeaveCmd;
import com.example.flow.service.LeaveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/leave")
@AllArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/create")
    public Response create(@RequestBody LeaveCmd.CreateRequest request){
        request.setUsername(TokenContext.current().getUsername());
        log.info("request:{}",request);
        leaveService.save(request.getUsername(),request.getReason(), request.getWorkId(), request.getDays());
        return Response.buildSuccess();
    }

}
