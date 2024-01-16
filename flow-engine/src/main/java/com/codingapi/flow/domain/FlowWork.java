package com.codingapi.flow.domain;

import com.codingapi.flow.domain.user.IFlowUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 流程work
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class FlowWork {

    private long id;
    private String title;
    private String description;
    private IFlowUser creator;
    private long createTime;

    private FlowNode flow;


    public FlowWork(int id, String title, String description, IFlowUser creator, FlowNode flow) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.createTime = System.currentTimeMillis();
        this.flow = flow;
    }

    public FlowWork(int id, String title, IFlowUser creator, FlowNode flow) {
        this(id, title, "", creator, flow);
    }

}
