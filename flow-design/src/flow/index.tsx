import React, {useEffect, useRef} from 'react';
import {Graph} from '@antv/x6';
import './index.less';

Graph.registerNode(
    'custom-node',
    {
        markup: [
            {
                tagName: 'rect',
                selector: 'body',
            },
            {
                tagName: 'text',
                selector: 'label',
            },
            {
                tagName: 'g',
                children: [
                    {
                        tagName: 'text',
                        selector: 'btnText',
                    },
                    {
                        tagName: 'rect',
                        selector: 'btn',
                    },
                ],
            },
        ],
        attrs: {
            btn: {
                refX: '100%',
                refX2: -28,
                y: 4,
                width: 24,
                height: 18,
                rx: 10,
                ry: 10,
                fill: 'rgba(255,255,0,0.01)',
                stroke: 'red',
                cursor: 'pointer',
                event: 'node:delete',
            },
            btnText: {
                fontSize: 14,
                fill: 'red',
                text: 'x',
                refX: '100%',
                refX2: -19,
                y: 17,
                cursor: 'pointer',
                pointerEvent: 'none',
            },
            body: {
                stroke: '#8f8f8f',
                strokeWidth: 1,
                fill: '#fff',
                rx: 6,
                ry: 6,
                refWidth: '100%',
                refHeight: '100%',
            },
            label: {
                fontSize: 14,
                fill: '#333333',
                refX: '50%',
                refY: '50%',
                textAnchor: 'middle',
                textVerticalAnchor: 'middle',
            },
        },
        ports: {
            groups: {
                top: {
                    position: 'top',
                    attrs: {
                        circle: {
                            r: 4,
                            magnet: true,
                            stroke: '#31d0c6',
                            strokeWidth: 2,
                            fill: '#fff',
                        },
                    },
                },
                right: {
                    position: 'right',
                    attrs: {
                        circle: {
                            r: 4,
                            magnet: true,
                            stroke: '#31d0c6',
                            strokeWidth: 2,
                            fill: '#fff',
                        },
                    },
                },
                bottom: {
                    position: 'bottom',
                    attrs: {
                        circle: {
                            r: 4,
                            magnet: true,
                            stroke: '#31d0c6',
                            strokeWidth: 2,
                            fill: '#fff',
                        },
                    },
                },
                left: {
                    position: 'left',
                    attrs: {
                        circle: {
                            r: 4,
                            magnet: true,
                            stroke: '#31d0c6',
                            strokeWidth: 2,
                            fill: '#fff',
                        },
                    },
                },
            },
            items: [
                {
                    id: 'port1',
                    group: 'top',
                },
                {
                    id: 'port2',
                    group: 'right',
                },
                {
                    id: 'port3',
                    group: 'bottom',
                },
                {
                    id: 'port4',
                    group: 'left',
                },
            ],
        }
    },
    true,
)
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
            connecting: {
                snap: true,
            },
            grid: {
                size: 10,      // 网格大小 10px
                visible: true, // 渲染网格背景
            },
        });

        //@ts-ignore
        graph.on('node:delete', ({view, e}) => {
            e.stopPropagation()
            view.cell.remove()
        })

        const source = graph.addNode({
            shape: 'custom-node',
            x: 40,
            y: 40,
            width: 120,
            height: 40,
            attrs: {
                label: {
                    text: 'Source',
                },
            },
        })

        const target = graph.addNode({
            shape: 'custom-node',
            x: 160,
            y: 240,
            width: 120,
            height: 40,
            attrs: {
                label: {
                    text: 'Target',
                },
            },
        })

        // graph.centerContent();
        // graph.fromJSON(data);
        //@ts-ignore
        graph.on('node:delete', ({view, e}) => {
            e.stopPropagation()
            view.cell.remove()
        })


    }, []);


    return (
        <div className="react-shape-app">
            <div className="app-content" ref={containerRef}/>
        </div>
    );
};

