import React from 'react';
import {Flowchart} from '@ant-design/flowchart';
import "@ant-design/flowchart/dist/index.css";
import "antd/dist/reset.css";

import {controlMapService, formSchemaService} from "./panel"
import * as data from "./data/data.json";
import {components} from "./components";

export const Flow = () => {
    return (
        <div style={{height: '100vh'}}>
            <Flowchart
                onSave={(d) => {
                    console.log(d);
                    console.log(JSON.stringify(d));
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
                    edgeConfig:{
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
                    position: {width: 260, top: 40, bottom: 0, right: 0},
                    controlMapService,
                    formSchemaService
                }}
            />
        </div>
    );
};