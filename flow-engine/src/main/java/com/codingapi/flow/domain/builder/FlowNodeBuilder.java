package com.codingapi.flow.domain.builder;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.exception.FlowBuilderException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FlowNodeBuilder {

    private final List<FlowNode> nodes;

    private FlowNodeBuilder() {
        this.nodes = new ArrayList<>();
    }

    public static FlowNodeBuilder builder() {
        return new FlowNodeBuilder();
    }

    public FlowNodeBuilder addNodes(FlowNode... nodes) {
        for (FlowNode node : nodes) {
            if (node.getNext() != null && !node.getNext().isEmpty()) {
                throw new FlowBuilderException("flow.build.error", "节点不能有下一节点，只能是单独的节点");
            }
            this.nodes.add(node);
        }
        return this;
    }


    public NodeRelation relations() {
        return new NodeRelation(nodes);
    }

    public static class NodeRelation {
        private final List<FlowNode> nodes;
        private FlowNode root;
        private FlowNode current;

        public NodeRelation(List<FlowNode> nodes) {
            this.nodes = nodes;
        }

        public NodeRelation start(String code) {
            FlowNode start = getNode(code,false);
            if (start == null) {
                throw new FlowBuilderException("flow.build.error", "开始节点不存在");
            }
            this.root = start;
            this.current = start;
            return this;
        }

        private FlowNode getNode(String code) {
            return getNode(code, true);
        }

        private FlowNode getNode(String code, boolean newCopy) {
            for (FlowNode n : nodes) {
                if (n.getCode().equals(code)) {
                    if (newCopy) {
                        return n.copyNew();
                    } else {
                        return n;
                    }
                }
            }
            return null;
        }

        public NodeRelation addNext(String code) {
            FlowNode next = getNode(code);
            if (next == null) {
                throw new FlowBuilderException("flow.build.error", "下一节点不存在");
            }
            if (next.isOver()) {
                throw new FlowBuilderException("flow.build.error", "结束节点不能再添加下一节点");
            }
            next.setPrev(this.current);
            this.current.addNext(next);
            this.current = next;
            return this;
        }

        public NodeRelation over(String code) {
            FlowNode over = getNode(code);
            if (over == null) {
                throw new FlowBuilderException("flow.build.error", "结束节点不存在");
            }
            this.current.addNext(over);
            this.current = over;
            return this;
        }

        public FlowNode build() {
            if (this.root == null) {
                throw new FlowBuilderException("flow.build.error", "节点尚未构建");
            }
            return this.root;
        }
    }


}
