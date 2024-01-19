import React from "react";
import {Property} from "csstype";
import {ApiOutlined, SendOutlined, UserSwitchOutlined} from "@ant-design/icons"

export const StartDefaultConfig = {
    name: 'flow-start',
    width: 180,
    height: 50,
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
        userValue: string,
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
    const {width, height} = size;
    const {
        label = StartDefaultConfig.label,
        stroke = StartDefaultConfig.stroke,
        fill = StartDefaultConfig.fill,
        fontFill = StartDefaultConfig.fontFill,
        fontSize = StartDefaultConfig.fontSize,
        code = StartDefaultConfig.code,
        userType = StartDefaultConfig.userType,
        userValue = StartDefaultConfig.userValue,
        conditionType = StartDefaultConfig.conditionType,
        conditionValue = StartDefaultConfig.conditionValue,
    } = data;
    return (
        <div
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
                    backgroundColor: '#47e533',
                    width: 10,
                    height: '100%',
                    marginRight: 15
                }
            }></div>
            <SendOutlined style={{fontSize: '24px', color: '#47e533'}}/>
            <div style={{
                display: 'flex',
                flex: 1,
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                marginBottom: 6,
                width: '60px',
                marginTop: 6
            }}>
                <div style={{
                    color: fontFill,
                    fontWeight: 'bold',
                    marginBottom: 6,
                    textAlign: 'center'
                }}>{label}</div>
                {code && (
                    <div style={
                        {
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            color: fontFill,
                            fontSize: 14
                        }}>
                        {code}
                    </div>
                )}
            </div>

            <div style={
                {
                    width: '20px',
                    height: '100%',
                    display: 'flex',
                    flex: 1,
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    marginRight: 3
                }
            }>
                {userType && (
                    <UserSwitchOutlined style={{
                        color: '#9fab20',
                    }}/>
                )}
                {conditionType && (
                    <ApiOutlined style={{
                        color: '#9fab20',
                        marginTop: '10px'
                    }}/>
                )}
            </div>
        </div>
    );
};