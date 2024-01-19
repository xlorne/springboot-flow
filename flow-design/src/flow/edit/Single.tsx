import React, {useEffect, useRef, useState} from "react";
import {Button, Divider, Form, Input, Select} from "antd";
import TextArea from "antd/es/input/TextArea";
import {PanelStyle} from "./components/PanelStyle";
import {convertUsers} from "./utils";
import {CodeEditor} from "./components/CodeEditor";
import {CodeSandboxOutlined} from "@ant-design/icons";


const PREFIX = 'flowchart-editor';

export const Single: React.FC = (props: any) => {

    const {config, plugin = {}} = props;
    const {updateNode} = plugin;
    const [nodeConfig, setNodeConfig] = useState({
        ...config,
    });

    const [showCode, setShowCode] = useState(false);
    const [code, setCode] = useState('');
    const [codeKey, setCodeKey] = useState('');
    const codeEditorRef = useRef(null);

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
                        label="编码"
                    >
                        <Input
                            value={nodeConfig.code ? nodeConfig.code : config.originData.code}
                            onChange={(value) => {
                                onNodeConfigChange('code', value.target.value);
                            }}/>
                    </Form.Item>

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
                            <Button icon={<CodeSandboxOutlined/>} onClick={() => {
                                setCodeKey('userValue')
                                setCode(nodeConfig.userValue ? nodeConfig.userValue : config.originData.userValue)
                                setShowCode(true);
                            }}>Code</Button>
                        )}
                        {nodeConfig.userType === 'Users' && (
                            <TextArea
                                rows={5}
                                placeholder="请输入用户ID，多个用户ID用英文逗号分隔"
                                value={convertUsers(nodeConfig.userValue ? nodeConfig.userValue : config.originData.userValue)}
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
                            <Select.Option value="Custom">自定义</Select.Option>
                        </Select>

                        {nodeConfig.conditionType === 'Custom' && (
                            <Button icon={<CodeSandboxOutlined/>} onClick={() => {
                                setCodeKey('conditionValue')
                                setCode(nodeConfig.conditionValue ? nodeConfig.conditionValue : config.originData.conditionValue)
                                setShowCode(true);
                            }}>Code</Button>
                        )}

                    </Form.Item>
                </Form>
            </div>
            <Divider/>
            <PanelStyle
                {...props}
            />

            <CodeEditor
                show={showCode}
                value={code}
                onChange={(value) => {
                    onNodeConfigChange(codeKey, value);
                    setShowCode(false);
                }}/>
        </div>
    );
};