package com.codingapi.flow.infrastructure.entity;

import com.codingapi.flow.domain.IBind;
import com.codingapi.flow.domain.em.FlowState;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.infrastructure.entity.converter.ListLongConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class FlowRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 流程workId
     */
    private Long workId;

    /**
     * 流程id
     */
    private Long processId;

    /**
     * 当前节点名称
     */
    private String name;

    /**
     * 当前节点
     */
    private Long nodeId;

    /**
     * 已审批用户列表
     */
    @Convert(converter = ListLongConverter.class)
    private List<Long> users;

    /**
     * 用户的数据类型
     */
    private Class<? extends IFlowUser> userClass;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 审批状态
     */
    private FlowState state;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 审批意见
     */
    private String opinion;

    /**
     * 绑定的数据
     */
    private Long bindId;

    /**
     * 绑定的数据类型
     */
    private Class<? extends IBind> bindClass;

    /**
     * 是否结束
     */
    private Boolean finish;

}
