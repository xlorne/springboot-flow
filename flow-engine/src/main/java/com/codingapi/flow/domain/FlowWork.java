package com.codingapi.flow.domain;

import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.gennerate.IdGeneratorContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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


    public FlowWork(String title, String description, IFlowUser creator, FlowNode flow) {
        this.id = IdGeneratorContext.getInstance().nextWorkId();
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.createTime = System.currentTimeMillis();
        this.flow = flow;
    }

    public FlowWork(String title, IFlowUser creator, FlowNode flow) {
        this(title, "", creator, flow);
    }


    public List<Long> matchUserNodes(IFlowUser currentUser) {
        List<Long> nodeIds = new ArrayList<>();
        Consumer<FlowNode> consumer = new Consumer<>() {
            @Override
            public void accept(FlowNode flowNode) {
                if (flowNode.matchUser(currentUser)) {
                    nodeIds.add(flowNode.getId());
                }
                if (flowNode.getNext() != null) {
                    flowNode.getNext().forEach(this);
                }
            }
        };
        consumer.accept(flow);
        return nodeIds;
    }
}
