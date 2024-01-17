package com.codingapi.flow.test;

import com.codingapi.flow.FlowConfiguration;
import com.codingapi.flow.domain.*;
import com.codingapi.flow.domain.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.em.FlowType;
import com.codingapi.flow.domain.trigger.FlowTriggerFactory;
import com.codingapi.flow.domain.trigger.IFlowTrigger;
import com.codingapi.flow.domain.user.FlowUserMatcherFactory;
import com.codingapi.flow.exception.FlowServiceException;
import com.codingapi.flow.service.FlowQuery;
import com.codingapi.flow.service.FlowService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlowTest {


    @BeforeAll
    static void registerIdGenerator() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        flowConfiguration.flowIdGeneratorRegister();
    }

    /**
     * 请假流程(通过)
     */
    @Test
    void test1() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();

        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        flowService.pass(managerTodo.getId(), "同意", manager);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, user);
        assertEquals(userTodos2.size(), 0);

        List<FlowRecord> managerTodos2 = flowQuery.todos(processId, manager);
        assertEquals(managerTodos2.size(), 0);

    }



    /**
     * 请假流程(通过)
     * 再次审批报错
     */
    @Test
    void test11() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();

        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        flowService.pass(managerTodo.getId(), "同意", manager);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, user);
        assertEquals(userTodos2.size(), 0);

        assertThrows(FlowServiceException.class,()->flowService.pass(managerTodo.getId(), "同意", manager));


    }

    /**
     * 请假流程(通过)
     * 非审核人员审核报错
     */
    @Test
    void test12() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();

        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        assertThrows(FlowServiceException.class,()->flowService.pass(managerTodo.getId(), "同意", admin));



    }


    /**
     * 请假流程(拒绝)
     */
    @Test
    void test2() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        flowService.reject(managerTodo.getId(), "不同意", manager);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, user);
        assertEquals(userTodos2.size(), 0);

        List<FlowRecord> managerTodos2 = flowQuery.todos(processId, manager);
        assertEquals(managerTodos2.size(), 0);

    }


    /**
     * 请假流程(退回)
     */
    @Test
    void test21() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        flowService.back(managerTodo.getId(), "不同意", manager);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, user);
        assertEquals(userTodos2.size(), 1);

        List<FlowRecord> managerTodos2 = flowQuery.todos(processId, manager);
        assertEquals(managerTodos2.size(), 0);

    }

    /**
     * 请假流程(撤回)
     */
    @Test
    void test22() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> userProcess = flowQuery.process(processId, user);
        assertEquals(userProcess.size(), 1);

        flowService.recall(userProcess.get(0).getId(), user);

        List<FlowRecord> userProcess2 = flowQuery.process(processId, user);
        assertEquals(userProcess2.size(), 0);

        List<FlowRecord> userTodos1 = flowQuery.todos(processId, user);
        assertEquals(userTodos1.size(), 1);

    }

    /**
     * 请假流程 (撤回) 异常
     */
    @Test
    void test221() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        flowService.pass(managerTodo.getId(), "同意", manager);

        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerProcess = flowQuery.process(processId, manager);
        assertEquals(managerProcess.size(), 1);

        assertThrows(FlowServiceException.class, () -> {
            flowService.recall(managerProcess.get(0).getId(), manager);
        });

    }


    /**
     * 请假流程 非审核人审核
     */
    @Test
    void test23() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");
        User user = new User(1, "小明");
        User manager = new User(2, "经理");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        assertThrows(FlowServiceException.class, () -> flowService.pass(managerTodo.getId(), "同意", admin));

    }

    /**
     * 请假流程 (角色审批)
     */
    @Test
    void test3() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");

        User user = new User(1, "小明");
        User manager1 = new User(2, "经理1", "manager");
        User manager2 = new User(2, "经理2", "manager");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowUserMatcherFactory.users(manager1, manager2), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager1);
        assertEquals(managerTodos.size(), 1);

        FlowRecord managerTodo = managerTodos.get(0);
        flowService.reject(managerTodo.getId(), "不同意", manager2);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, user);
        assertEquals(userTodos2.size(), 0);

        List<FlowRecord> managerTodos2 = flowQuery.todos(processId, manager1);
        assertEquals(managerTodos2.size(), 0);

    }

    /**
     * 请假流程 会签
     */
    @Test
    void test4() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");

        User user = new User(1, "小明");
        User manager = new User(2, "经理1", "manager");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.PARALLEL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.rate(0.6f), 5),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 5);

        FlowRecord managerTodo0 = managerTodos.get(0);
        FlowRecord managerTodo1 = managerTodos.get(1);
        FlowRecord managerTodo2 = managerTodos.get(2);

        flowService.pass(managerTodo0.getId(), "同意", manager);
        flowService.pass(managerTodo1.getId(), "同意", manager);
        flowService.pass(managerTodo2.getId(), "同意", manager);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, manager);
        assertEquals(userTodos2.size(), 0);


    }


    /**
     * 请假流程 会签
     */
    @Test
    void test41() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");

        User user = new User(1, "小明");
        User manager = new User(2, "经理1", "manager");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager", "manager", FlowType.PARALLEL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.rate(0.6f), 5),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);

        List<FlowRecord> managerTodos = flowQuery.todos(processId, manager);
        assertEquals(managerTodos.size(), 5);

        FlowRecord managerTodo0 = managerTodos.get(0);
        FlowRecord managerTodo1 = managerTodos.get(1);
        FlowRecord managerTodo2 = managerTodos.get(2);

        flowService.pass(managerTodo0.getId(), "同意", manager);
        flowService.pass(managerTodo1.getId(), "同意", manager);
        flowService.back(managerTodo2.getId(), "不同意", manager);

        List<FlowRecord> userTodos2 = flowQuery.todos(processId, manager);
        assertEquals(userTodos2.size(), 0);

        List<FlowRecord> newUserTodos = flowQuery.todos(processId, user);
        assertEquals(newUserTodos.size(), 1);

    }


    /**
     * 请假流程 条件审批
     */
    @Test
    void test5() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");

        User user = new User(1, "小明");
        User manager1 = new User(2, "经理1", "manager");
        User manager2 = new User(3, "经理2", "manager");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), new IFlowTrigger() {
                                    @Override
                                    public List<FlowNode> next(FlowNode node, FlowRecord... records) {
                                        FlowRecord current = records[0];
                                        User currentUser = (User) current.getUsers().get(0);
                                        if (currentUser.getId() > 10) {
                                            return node.getNextByCode("manager2");
                                        } else {
                                            return node.getNextByCode("manager1");
                                        }
                                    }
                                }),
                                FlowNode.create("manager1", "manager1", FlowType.SERIAL, FlowUserMatcherFactory.users(manager1), FlowTriggerFactory.basic()),
                                FlowNode.create("manager2", "manager2", FlowType.SERIAL, FlowUserMatcherFactory.users(manager2), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager1").over("end")
                        .start("start").addNext("manager2").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);


        long processId = flowService.createFlow(workId, user);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);


        List<FlowRecord> manager1Todos = flowQuery.todos(processId, manager1);
        assertEquals(manager1Todos.size(), 1);

        List<FlowRecord> manager2Todos = flowQuery.todos(processId, manager2);
        assertEquals(manager2Todos.size(), 0);

        flowService.pass(manager1Todos.get(0).getId(), "同意", manager1);

        List<FlowRecord> newManager1Todos = flowQuery.todos(processId, manager1);
        assertEquals(newManager1Todos.size(), 0);

        List<FlowRecord> newManager2Todos = flowQuery.todos(processId, manager2);
        assertEquals(newManager2Todos.size(), 0);

    }


    /**
     * 请假流程 条件审批
     */
    @Test
    void test51() {
        FlowConfiguration flowConfiguration = new FlowConfiguration();
        FlowService flowService = new FlowService(flowConfiguration.flowWorkRepository(), flowConfiguration.flowRecordRepository());
        FlowQuery flowQuery = new FlowQuery(flowConfiguration.flowRecordQuery());

        User admin = new User(100, "admin");

        User user = new User(1, "小明");
        User manager1 = new User(2, "经理1", "manager");
        User manager2 = new User(3, "经理2", "manager");


        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), new IFlowTrigger() {
                                    @Override
                                    public List<FlowNode> next(FlowNode node, FlowRecord... records) {
                                        FlowRecord current = records[0];
                                        Leave leave = (Leave) current.getBind();
                                        if (leave.getDays() >= 10) {
                                            return node.getNextByCode("manager2");
                                        } else {
                                            return node.getNextByCode("manager1");
                                        }
                                    }
                                }),
                                FlowNode.create("manager1", "manager1", FlowType.SERIAL, FlowUserMatcherFactory.users(manager1), FlowTriggerFactory.basic()),
                                FlowNode.create("manager2", "manager2", FlowType.SERIAL, FlowUserMatcherFactory.users(manager2), FlowTriggerFactory.basic()),
                                FlowNode.over("end", "结束")
                        )
                        .relations()
                        .start("start").addNext("manager1").over("end")
                        .start("start").addNext("manager2").over("end")
                        .build();


        FlowWork work = new FlowWork("请假流程", admin, flow);
        long workId = flowService.save(work);

        Leave leave = new Leave(1, "请假", user, 10);

        long processId = flowService.createFlow(workId, user, leave);


        List<FlowRecord> userTodos = flowQuery.todos(processId, user);
        assertEquals(userTodos.size(), 0);


        List<FlowRecord> manager1Todos = flowQuery.todos(processId, manager1);
        assertEquals(manager1Todos.size(), 0);

        List<FlowRecord> manager2Todos = flowQuery.todos(processId, manager2);
        assertEquals(manager2Todos.size(), 1);

        long recordId = manager2Todos.get(0).getId();
        // 修改请假天数
        Leave leaveData = (Leave) manager2Todos.get(0).getBind();
        leaveData.setDays(5);
        // 提交审批
        flowService.pass(recordId, "同意", manager2);

        List<FlowRecord> newManager1Todos = flowQuery.todos(processId, manager1);
        assertEquals(newManager1Todos.size(), 0);

        List<FlowRecord> newManager2Todos = flowQuery.todos(processId, manager2);
        assertEquals(newManager2Todos.size(), 0);

        assertEquals(leave.getDays(), 5);

    }
}