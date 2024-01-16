package com.example.flow.register;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.domain.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.em.FlowType;
import com.codingapi.flow.domain.trigger.FlowTriggerFactory;
import com.codingapi.flow.domain.user.FlowUserMatcherFactory;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.service.FlowService;
import com.example.flow.domain.User;
import com.example.flow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FlowRegister implements InitializingBean {

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
                                FlowNode.create(1, "start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create(2, "manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager.toArray(new IFlowUser[]{})), FlowTriggerFactory.basic()),
                                FlowNode.over(3, "end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();

        FlowWork work = new FlowWork(1, "请假流程", admin, flow);
        flowService.save(work);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initUser();
        this.initFlow();
    }
}
