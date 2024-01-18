import React, {useEffect, useState} from 'react';
import {CanvasService, EdgeService, EditorPanels, FormWrapper, GroupService, NodeService} from '@ant-design/flowchart';

const {
    InputFiled,
    ColorPicker,
    Position,
    InputNumberFiled,
    Size
} = EditorPanels;

const PREFIX = 'flowchart-editor';


const NodePanel = (props: any) => {
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

// const NodeService = (props: any) => {
//     return (
//         <FormWrapper {...props}>
//             {(config, plugin) => <NodePanel {...props} plugin={plugin} config={config}/>}
//         </FormWrapper>
//     );
// };

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
