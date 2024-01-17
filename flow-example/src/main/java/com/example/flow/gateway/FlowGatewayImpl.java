package com.example.flow.gateway;

import com.codingapi.flow.bind.IBind;
import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.gateway.FlowGateway;
import com.codingapi.springboot.fast.manager.EntityManagerContent;
import com.example.flow.repository.LeaveRepository;
import com.example.flow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class FlowGatewayImpl implements FlowGateway {

    private final UserRepository userRepository;
    private final LeaveRepository leaveRepository;

    @Override
    public <T extends IFlowUser> T getUser(long userId, Class<T> clazz) {
        T data =  (T) userRepository.getUserById(userId);
        EntityManagerContent.getInstance().detach(data);
        return data;
    }

    @Override
    public <T extends IBind> T getBind(long bindId, Class<T> clazz) {
        T data =  (T) leaveRepository.getLeaveById(bindId);
        EntityManagerContent.getInstance().detach(data);
        return data;
    }
}
