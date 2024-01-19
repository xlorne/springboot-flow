import React, {useRef} from 'react';
import {Flowchart} from '@ant-design/flowchart';
import "@ant-design/flowchart/dist/index.css";
import "antd/dist/reset.css";


import {controlMapService, formSchemaService} from "./panel"
import {components} from "./components";
import {message} from "antd";
import * as initData from "./data/data.json";

export const Flow = () => {

    let flowApp: any = null;
    let flowGraph: any = null;

    let hasStartNode = false;
    let hasOverNode = false;


    const initFlowNodeState = (data: any) => {
        const nodes = data.nodes;
        for (const node of nodes) {
            if (node.name === 'flow-start') {
                hasStartNode = true;
            }
            if (node.name === 'flow-over') {
                hasOverNode = true;
            }
        }
        return data;
    }

    const data = initFlowNodeState(initData);

    return (
        <div style={{height: '100vh'}}>
            <Flowchart
                onSave={async (d) => {
                    console.log(JSON.stringify(d));
                }}
                onReady={async (graph, app) => {
                    flowApp = app;
                    flowGraph = graph;
                }}
                onAddNode={async (d) => {
                    const currentNodeName = d.name;
                    if (currentNodeName === 'flow-start') {
                        if (hasStartNode) {
                            flowGraph.removeNode(d.id);
                            message.error('开始节点只能有一个');
                        }
                        hasStartNode = true;
                    }
                    if (currentNodeName === 'flow-over') {
                        if (hasOverNode) {
                            flowGraph.removeNode(d.id);
                            message.error('结束节点只能有一个');
                        }
                        hasOverNode = true;
                    }
                }}
                onDelNode={async (d) => {
                    const nodes = await flowApp.getAllNodes();
                    hasOverNode = false;
                    hasStartNode = false;
                    for(const node of nodes){
                        const currentNodeName = node['store']['data']['data']['name'];
                        if (currentNodeName === 'flow-start') {
                            hasStartNode = true;
                        }
                        if (currentNodeName === 'flow-over') {
                            hasOverNode = true;
                        }
                    }
                }}
                data={
                    data
                }
                toolbarPanelProps={{
                    position: {
                        top: 0,
                        left: 0,
                        right: 0,
                    },
                }}
                scaleToolbarPanelProps={{
                    layout: 'horizontal',
                    position: {
                        right: 0,
                        top: -40,
                    },
                    style: {
                        width: 150,
                        height: 39,
                        left: 'auto',
                        background: 'transparent',
                    },
                }}
                canvasProps={{
                    position: {
                        top: 40,
                        left: 0,
                        right: 0,
                        bottom: 0,
                    },
                    edgeConfig: {
                        attrs: {
                            line: {
                                stroke: 'green',
                                strokeDasharray: '0',
                                strokeWidth: 1,
                            },
                        },
                    }
                }}
                nodePanelProps={{
                    //隐藏通用节点
                    showOfficial: false,
                    position: {width: 260, top: 40, bottom: 0, left: 0},
                    defaultActiveKey: ['custom'], // ['custom', 'official']
                    //@ts-ignore
                    registerNode: {
                        title: '流程节点',
                        key: 'custom',
                        nodes: components,
                    },
                }}
                detailPanelProps={{
                    position: {width: 360, top: 40, bottom: 0, right: 0},
                    controlMapService,
                    formSchemaService
                }}
            />
        </div>
    );
};