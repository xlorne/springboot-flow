package com.codingapi.flow.domain;

import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.trigger.FlowTriggerFactory;
import com.codingapi.flow.trigger.IFlowTrigger;
import com.codingapi.flow.user.FlowUserMatcherFactory;
import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.user.IFlowUserMatcher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程节点
 */
@Setter
@Getter
public class FlowNode {

    public static final String CODE_OVER = "over";
    public static final String CODE_START = "start";

    /**
     * 节点id
     */
    private long id;

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
    private int count;

    /**
     * 下一个节点
     */
    private List<FlowNode> next;


    /**
     * 上一个节点
     */
    @JsonIgnore
    private FlowNode prev;

    /**
     * 流程触发条件
     */
    @JsonIgnore
    private IFlowTrigger flowTrigger;


    /**
     * 可见节点用户列表
     */
    @JsonIgnore
    private IFlowUserMatcher userMatcher;


    public FlowNode() {
        this.next = new ArrayList<>();
    }

    /**
     * 设置下一个节点
     *
     * @param next 下一个节点
     */
    public void setNext(List<FlowNode> next) {
        this.next = next;
        if (next != null && !next.isEmpty()) {
            for (FlowNode node : next) {
                node.setPrev(this);
            }
        }
    }

    /**
     * 是否匹配用户
     *
     * @param user 用户
     * @return true 匹配 false 不匹配
     */
    public boolean matchUser(IFlowUser user) {
        return userMatcher.match(user);
    }

    /**
     * 是否是并行流程
     *
     * @return true 是 false 否
     */
    public boolean isParallel() {
        return this.flowType == FlowType.PARALLEL;
    }

    /**
     * 添加下一个节点
     *
     * @param flowNode 下一个节点
     */
    public void addNext(FlowNode flowNode) {
        flowNode.setPrev(this);
        this.next.add(flowNode);
    }

    /**
     * 触发下一个节点
     *
     * @param records 流程记录
     * @return 下一个节点
     */
    public List<FlowNode> trigger(FlowRecord... records) {
        return flowTrigger.next(this, records);
    }

    /**
     * 是否是结束节点
     */
    public boolean isOver() {
        return this.flowType == FlowType.OVER;
    }

    /**
     * 是否是发起节点
     */
    public boolean isStart() {
        return this.flowType == FlowType.START;
    }


    public FlowNode copyNew() {
        FlowNode flowNode = new FlowNode();
        flowNode.setId(id);
        flowNode.setCode(code);
        flowNode.setName(name);
        flowNode.setFlowType(flowType);
        flowNode.setCount(count);
        flowNode.setUserMatcher(userMatcher);
        flowNode.setFlowTrigger(flowTrigger);
        return flowNode;
    }

    public List<FlowNode> getNextByCode(String code) {
        List<FlowNode> nodes = new ArrayList<>();
        for (FlowNode node : next) {
            if (node.getCode().equals(code)) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    public static FlowNode start(String name, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger) {
        return start(CODE_START, name, userMatcher, flowTrigger);
    }

    public static FlowNode start(String code, String name, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger) {
        return create(code, name, FlowType.START, userMatcher, flowTrigger, 1);
    }


    public static FlowNode create(String code, String name, FlowType flowType, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger, int count) {
        FlowNode flowNode = new FlowNode();
        flowNode.setCode(code);
        flowNode.setName(name);
        flowNode.setFlowType(flowType);
        flowNode.setCount(count);
        flowNode.setUserMatcher(userMatcher);
        flowNode.setFlowTrigger(flowTrigger);
        return flowNode;
    }


    public static FlowNode create(String code, String name, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger) {
        return create(code, name, FlowType.SERIAL, userMatcher, flowTrigger, 1);
    }

    public static FlowNode create(String code, String name, FlowType flowType, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger) {
        return create(code, name, flowType, userMatcher, flowTrigger, 1);
    }

    public static FlowNode over(String name) {
        return over(CODE_OVER, name);
    }

    public static FlowNode over(String code, String name) {
        return create(code, name, FlowType.OVER, FlowUserMatcherFactory.noUsers(), FlowTriggerFactory.over(), 1);
    }


}
