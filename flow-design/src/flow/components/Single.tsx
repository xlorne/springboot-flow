import React from "react";
import {Property} from "csstype";
import {ApiOutlined, UserOutlined, UserSwitchOutlined} from "@ant-design/icons"

export const SingleDefaultConfig = {
    name: 'flow-single',
    width: 160,
    height: 60,
    label: '或签节点',
    stroke: '#ccc',
    fill: '#fff',
    fontFill: '#000000',
    fontSize: 14,
    code: 'single',
    userValue: null,
    userType: 'AnyUsers',
    conditionType: 'RejectBack',
    conditionValue: null,
}

interface SingleProps {
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

export const Single: React.FC<SingleProps> = (props) => {
    const {
        size = {
            width: SingleDefaultConfig.width,
            height: SingleDefaultConfig.height
        },
        data
    } = props;
    const { width, height } = size;
    const {
        label = SingleDefaultConfig.label,
        stroke = SingleDefaultConfig.stroke,
        fill = SingleDefaultConfig.fill,
        fontFill = SingleDefaultConfig.fontFill,
        fontSize = SingleDefaultConfig.fontSize,
        code = SingleDefaultConfig.code,
        userType = SingleDefaultConfig.userType,
        userValue = SingleDefaultConfig.userValue,
        conditionType = SingleDefaultConfig.conditionType,
        conditionValue = SingleDefaultConfig.conditionValue,
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
                    backgroundColor: '#405bec',
                    width: 10,
                    height: '100%',
                    marginRight: 15
                }
            }></div>
            <UserOutlined  style={{ fontSize: '24px', color: '#08c' }} />
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
                        <UserSwitchOutlined style={{ marginLeft: 6, color: 'green' }} />
                    )}
                    {conditionType && (
                        <ApiOutlined style={{ marginLeft: 6, color: 'green' }} />
                    )}
                </div>

            </div>
        </div>
    );
};