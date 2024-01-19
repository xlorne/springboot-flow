import React, {useState} from "react";
import {EditorPanels} from "@ant-design/flowchart";

const {
    ColorPicker,
    Position,
    InputNumberFiled,
    Size
} = EditorPanels;

const PREFIX = 'flowchart-editor';

interface PanelStyleProps {
    config: any,
    plugin: any
}

export const PanelStyle:React.FC<PanelStyleProps> = (props)=>{
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

    return (
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
    )
}