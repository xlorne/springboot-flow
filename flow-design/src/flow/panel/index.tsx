import React from 'react';
import {CanvasService, EdgeService, FormWrapper, GroupService} from '@ant-design/flowchart';
import {Start} from "./Start";
import {Over} from "./Over";
import {Single} from "./Single";
import {Multiple} from "./Multiple";


const components = [
    {
        name: 'flow-start',
        component: Start,
    },
    {
        name: 'flow-over',
        component: Over,
    },
    {
        name: 'flow-single',
        component: Single,
    },
    {
        name: 'flow-multiple',
        component: Multiple,
    }

]


const NodeService = (props: any) => {
    return (
        <FormWrapper {...props}>
            {(config, plugin) => {
                // @ts-ignore
                const tag = components.find(item => item.name === config.name);
                const Component = tag?.component;
                if (Component==null) {
                    return null;
                }
                return (
                    <Component {...props} plugin={plugin} config={config}/>
                )
            }
            }
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
