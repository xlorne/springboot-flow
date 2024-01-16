package com.codingapi.flow.infrastructure.entity;

import com.codingapi.flow.domain.em.FlowType;
import com.codingapi.flow.domain.trigger.IFlowTrigger;
import com.codingapi.flow.domain.user.IFlowUserMatcher;
import com.codingapi.flow.infrastructure.entity.converter.ListLongConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class FlowNodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 节点编码(流程内唯一)
     */
    private String code;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点类型
     */
    private FlowType flowType;

    /**
     * 执行次数 默认为1
     */
    private Integer count;

    /**
     * 下一个节点
     */
    @Convert(converter = ListLongConverter.class)
    private List<Long> next;

    /**
     * 上一个节点
     */
    private Long prev;

    /**
     * 流程触发条件
     */
    private IFlowTrigger trigger;


    /**
     * 可见节点用户列表
     */
    private IFlowUserMatcher userMatcher;


}
