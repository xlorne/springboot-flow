package com.example.flow.controller;

import com.codingapi.springboot.framework.dto.response.Response;
import com.codingapi.springboot.security.gateway.TokenContext;
import com.example.flow.command.LeaveCmd;
import com.example.flow.service.LeaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leave")
@AllArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/create")
    public Response create(@RequestBody LeaveCmd.CreateRequest request){
        request.setUsername(TokenContext.current().getUsername());
        leaveService.create(request.getUsername(),request.getReason(), request.getWorkId(), request.getDays());
        return Response.buildSuccess();
    }

    @PostMapping("/pass")
    public Response pass(@RequestBody LeaveCmd.PassRequest request){
        request.setUsername(TokenContext.current().getUsername());
        leaveService.pass(request.getUsername(),request.getOpinion(), request.getRecordId());
        return Response.buildSuccess();
    }

    @PostMapping("/reject")
    public Response reject(@RequestBody LeaveCmd.RejectRequest request){
        request.setUsername(TokenContext.current().getUsername());
        leaveService.reject(request.getUsername(),request.getOpinion(), request.getRecordId());
        return Response.buildSuccess();
    }

    @PostMapping("/recall")
    public Response recall(@RequestBody LeaveCmd.RecallRequest request){
        request.setUsername(TokenContext.current().getUsername());
        leaveService.recall(request.getUsername(),request.getRecordId());
        return Response.buildSuccess();
    }

    @PostMapping("/back")
    public Response back(@RequestBody LeaveCmd.BackRequest request){
        request.setUsername(TokenContext.current().getUsername());
        leaveService.back(request.getUsername(),request.getOpinion(),request.getRecordId());
        return Response.buildSuccess();
    }

}
