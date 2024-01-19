import React from "react";
import { Property } from "csstype";
import { PoweroffOutlined, TeamOutlined, ApiOutlined } from "@ant-design/icons"

export const OverDefaultConfig = {
    name: 'flow-over',
    width: 160,
    height: 60,
    label: '结束',
    stroke: '#ccc',
    fill: '#fff',
    fontFill: '#000000',
    fontSize: 16,
    code: 'over',
    users: null,
    conditions: null,
}

interface OverProps {
    data: {
        label: string,
        stroke: Property.BorderColor,
        fill: Property.BorderColor,
        fontFill: Property.BorderColor,
        fontSize: Property.FontSize,
        code: string,
        users: string,
        conditions: string,
    }
    size: {
        width: number,
        height: number
    }
}

export const Over: React.FC<OverProps> = (props) => {
    const {
        size = {
            width: OverDefaultConfig.width,
            height: OverDefaultConfig.height
        },
        data
    } = props;
    const { width, height } = size;
    const {
        label = OverDefaultConfig.label,
        stroke = OverDefaultConfig.stroke,
        fill = OverDefaultConfig.fill,
        fontFill = OverDefaultConfig.fontFill,
        fontSize = OverDefaultConfig.fontSize,
        code = OverDefaultConfig.code,
        users = OverDefaultConfig.users,
        conditions = OverDefaultConfig.conditions,
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
            <PoweroffOutlined style={{ fontSize: '24px', color: '#08c' }}/>
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
                    {users && (
                        <TeamOutlined style={{ marginLeft: 6, color: 'green' }} />
                    )}
                    {conditions && (
                        <ApiOutlined style={{ marginLeft: 6, color: 'green' }} />
                    )}
                </div>

            </div>
        </div>
    );
};