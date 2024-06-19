import React from 'react';
import {Flow} from "@/flow";
import * as data from "@/data/data.json";

export const Design = () => {
    return (
        <Flow
            data={data}
            onSave={(data) => {
                // console.log(data);
                console.log(JSON.stringify(data));

                const nodes = data.data.nodes;

                const flows: any[] = [];
                for (const node of nodes) {
                    const flow = node.originData;
                    flow['nodeId'] = node.id;
                    flows.push(flow);

                    console.log(flow);
                }

                const getNode = (nodeId: string) => {
                    for (const node of flows) {
                        if (node.nodeId === nodeId) {
                            return node;
                        }
                    }
                    return null;
                }

                const edges = data.data.edges;
                for (const edge of edges) {
                    const source = edge.source.cell;
                    const target = edge.target.cell;
                    const sourceNode = getNode(source);
                    const targetNode = getNode(target);
                    console.log("edge:", sourceNode.label, '->', targetNode.label);
                }
            }}
        />
    )
}
