package com.example.flow.register;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.domain.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.trigger.FlowTriggerFactory;
import com.codingapi.flow.domain.user.FlowUserMatcherFactory;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.service.FlowService;
import com.example.flow.domain.User;
import com.example.flow.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class FlowRegister implements ApplicationRunner {

    private final UserRepository userRepository;

    private final FlowService flowService;

    private void initUser() {
        User user = new User();
        user.setId(1L);
        user.setName("小明");
        user.setRole("user");
        userRepository.save(user);

        User manager1 = new User();
        manager1.setId(2L);
        manager1.setName("经理1");
        manager1.setRole("manager");
        userRepository.save(manager1);


        User manager2 = new User();
        manager2.setId(3L);
        manager2.setName("经理2");
        manager2.setRole("manager");
        userRepository.save(manager2);

        User admin = new User();
        admin.setId(4L);
        admin.setName("admin");
        admin.setRole("admin");
        userRepository.save(admin);
    }


    public void initFlow() {
        User admin = userRepository.findByName("admin");
        List<User> manager = userRepository.findByRole("manager");
        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create( "manager", "manager", FlowUserMatcherFactory.users(manager.toArray(new IFlowUser[]{})), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();

        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);
        log.info("workId:{}", workId);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUser();
        initFlow();
    }
}
