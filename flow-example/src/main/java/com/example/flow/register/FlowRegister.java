package com.example.flow.register;

import com.codingapi.flow.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.query.FlowProcessQuery;
import com.codingapi.flow.service.FlowService;
import com.codingapi.flow.trigger.FlowTriggerFactory;
import com.codingapi.flow.user.FlowUserMatcherFactory;
import com.example.flow.domain.User;
import com.example.flow.repository.UserRepository;
import com.example.flow.trigger.LeaveFlowTrigger;
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
        User user = userRepository.getByUsername("xiaoming");
        if (user == null) {
            user = new User();
            user.setUsername("xiaoming");
            user.setPassword("123456");
            user.setRole("user");
            userRepository.save(user);
        }

        User manager1 = userRepository.getByUsername("jingli1");
        if (manager1 == null) {
            manager1 = new User();
            manager1.setUsername("jingli1");
            manager1.setPassword("123456");
            manager1.setRole("manager");
            userRepository.save(manager1);
        }


        User manager2 = userRepository.getByUsername("jingli2");
        if (manager2 == null) {
            manager2 = new User();
            manager2.setUsername("jingli2");
            manager2.setPassword("123456");
            manager2.setRole("manager");
            userRepository.save(manager2);
        }


        User boss = userRepository.getByUsername("boss");
        if (boss == null) {
            boss = new User();
            boss.setUsername("boss");
            boss.setPassword("123456");
            boss.setRole("boss");
            userRepository.save(boss);
        }


        User admin = userRepository.getByUsername("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setRole("admin");
            userRepository.save(admin);
        }
    }

    public void initFlow() {
        User admin = userRepository.getByUsername("admin");
        List<User> manager = userRepository.findByRole("manager");
        List<User> boss = userRepository.findByRole("boss");

        FlowWork flowWork = flowService.get(1);
        if (flowWork == null) {
            FlowNode flow =
                    FlowNodeBuilder.builder()
                            .addNodes(
                                    FlowNode.start("start", "发起请假", FlowUserMatcherFactory.anyUsers(), new LeaveFlowTrigger()),
                                    FlowNode.create("manager", "经理审核", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic(), 1),
                                    FlowNode.create("boss", "总理审核", FlowType.SERIAL, FlowUserMatcherFactory.users(boss), FlowTriggerFactory.basic(), 1),
                                    FlowNode.over("over", "结束")
                            )
                            .edges()
                            .from("start").to("manager")
                            .from("start").to("boss")
                            .from("manager").to("boss")
                            .from("boss").to("over")
                            .start("start")
                            .build();

            flowWork = new FlowWork("请假流程", "这是流程说明", admin, flow);
            long workId = flowService.save(flowWork);
            log.info("workId:{}", workId);
        }
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUser();
        initFlow();
    }
}
