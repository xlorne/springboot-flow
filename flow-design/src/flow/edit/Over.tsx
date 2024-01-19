import React, {useEffect, useState} from "react";
import {Divider, Form, Input} from "antd";
import {PanelStyle} from "./components/PanelStyle";

const PREFIX = 'flowchart-editor';

export const Over: React.FC = (props: any) => {

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
                            disabled={true}
                            onChange={(value) => {
                                onNodeConfigChange('code', value.target.value);
                            }}/>
                    </Form.Item>
                </Form>
            </div>
            <Divider/>
            <PanelStyle
                {...props}
            />
        </div>
    );
};