import React from 'react';
import {Flowchart} from '@ant-design/flowchart';
import "@ant-design/flowchart/dist/index.css";
import "antd/dist/reset.css";

const IndicatorNode = (props:any) => {
    const { size = { width: 120, height: 50 }, data } = props;
    const { width, height } = size;
    const { label = '自定义节点', stroke = '#ccc', fill = '#fff', fontFill, fontSize } = data;

    return (
        <div
            className="indicator-container"
            style={{
                position: 'relative',
                display: 'block',
                background: '#fff',
                border: '1px solid #84b2e8',
                borderRadius: '2px',
                padding: '10px 12px',
                overflow: 'hidden',
                boxShadow: '0 1px 4px 0 rgba(0,0,0,0.20)',
                width,
                height,
                borderColor: stroke,
                backgroundColor: fill,
                color: fontFill,
                fontSize,
            }}
        >
            <div style={{ color: fontFill }}>{label}</div>
        </div>
    );
};

export const Flow = () => {
    return (
        <div style={{ height: 600 }}>
            <Flowchart
                onSave={(d) => {
                    console.log(d);
                }}
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
                }}
                nodePanelProps={{
                    //隐藏通用节点
                    showOfficial:false,
                    position: { width: 160, top: 40, bottom: 0, left: 0 },
                    defaultActiveKey: ['custom'], // ['custom', 'official']
                    //@ts-ignore
                    registerNode: {
                        title: '指标节点',
                        key: 'custom',
                        nodes: [
                            {
                                component: IndicatorNode,
                                popover: () => <div>指标节点</div>,
                                name: 'custom-node-indicator',
                                width: 120,
                                height: 50,
                                label: '自定义节点',
                            },
                        ],
                    },
                }}
                detailPanelProps={{
                    position: { width: 200, top: 40, bottom: 0, right: 0 },
                }}
            />
        </div>
    );
};