package com.codingapi.flow.domain;

import com.codingapi.flow.domain.em.FlowState;
import com.codingapi.flow.domain.user.IFlowUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程记录
 */
@Setter
@Getter
public class FlowRecord {

    /**
     * 流程记录id
     */
    private long id;

    /**
     * 流程workId
     */
    private long workId;

    /**
     * 流程id
     */
    private long processId;

    /**
     * 当前节点名称
     */
    private String name;

    /**
     * 当前节点
     */
    @JsonIgnore
    private FlowNode node;

    /**
     * 已审批用户列表
     */
    private List<IFlowUser> users;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 审批状态
     */
    private FlowState state;

    /**
     * 更新时间
     */
    private long updateTime;

    /**
     * 审批意见
     */
    private String opinion;

    /**
     * 绑定的数据
     */
    private IBind bind;

    /**
     * 是否结束
     */
    private boolean finish;

    public FlowRecord() {
        this.users = new ArrayList<>();
    }

    /**
     * 同步审批意见
     *
     * @param user    审批用户
     * @param state   审批状态
     * @param opinion 审批意见
     */
    public void approval(IFlowUser user, FlowState state, String opinion) {
        this.state = state;
        this.users.add(user);
        this.opinion = opinion;
        this.updateTime = System.currentTimeMillis();
    }


    /**
     * 是否可以审批
     *
     * @param user 审批用户
     * @return true 已经审批 false 未被审批
     */
    public boolean isApproval(IFlowUser user) {
        if (this.state != FlowState.WAIT) {
            return containsUser(user);
        }
        return false;
    }

    /**
     * 是否包含审批用户
     *
     * @param user 审批用户
     * @return true 包含 false 不包含
     */
    public boolean containsUser(IFlowUser user) {
        for (IFlowUser flowUser : users) {
            if (flowUser.getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 撤回
     */
    public void recall() {
        this.state = FlowState.WAIT;
        this.updateTime = System.currentTimeMillis();
    }


    public void finish() {
        this.finish = true;
    }
}
