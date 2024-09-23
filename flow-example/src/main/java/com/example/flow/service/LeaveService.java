package com.example.flow.service;

import com.codingapi.flow.service.FlowService;
import com.example.flow.domain.Leave;
import com.example.flow.domain.User;
import com.example.flow.repository.LeaveRepository;
import com.example.flow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class LeaveService {

    private final UserRepository userRepository;
    private final LeaveRepository leaveRepository;
    private final FlowService flowService;

    public void create(String username, String reason, long workId, int days){

        User user = userRepository.getByUsername(username);

        Leave leave = new Leave();
        leave.setDays(days);
        leave.setReason(reason);
        leave.setUsername(user.getUsername());
        leave.setCreateTime(System.currentTimeMillis());
        leave.setUserId(user.getId());
        leaveRepository.save(leave);

        flowService.createFlow(workId, user, leave);
    }

    public void pass(String username, String opinion, long recordId) {
        User user = userRepository.getByUsername(username);
        flowService.pass(recordId, opinion,user);
    }

    public void reject(String username, String opinion, long recordId) {
        User user = userRepository.getByUsername(username);
        flowService.reject(recordId, opinion,user);
    }

    public void recall(String username, long recordId) {
        User user = userRepository.getByUsername(username);
        flowService.recall(recordId, user);
    }

    public void back(String username,String opinion,  long recordId) {
        User user = userRepository.getByUsername(username);
        flowService.back(recordId,opinion, user);
    }
}
