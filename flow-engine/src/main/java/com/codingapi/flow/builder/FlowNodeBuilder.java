package com.codingapi.flow.builder;

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

    public Edges edges() {
        return new Edges(nodes);
    }

    public static class Edges {
        private final List<FlowNode> nodes;

        public Edges(List<FlowNode> nodes) {
            this.nodes = nodes;
        }

        public BuildEdge start(String start) {
            return new BuildEdge(start);
        }

        public class BuildEdge {
            private final String start;

            public BuildEdge(String start) {
                this.start = start;
            }

            public FlowNode build() {
                if (this.start == null) {
                    throw new FlowBuilderException("flow.build.error", "开始节点尚未设置");
                }
                return getNode(start);
            }
        }


        private FlowNode getNode(String code) {
            for (FlowNode n : nodes) {
                if (n.getCode().equals(code)) {
                    return n;
                }
            }
            return null;
        }

        public ToEdge from(String code) {
            FlowNode startNode = getNode(code);
            if (startNode == null) {
                throw new FlowBuilderException("flow.build.error", "开始节点不存在");
            }
            return new ToEdge(startNode);
        }

        public class ToEdge {
            private final FlowNode current;

            public ToEdge(FlowNode current) {
                this.current = current;
            }

            public Edges to(String... codes) {
                if (current.isOver()) {
                    throw new FlowBuilderException("flow.build.error", "结束节点不能再添加下一节点");
                }

                for (String code : codes) {
                    FlowNode nextNode = getNode(code);
                    if (nextNode == null) {
                        throw new FlowBuilderException("flow.build.error", "下一节点不存在");
                    }
                    this.current.addNext(nextNode);
                }
                return Edges.this;
            }
        }
    }

    public static class NodeRelation {
        private final List<FlowNode> nodes;
        private FlowNode root;
        private FlowNode current;

        public NodeRelation(List<FlowNode> nodes) {
            this.nodes = nodes;
        }

        public NodeRelation start(String code) {
            FlowNode start = getNode(FlowNode.CODE_START, false);
            if (start == null) {
                throw new FlowBuilderException("flow.build.error", "开始节点不存在");
            }
            this.root = start;
            this.current = start;
            return this;
        }

        public NodeRelation start() {
            return start(FlowNode.CODE_START);
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

        public NodeRelation over() {
            return over(FlowNode.CODE_OVER);
        }

        public FlowNode build() {
            if (this.root == null) {
                throw new FlowBuilderException("flow.build.error", "节点尚未构建");
            }
            return this.root;
        }
    }


}
