package com.example.flow.gateway;

import com.codingapi.flow.domain.IBind;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.gateway.FlowGateway;
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
        return (T) userRepository.getReferenceById(userId);
    }

    @Override
    public <T extends IBind> T getBind(long bindId, Class<T> clazz) {
        return (T) leaveRepository.getReferenceById(bindId);
    }
}
