package com.example.flow.register;

import com.codingapi.flow.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.service.FlowService;
import com.codingapi.flow.trigger.FlowTriggerFactory;
import com.codingapi.flow.user.FlowUserMatcherFactory;
import com.example.flow.domain.Leave;
import com.example.flow.domain.User;
import com.example.flow.repository.LeaveRepository;
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

    private final LeaveRepository leaveRepository;

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


        User boss = new User();
        boss.setId(4L);
        boss.setName("总理");
        boss.setRole("boss");
        userRepository.save(boss);


        User admin = new User();
        admin.setId(5L);
        admin.setName("admin");
        admin.setRole("admin");
        userRepository.save(admin);
    }


    public void initFlow() {
        User admin = userRepository.findByName("admin");
        List<User> manager = userRepository.findByRole("manager");
        List<User> boss = userRepository.findByRole("boss");
        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.start(1, "start", "发起请假", FlowUserMatcherFactory.anyUsers(), new LeaveFlowTrigger()),
                                FlowNode.create(2, "manager", "经理审核", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic(), 1),
                                FlowNode.create(3, "boss", "总理审核", FlowType.SERIAL, FlowUserMatcherFactory.users(boss), FlowTriggerFactory.basic(), 1),
                                FlowNode.over(4, "over", "结束")
                        )
                        .edges()
                        .from("start").to("manager")
                        .from("manager").to("boss")
                        .from("boss").to("over")
                        .start("start")
                        .build();

        FlowWork work = new FlowWork(1, "请假流程", "这是流程说明", admin, flow);
        long workId = flowService.save(work);
        log.info("workId:{}", workId);
    }

    public void initLeave() {
        Leave leave = new Leave();
        leave.setId(1L);
        leave.setUserId(1L);
        leave.setDays(3.0f);
        leave.setReason("生病了");
        leaveRepository.save(leave);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUser();
        initFlow();
        initLeave();
    }
}
