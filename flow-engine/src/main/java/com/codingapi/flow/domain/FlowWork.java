package com.codingapi.flow.domain;

import com.codingapi.flow.user.IFlowUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private FlowNode flow;

    public FlowWork( String title, String description, IFlowUser creator, FlowNode flow) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.createTime = System.currentTimeMillis();
        this.flow = flow;
    }

    public FlowWork(String title, IFlowUser creator, FlowNode flow) {
        this(title, "", creator, flow);
    }

}
