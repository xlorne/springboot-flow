package com.codingapi.flow.test;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.User;
import com.codingapi.flow.domain.builder.FlowNodeBuilder;
import com.codingapi.flow.domain.em.FlowType;
import com.codingapi.flow.domain.trigger.FlowTriggerFactory;
import com.codingapi.flow.domain.user.FlowUserMatcherFactory;
import com.codingapi.flow.exception.FlowBuilderException;
import com.codingapi.flow.gennerate.IdGeneratorRegister;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlowBuildTest {

    @BeforeAll
    static void registerIdGenerator() {
        new IdGeneratorRegister(System::nanoTime);
    }

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
                                    FlowNode.create(1, "start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                    FlowNode.create(2, "manager", "经理审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                    FlowNode.create(3, "end", "结束", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic())
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
                                FlowNode.create(1, "start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                FlowNode.create(2, "manager1", "经理1审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.create(3, "manager2", "经理1审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                FlowNode.create(4, "end", "结束", FlowType.OVER, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic())
                        )
                        .relations()

                        .start("start").addNext("manager1").over("end")
                        .start("start").addNext("manager2").over("end")
                        .start("start").addNext("manager1").addNext("manager2").over("end")
                        .start("start").over("end")

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
                                    FlowNode.create(1, "start", "发起请假", FlowType.SERIAL, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic()),
                                    FlowNode.create(2, "manager", "经理审批", FlowType.SERIAL, FlowUserMatcherFactory.users(manager), FlowTriggerFactory.basic()),
                                    FlowNode.create(3, "end", "结束", FlowType.OVER, FlowUserMatcherFactory.anyUsers(), FlowTriggerFactory.basic())
                            )
                            .relations()
                            .start("start").addNext("manager").addNext("end").addNext("manager")
                            .build();
        });
    }

}