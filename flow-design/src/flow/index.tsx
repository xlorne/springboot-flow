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
                    console.log(JSON.stringify(d));
                }}
                data={
                   {"nodes":[{"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","renderKey":"custom-node-indicator","name":"custom-node-indicator","label":"发起流程","width":120,"height":50,"ports":{"items":[{"group":"top","id":"500cccb4-add6-49a9-b382-c4a6c7b668fd"},{"group":"right","id":"313fb347-50ef-40e3-8594-0bb36842f165"},{"group":"bottom","id":"6b06679c-3465-4793-8ec2-9c6f71300652"},{"group":"left","id":"0af5f382-bf87-46e3-b816-ff787545c62f"}],"groups":{"top":{"position":{"name":"top"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"right":{"position":{"name":"right"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"bottom":{"position":{"name":"bottom"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"left":{"position":{"name":"left"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10}}},"originData":{"name":"custom-node-indicator","width":120,"height":50,"label":"自定义节点","parentKey":"custom"},"isCustom":true,"parentKey":"custom","x":380,"y":190,"zIndex":10,"incomingEdges":null,"outgoingEdges":[{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]},{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]}],"script":"start"},{"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","renderKey":"custom-node-indicator","name":"custom-node-indicator","label":"经理审批","width":120,"height":50,"ports":{"items":[{"group":"top","id":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},{"group":"right","id":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},{"group":"bottom","id":"1fc137eb-4153-4453-af47-7ced6c54028e"},{"group":"left","id":"40d1fef0-41c2-448b-8a86-317362c3d103"}],"groups":{"top":{"position":{"name":"top"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"right":{"position":{"name":"right"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"bottom":{"position":{"name":"bottom"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"left":{"position":{"name":"left"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10}}},"originData":{"name":"custom-node-indicator","width":120,"height":50,"label":"自定义节点","parentKey":"custom"},"isCustom":true,"parentKey":"custom","x":670,"y":80,"zIndex":10,"incomingEdges":[{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]}],"outgoingEdges":[{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]},{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:1fc137eb-4153-4453-af47-7ced6c54028e-node-8e1af430-6254-4669-b1bb-d6959bc8c714:90cf6a5b-9afa-473d-b480-16b82d6b7cc7","targetPortId":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePortId":"1fc137eb-4153-4453-af47-7ced6c54028e","zIndex":1,"data":{"targetPortId":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePortId":"1fc137eb-4153-4453-af47-7ced6c54028e","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"1fc137eb-4153-4453-af47-7ced6c54028e"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePortId":"1fc137eb-4153-4453-af47-7ced6c54028e","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:1fc137eb-4153-4453-af47-7ced6c54028e-node-8e1af430-6254-4669-b1bb-d6959bc8c714:90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePort":"1fc137eb-4153-4453-af47-7ced6c54028e","targetPort":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},"source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"1fc137eb-4153-4453-af47-7ced6c54028e"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},"labels":[{"targetPortId":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePortId":"1fc137eb-4153-4453-af47-7ced6c54028e","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"1fc137eb-4153-4453-af47-7ced6c54028e"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePortId":"1fc137eb-4153-4453-af47-7ced6c54028e","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:1fc137eb-4153-4453-af47-7ced6c54028e-node-8e1af430-6254-4669-b1bb-d6959bc8c714:90cf6a5b-9afa-473d-b480-16b82d6b7cc7","sourcePort":"1fc137eb-4153-4453-af47-7ced6c54028e","targetPort":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"}]}],"script":"manager"},{"id":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","renderKey":"custom-node-indicator","name":"custom-node-indicator","label":"结束","width":120,"height":50,"ports":{"items":[{"group":"top","id":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},{"group":"right","id":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},{"group":"bottom","id":"1fc137eb-4153-4453-af47-7ced6c54028e"},{"group":"left","id":"40d1fef0-41c2-448b-8a86-317362c3d103"}],"groups":{"top":{"position":{"name":"top"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"right":{"position":{"name":"right"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"bottom":{"position":{"name":"bottom"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"left":{"position":{"name":"left"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10}}},"originData":{"name":"custom-node-indicator","width":120,"height":50,"label":"自定义节点","parentKey":"custom"},"isCustom":true,"parentKey":"custom","x":1020,"y":190,"zIndex":10,"incomingEdges":[{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]},{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]}],"outgoingEdges":null,"groupChildren":null,"groupCollapsedSize":null,"originalId":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","isCollapsed":false,"script":"over"},{"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","renderKey":"custom-node-indicator","name":"custom-node-indicator","label":"总经理审批","width":120,"height":50,"ports":{"items":[{"group":"top","id":"90cf6a5b-9afa-473d-b480-16b82d6b7cc7"},{"group":"right","id":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},{"group":"bottom","id":"1fc137eb-4153-4453-af47-7ced6c54028e"},{"group":"left","id":"40d1fef0-41c2-448b-8a86-317362c3d103"}],"groups":{"top":{"position":{"name":"top"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"right":{"position":{"name":"right"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"bottom":{"position":{"name":"bottom"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10},"left":{"position":{"name":"left"},"attrs":{"circle":{"r":4,"magnet":true,"stroke":"#31d0c6","strokeWidth":2,"fill":"#fff","style":{"visibility":"hidden"}}},"zIndex":10}}},"originData":{"name":"custom-node-indicator","width":120,"height":50,"label":"自定义节点","parentKey":"custom"},"isCustom":true,"parentKey":"custom","x":670,"y":290,"zIndex":10,"incomingEdges":[{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]}],"outgoingEdges":[{"shape":"edge","attrs":{"line":{"stroke":"#A2B1C3","strokeWidth":1,"targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5"}},"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},"source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"labels":[{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]}],"script":"boss","groupChildren":null,"groupCollapsedSize":null,"originalId":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","isCollapsed":false}],"edges":[{"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8c90adf9-9f56-4078-b287-706dc45d6dfb:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},{"id":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8c90adf9-9f56-4078-b287-706dc45d6dfb","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},{"id":"node-8e1af430-6254-4669-b1bb-d6959bc8c714:128ee23b-0a6b-451d-bc11-4e1109fece6b-node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"128ee23b-0a6b-451d-bc11-4e1109fece6b"},"target":{"cell":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"128ee23b-0a6b-451d-bc11-4e1109fece6b","source":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","target":"node-a53c4195-94e5-4a5a-8fe3-5f2861f54a54","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"sourcePort":"128ee23b-0a6b-451d-bc11-4e1109fece6b","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"},{"id":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c:313fb347-50ef-40e3-8594-0bb36842f165-node-8e1af430-6254-4669-b1bb-d6959bc8c714:40d1fef0-41c2-448b-8a86-317362c3d103","targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":{"cell":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","port":"313fb347-50ef-40e3-8594-0bb36842f165"},"target":{"cell":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","port":"40d1fef0-41c2-448b-8a86-317362c3d103"},"attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}},"zIndex":1,"data":{"targetPortId":"40d1fef0-41c2-448b-8a86-317362c3d103","sourcePortId":"313fb347-50ef-40e3-8594-0bb36842f165","source":"node-6ec1fd8f-463e-4e0c-a76e-db7a627ca03c","target":"node-8e1af430-6254-4669-b1bb-d6959bc8c714","attrs":{"line":{"stroke":"#A2B1C3","targetMarker":{"name":"block","width":12,"height":8},"strokeDasharray":"5 5","strokeWidth":1}}},"sourcePort":"313fb347-50ef-40e3-8594-0bb36842f165","targetPort":"40d1fef0-41c2-448b-8a86-317362c3d103"}]}
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
                }}
                nodePanelProps={{
                    //隐藏通用节点
                    showOfficial:false,
                    position: {width: 160, top: 40, bottom: 0, left: 0},
                    defaultActiveKey: ['custom'], // ['custom', 'official']
                    //@ts-ignore
                    registerNode: {
                        title: '流程节点',
                        key: 'custom',
                        nodes: [
                            {
                                component: IndicatorNode,
                                popover: () => <div>指标节点</div>,
                                name: 'custom-node-indicator',
                                width: 120,
                                height: 50,
                                label: '自定义节点',
                            }                           
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