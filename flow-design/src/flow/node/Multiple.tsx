import React from "react";
import {Property} from "csstype";
import {ApiOutlined, TeamOutlined, UserSwitchOutlined} from "@ant-design/icons"

export const MultipleDefaultConfig = {
    name: 'flow-multiple',
    width: 180,
    height: 50,
    label: '会签节点',
    stroke: '#ccc',
    fill: '#fff',
    fontFill: '#000000',
    fontSize: 14,
    code: 'multiple',
    userValue: null,
    userType: 'AnyUsers',
    conditionType: 'RejectBack',
    conditionValue: null,
    count: 1,
    id: null,
}

interface MultipleProps {
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
        count: number,
        id: number,
    }
    size: {
        width: number,
        height: number
    }
}

export const Multiple: React.FC<MultipleProps> = (props) => {
    const {
        size = {
            width: MultipleDefaultConfig.width,
            height: MultipleDefaultConfig.height
        },
        data
    } = props;
    const { width, height } = size;
    const {
        label = MultipleDefaultConfig.label,
        stroke = MultipleDefaultConfig.stroke,
        fill = MultipleDefaultConfig.fill,
        fontFill = MultipleDefaultConfig.fontFill,
        fontSize = MultipleDefaultConfig.fontSize,
        code = MultipleDefaultConfig.code,
        userType = MultipleDefaultConfig.userType,
        userValue = MultipleDefaultConfig.userValue,
        conditionType = MultipleDefaultConfig.conditionType,
        conditionValue = MultipleDefaultConfig.conditionValue,
        count = MultipleDefaultConfig.count,
        id = MultipleDefaultConfig.id,
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
                    backgroundColor: '#0623b9',
                    width: 10,
                    height: '100%',
                    marginRight: 15
                }
            }></div>
            <TeamOutlined style={{fontSize: '18px', color: '#0623b9'}}/>
            <div style={{
                display: 'flex',
                flex: 3,
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                marginBottom: 6,
                marginTop: 6

            }}>
                <div style={{
                    color: fontFill,
                    fontWeight: 'bold',
                    marginBottom: 6,
                    width: '60px',
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
                    height: '100%',
                    display: 'flex',
                    flex: 1,
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
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