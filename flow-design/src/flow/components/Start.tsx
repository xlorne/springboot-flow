import React from "react";
import { Property } from "csstype";
import { SendOutlined, TeamOutlined, ApiOutlined } from "@ant-design/icons"
import {NodeDefaultConfig} from "./Node";

export const StartDefaultConfig = {
    name: 'flow-start',
    width: 160,
    height: 60,
    label: '开始',
    stroke: '#ccc',
    fill: '#fff',
    fontFill: '#000000',
    fontSize: 14,
    code: 'start',
    userValue: null,
    userType: 'AnyUsers',
    conditionType: 'RejectBack',
    conditionValue: null,
}

interface StartProps {
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

export const Start: React.FC<StartProps> = (props) => {
    const {
        size = {
            width: StartDefaultConfig.width,
            height: StartDefaultConfig.height
        },
        data
    } = props;
    const { width, height } = size;
    const {
        label = StartDefaultConfig.label,
        stroke = StartDefaultConfig.stroke,
        fill = StartDefaultConfig.fill,
        fontFill = StartDefaultConfig.fontFill,
        fontSize = StartDefaultConfig.fontSize,
        code = StartDefaultConfig.code,
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
                    backgroundColor: '#28b215',
                    width: 10,
                    height: '100%',
                    marginRight: 15
                }
            }></div>
            <SendOutlined style={{ fontSize: '24px', color: '#28b215' }} />
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