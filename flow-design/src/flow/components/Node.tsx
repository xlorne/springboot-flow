import React from "react";
import { Property } from "csstype";
import { SettingOutlined, TeamOutlined, ApiOutlined } from "@ant-design/icons"

export const NodeDefaultConfig = {
    name: 'flow-node',
    width: 160,
    height: 60,
    label: '流程节点',
    stroke: '#ccc',
    fill: '#fff',
    fontFill: '#000000',
    fontSize: 14,
    code: 'code',
    userValue: null,
    userType: 'AnyUsers',
    conditionType: 'RejectBack',
    conditionValue: null,
}

interface NodeProps {
    data: {
        label: string,
        stroke: Property.BorderColor,
        fill: Property.BorderColor,
        fontFill: Property.BorderColor,
        fontSize: Property.FontSize,
        code: string,
        userType: string,
        userValue:string,
        conditionType: string,
        conditionValue: string,
    }
    size: {
        width: number,
        height: number
    }
}

export const Node: React.FC<NodeProps> = (props) => {
    const {
        size = {
            width: NodeDefaultConfig.width,
            height: NodeDefaultConfig.height
        },
        data
    } = props;
    const { width, height } = size;
    const {
        label = NodeDefaultConfig.label,
        stroke = NodeDefaultConfig.stroke,
        fill = NodeDefaultConfig.fill,
        fontFill = NodeDefaultConfig.fontFill,
        fontSize = NodeDefaultConfig.fontSize,
        code = NodeDefaultConfig.code,
        userType = NodeDefaultConfig.userType,
        userValue = NodeDefaultConfig.userValue,
        conditionType = NodeDefaultConfig.conditionType,
        conditionValue = NodeDefaultConfig.conditionValue,
    } = data;
    return (
        <div
            className="indicator-container"
            style={{
                position: 'relative',
                borderRadius: '6px',
                display: 'flex',
                background: '#fff',
                border: '1px solid #84b2e8',
                overflow: 'hidden',
                boxShadow: '0 1px 4px 0 rgba(0,0,0,0.20)',
                width,
                height,
                borderColor: stroke,
                backgroundColor: fill,
                color: fontFill,
                fontSize: fontSize
            }}
        >
            <div style={
                {
                    backgroundColor: 'blue',
                    width: 10,
                    height: '100%',
                    marginRight: 15
                }
            }></div>
            <SettingOutlined style={{ fontSize: '24px', color: '#08c' }} />
            <div style={{
                display: 'flex',
                flex: 1,
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                marginBottom: 6,
                marginTop: 6

            }}>
                <div style={{ color: fontFill, fontWeight: 'bold', marginBottom: 6 }}>{label}</div>

                <div style={
                    {
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        color: fontFill,
                        fontSize: 14
                    }}>

                    {code && (
                        <div >{code}</div>
                    )}
                    {userType && (
                        <TeamOutlined style={{ marginLeft: 6, color: 'green' }} />
                    )}
                    {conditionType && (
                        <ApiOutlined style={{ marginLeft: 6, color: 'green' }} />
                    )}
                </div>

            </div>
        </div>
    );
};