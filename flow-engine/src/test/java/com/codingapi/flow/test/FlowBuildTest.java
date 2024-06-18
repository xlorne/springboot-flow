package com.codingapi.flow.test;

import com.codingapi.flow.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.User;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.exception.FlowBuilderException;
import com.codingapi.flow.trigger.FlowTriggerFactory;
import com.codingapi.flow.user.FlowUserMatcherFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowBuildTest {


    /**
     * 结束以后不能再增加节点
     */
    @Test
    void test1() {
        User manager = new User(1, "经理");
        assertThrows(FlowBuilderException.class, () -> {
            FlowNode flow =
                    FlowNodeBuilder.builder()
                            .addNodes(
                                    FlowNode.start("发起请假", FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                    FlowNode.create("manager", "经理审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                    FlowNode.over("结束")
                            )
                            .relations()
                            .build();
        });
    }


    /**
     * 最好一个节点是结束节点
     */
    @Test
    void test2() {
        User manager = new User(1, "经理");

        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.start("start", "发起请假", FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager1", "经理1审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.create("manager2", "经理1审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.over("over", "结束")
                        )
                        .relations()

                        .start("start").addNext("manager1").over("over")
                        .start("start").addNext("manager2").over("over")
                        .start("start").addNext("manager1").addNext("manager2").over("over")
                        .start("start").over("over")

                        .build();

        System.out.println(flow);

        assertTrue(flow.getNext().get(0).getNext().get(0).isOver());
    }

    /**
     * 最好一个节点是结束节点
     */
    @Test
    void test21() {
        User manager = new User(1, "经理");

        assertThrows(FlowBuilderException.class, () -> {
            FlowNode flow =
                    FlowNodeBuilder.builder()
                            .addNodes(
                                    FlowNode.start("发起请假", FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                    FlowNode.create("manager", "经理审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                    FlowNode.over("结束")
                            )
                            .relations()
                            .start().addNext("manager").addNext("end").addNext("manager")
                            .build();
        });
    }


    /**
     * 最好一个节点是结束节点
     */
    @Test
    void test23() {
        User manager = new User(1, "经理");

        FlowNode flow =
                FlowNodeBuilder.builder()
                        .addNodes(
                                FlowNode.create("start", "发起请假", FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create("manager1", "经理1审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.create("manager2", "经理2审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.create("manager3", "经理3审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.create("over", "结束", FlowType.SERIAL, FlowUserMatcherFactory.noUsers(), FlowTriggerFactory.over())
                        )
                        .edges()
                        .from("start").to("manager1")
                        .from("manager1").to("manager2")
                        .from("manager2").to("manager3")
                        .from("manager3").to("manager1")
                        .from("manager2").to("over")
                        .start("start")
                        .build();

        System.out.println(flow);

        assertEquals(flow.getNext().get(0).getNext().get(0).getNext().size(), 2);
        assertEquals(flow.getNext().get(0).getNext().get(0).getNextByCode("over").size(), 1);
    }

}
