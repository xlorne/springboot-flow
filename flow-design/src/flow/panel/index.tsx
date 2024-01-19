import React, {useEffect, useRef, useState} from 'react';
import {CanvasService, EdgeService, EditorPanels, FormWrapper, GroupService} from '@ant-design/flowchart';
import {Button, Divider, Form, Input, InputNumber, Modal, Select} from "antd";
import TextArea from "antd/es/input/TextArea";
import {CodeEditor} from "./code";


const {
    InputFiled,
    ColorPicker,
    Position,
    InputNumberFiled,
    Size
} = EditorPanels;

const PREFIX = 'flowchart-editor';

const names = new Map(
    [
        ['flow-start', '开始节点'],
        ['flow-over', '结束节点'],
        ['flow-node', '流程节点'],
    ]
);

const NodePanel = (props: any) => {

    const {config, plugin = {}} = props;
    const {updateNode} = plugin;
    const [nodeConfig, setNodeConfig] = useState({
        ...config,
    });

    const [showCode, setShowCode] = useState(false);
    const [code, setCode] = useState('');
    const [codeKey, setCodeKey] = useState('');
    const codeEditorRef = useRef(null);

    const nodeName = names.get(nodeConfig.name);
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
            <h4 style={{textAlign: 'center'}}>{nodeName}</h4>
            <div className={`${PREFIX}-panel-group`}>
                <Form
                    layout={"vertical"}>
                    <Form.Item
                        label="标题"
                    >
                        <Input value={nodeConfig.label} onChange={(value) => {
                            onNodeConfigChange('label', value.target.value);
                        }}/>
                    </Form.Item>

                    <Form.Item
                        label="代码"
                    >
                        <Input
                            value={nodeConfig.code ? nodeConfig.code : config.originData.code}
                            disabled={nodeConfig.name !== 'flow-node'}
                            onChange={(value) => {
                                onNodeConfigChange('code', value.target.value);
                            }}/>
                    </Form.Item>

                    {nodeConfig.name !== 'flow-over' && (
                        <>
                            <Form.Item
                                label="用户"

                            >
                                <Select
                                    style={{
                                        marginBottom: 5
                                    }}
                                    value={nodeConfig.userType ? nodeConfig.userType : config.originData.userType}
                                    onChange={(value, option) => {
                                        onNodeConfigChange('userType', value);
                                    }}>
                                    <Select.Option value="AnyUsers">所有人可见</Select.Option>
                                    <Select.Option value="NoUsers">所有人不可见</Select.Option>
                                    <Select.Option value="Users">指定用户可见</Select.Option>
                                    <Select.Option value="Custom">自定义</Select.Option>
                                </Select>

                                {nodeConfig.userType === 'Custom' && (
                                    <Button onClick={() =>{
                                        setCodeKey('userValue')
                                        setCode(nodeConfig.userValue ? nodeConfig.userValue : config.originData.userValue)
                                        setShowCode(true);
                                    }}>自定义代码</Button>
                                )}
                                {nodeConfig.userType === 'Users' && (
                                    <TextArea
                                        rows={5}
                                        value={nodeConfig.userValue ? nodeConfig.userValue : config.originData.userValue}
                                        onChange={(value) => {
                                            onNodeConfigChange('userValue', value.target.value);
                                        }}
                                    />
                                )}

                            </Form.Item>


                            <Form.Item
                                label="条件"
                            >
                                <Select
                                    style={{
                                        marginBottom: 5
                                    }}
                                    value={nodeConfig.conditionType ? nodeConfig.conditionType : config.originData.conditionType}
                                    onChange={(value, option) => {
                                        onNodeConfigChange('conditionType', value);
                                    }}>
                                    <Select.Option value="RejectBack">基础控制(拒绝返回上一阶段)</Select.Option>
                                    <Select.Option value="RejectNext">基础控制(拒绝进入下一阶段)</Select.Option>
                                    <Select.Option value="Rate">会签控制(超过比例)</Select.Option>
                                    <Select.Option value="Custom">自定义</Select.Option>
                                </Select>

                                {nodeConfig.conditionType === 'Rate' && (
                                    <InputNumber
                                        value={nodeConfig.conditionValue ? nodeConfig.conditionValue : config.originData.conditionValue}
                                        onChange={(value) => {
                                            onNodeConfigChange('conditionValue', value);
                                        }}
                                    />
                                )}
                                {nodeConfig.conditionType === 'Custom' && (
                                    <Button onClick={() =>{
                                        setCodeKey('conditionValue')
                                        setCode(nodeConfig.conditionValue ? nodeConfig.conditionValue : config.originData.conditionValue)
                                        setShowCode(true);
                                    }}>自定义代码</Button>
                                )}

                            </Form.Item>
                        </>
                    )}


                </Form>
            </div>
            <Divider/>
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
                    value={nodeConfig.fill ? nodeConfig.fill : config.originData.fill}
                    //@ts-ignore
                    onChange={(value) => {
                        onNodeConfigChange('fill', value);
                    }}
                />
                <ColorPicker
                    //@ts-ignore
                    label="边框"
                    value={nodeConfig.stroke ? nodeConfig.stroke : config.originData.stroke}
                    //@ts-ignore
                    onChange={(value) => {
                        onNodeConfigChange('stroke', value);
                    }}
                />
                <div className={`${PREFIX}-node-text-style`}>
                    <InputNumberFiled
                        //@ts-ignore
                        label="字号"
                        value={nodeConfig.fontSize ? nodeConfig.fontSize : config.originData.fontSize}
                        width={68}
                        //@ts-ignore
                        onChange={(value) => {
                            onNodeConfigChange('fontSize', value);
                        }}
                    />
                    <ColorPicker
                        //@ts-ignore
                        value={nodeConfig.fontFill ? nodeConfig.fontFill : config.originData.fontFill}
                        //@ts-ignore
                        onChange={(value) => {
                            onNodeConfigChange('fontFill', value);
                        }}
                    />
                </div>
            </div>

            <Modal
                open={showCode}
                width={800}
                onCancel={() => {
                    setShowCode(false)
                }}
                okText="确认"
                cancelText="取消"

                title="自定义代码"
                onOk={()=>{
                    // @ts-ignore
                    const code = codeEditorRef.current?.getValue();
                    onNodeConfigChange(codeKey, code);
                    setShowCode(false);
                }}
            >
                <CodeEditor value={code} ref={codeEditorRef}/>
            </Modal>
        </div>
    );
};

const NodeService = (props: any) => {
    return (
        <FormWrapper {...props}>
            {(config, plugin) => <NodePanel {...props} plugin={plugin} config={config}/>}
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


export const formSchemaService = async (args: any) => {
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
