package com.codingapi.flow.domain;

import com.codingapi.flow.domain.em.FlowType;
import com.codingapi.flow.domain.trigger.FlowTriggerFactory;
import com.codingapi.flow.domain.trigger.IFlowTrigger;
import com.codingapi.flow.domain.user.FlowUserMatcherFactory;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.domain.user.IFlowUserMatcher;
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
    private FlowNode prev;

    /**
     * 流程触发条件
     */
    private IFlowTrigger flowTrigger;


    /**
     * 可见节点用户列表
     */
    private IFlowUserMatcher userMatcher;


    public FlowNode() {
        this.next = new ArrayList<>();
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


    public FlowNode copyNew() {
        return create(this.id, this.code, this.name, this.flowType, this.userMatcher, this.flowTrigger, this.count);
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

    public static FlowNode create(long id, String code, String name, FlowType flowType, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger, int count) {
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


    public static FlowNode create(long id, String code, String name, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger) {
        return create(id, code, name, FlowType.SERIAL, userMatcher, flowTrigger, 1);
    }

    public static FlowNode create(long id, String code, String name, FlowType flowType, IFlowUserMatcher userMatcher, IFlowTrigger flowTrigger) {
        return create(id, code, name, flowType, userMatcher, flowTrigger, 1);
    }


    public static FlowNode over(long id, String code, String name) {
        return create(id, code, name, FlowType.OVER, FlowUserMatcherFactory.noUsers(), FlowTriggerFactory.over(), 1);
    }

}
