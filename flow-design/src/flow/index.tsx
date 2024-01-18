import React, {useEffect, useState} from 'react';
import {CanvasService, EdgeService, EditorPanels, Flowchart, FormWrapper, GroupService} from '@ant-design/flowchart';
import "@ant-design/flowchart/dist/index.css";
import "antd/dist/reset.css";

const IndicatorNode = (props: any) => {
    const {size = {width: 120, height: 50}, data} = props;
    const {width, height} = size;
    const {label = '自定义节点', stroke = '#ccc', fill = '#fff', fontFill, fontSize} = data;
    const {script} = data;
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
            <div style={{color: fontFill}}>{label}</div>
            {script && (
                <div>{script}</div>
            )}
        </div>
    );
};

const {InputFiled, ColorPicker, Position, InputNumberFiled, Size} = EditorPanels;

const PREFIX = 'flowchart-editor';


const NodeComponent = (props: any) => {
    const {config, plugin = {}} = props;
    const {updateNode} = plugin;
    const [nodeConfig, setNodeConfig] = useState({
        ...config,
    });
    //@ts-ignore
    const onNodeConfigChange = (key, value) => {
        setNodeConfig({
            ...nodeConfig,
            [key]: value,
        });
        updateNode({
            [key]: value,
        });
    };
    useEffect(() => {
        setNodeConfig({
            ...config,
        });
    }, [config]);
    return (
        <div className={`${PREFIX}-panel-body`}>
            <div className={`${PREFIX}-panel-group`}>
                <h5>内容</h5>
                <InputFiled
                    //@ts-ignore
                    label={nodeConfig.name === 'custom-node-image' ? '图片地址' : '标题'}
                    value={nodeConfig.label}
                    //@ts-ignore
                    onChange={(value) => {
                        onNodeConfigChange('label', value);
                    }}
                />
                <InputFiled
                    //@ts-ignore
                    label={'代码'}
                    value={nodeConfig.script}
                    //@ts-ignore
                    onChange={(value) => {
                        onNodeConfigChange('script', value);
                    }}
                />
            </div>
            <div className={`${PREFIX}-panel-group`} style={{borderBottom: 'none'}}>
                <h5>样式</h5>
                <Position
                    //@ts-ignore
                    x={nodeConfig.x}
                    y={nodeConfig.y}
                    //@ts-ignore
                    onChange={(key, value) => {
                        onNodeConfigChange(key, value);
                    }}
                />
                <Size
                    //@ts-ignore
                    width={nodeConfig.width}
                    height={nodeConfig.height}
                    //@ts-ignore
                    onChange={(key, value) => {
                        onNodeConfigChange(key, value);
                    }}
                />
                <ColorPicker
                    //@ts-ignore
                    label="填充"
                    value={nodeConfig.fill}
                    //@ts-ignore
                    onChange={(value) => {
                        onNodeConfigChange('fill', value);
                    }}
                />
                <ColorPicker
                    //@ts-ignore
                    label="边框"
                    value={nodeConfig.stroke}
                    //@ts-ignore
                    onChange={(value) => {
                        onNodeConfigChange('stroke', value);
                    }}
                />
                <div className={`${PREFIX}-node-text-style`}>
                    <InputNumberFiled
                        //@ts-ignore
                        label="字号"
                        value={nodeConfig.fontSize}
                        width={68}
                        //@ts-ignore
                        onChange={(value) => {
                            onNodeConfigChange('fontSize', value);
                        }}
                    />
                    <ColorPicker
                        //@ts-ignore
                        value={nodeConfig.fontFill}
                        //@ts-ignore
                        onChange={(value) => {
                            onNodeConfigChange('fontFill', value);
                        }}
                    />
                </div>
            </div>
        </div>
    );
};

const NodeService = (props: any) => {
    return (
        <FormWrapper {...props}>
            {(config, plugin) => <NodeComponent {...props} plugin={plugin} config={config}/>}
        </FormWrapper>
    );
};

export const controlMapService = (controlMap: any) => {
    controlMap.set('custom-node-service', NodeService);
    controlMap.set('custom-edge-service', EdgeService);
    controlMap.set('custom-group-service', GroupService);
    controlMap.set('custom-canvas-service', CanvasService);
    return controlMap;
};


const formSchemaService = async (args: any) => {
    const {targetType} = args;
    const isGroup = args.targetData?.isGroup;
    const groupSchema = {
        tabs: [
            {
                name: '设置',
                groups: [
                    {
                        name: 'groupName',
                        controls: [
                            {
                                label: '分组名',
                                name: 'custom-group-service',
                                shape: 'custom-group-service',
                                placeholder: '分组名称',
                            },
                        ],
                    },
                ],
            },
        ],
    };
    const nodeSchema = {
        tabs: [
            {
                name: '设置',
                groups: [
                    {
                        name: 'groupName',
                        controls: [
                            {
                                label: '节点名',
                                name: 'custom-node-service',
                                shape: 'custom-node-service',
                                placeholder: '节点名称',
                            },
                        ],
                    },
                ],
            },
        ],
    };
    const edgeSchema = {
        tabs: [
            {
                name: '设置',
                groups: [
                    {
                        name: 'groupName',
                        controls: [
                            {
                                label: '边',
                                name: 'custom-edge-service',
                                shape: 'custom-edge-service',
                                placeholder: '边名称',
                            },
                        ],
                    },
                ],
            },
        ],
    };
    if (isGroup) {
        return groupSchema;
    }
    if (targetType === 'node') {
        return nodeSchema;
    }
    if (targetType === 'edge') {
        return edgeSchema;
    }
    return {
        tabs: [
            {
                name: '设置',
                groups: [
                    {
                        name: 'groupName',
                        controls: [
                            {
                                label: '',
                                name: 'custom-canvas-service',
                                shape: 'custom-canvas-service',
                            },
                        ],
                    },
                ],
            },
        ],
    };
};

export const Flow = () => {
    return (
        <div style={{height: 600}}>
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
                    // showOfficial:false,
                    position: {width: 160, top: 40, bottom: 0, left: 0},
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
                    position: {width: 200, top: 40, bottom: 0, right: 0},
                    controlMapService,
                    formSchemaService
                }}
            />
        </div>
    );
};