package com.codingapi.flow.infrastructure.entity;

import com.codingapi.flow.user.IFlowUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class FlowWorkEntity {

    @Id
    private long id;

    private String title;

    private String description;

    private Long creator;
    /**
     * 绑定的数据类型
     */
    private Class<? extends IFlowUser> creatorClass;

    private Long createTime;

    private Long flowId;
}
