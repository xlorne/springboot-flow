import React, {useEffect, useRef} from 'react';
import {Graph} from '@antv/x6';
import {register} from '@antv/x6-react-shape';
import {Dropdown} from 'antd';
import './index.less';

// @ts-ignore
const CustomComponent = ({node}) => {
    const label = node.prop('label');
    return (
        <Dropdown
            menu={{
                items: [
                    {key: 'copy', label: '复制'},
                    {key: 'paste', label: '粘贴'},
                    {key: 'delete', label: '删除'},
                ],
            }}
            trigger={['contextMenu']}
        >
            <div className="custom-react-node">{label}</div>
        </Dropdown>
    );
};

register({
    shape: 'custom-react-node',
    width: 100,
    height: 40,
    component: CustomComponent,
});

const data = {
    nodes: [
        {
            id: 'node1',
            shape: 'custom-react-node',
            x: 40,
            y: 40,
            label: 'hello',
            width: 80,
            height: 40,
        },
        {
            id: 'node2',
            shape: 'ellipse', // 使用 ellipse 渲染
            x: 160,
            y: 180,
            label: 'world',
            width: 80,
            height: 40,
        },
    ],
    edges: [
        {
            shape: 'edge',
            source: 'node1',
            target: 'node2',
            label: 'x6',
            attrs: {
                line: {
                    stroke: '#8f8f8f',
                    strokeWidth: 1,
                },
            },
        },
    ],
};

export const Flow = () => {
    const containerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const graph = new Graph({
            //@ts-ignore
            container: containerRef?.current,
            width: 800,
            height: 800,
            background: {
                color: '#fffbe6',
            },
            grid: {
                size: 10,      // 网格大小 10px
                visible: true, // 渲染网格背景
            },
        });

        graph.fromJSON(data);
        graph.centerContent();
    }, []);

    return (
        <div className="react-shape-app">
            <div className="app-content" ref={containerRef}/>
        </div>
    );
};

