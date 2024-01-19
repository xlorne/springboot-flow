import React from "react";
import {Property} from "csstype";
import {ApiOutlined, PoweroffOutlined, UserSwitchOutlined} from "@ant-design/icons"

export const OverDefaultConfig = {
    name: 'flow-over',
    width: 180,
    height: 50,
    label: '结束',
    stroke: '#ccc',
    fill: '#fff',
    fontFill: '#000000',
    fontSize: 14,
    code: 'over',
}

interface OverProps {
    data: {
        label: string,
        stroke: Property.BorderColor,
        fill: Property.BorderColor,
        fontFill: Property.BorderColor,
        fontSize: Property.FontSize,
        code: string,
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
                    backgroundColor: '#b71818',
                    width: 10,
                    height: '100%',
                    marginRight: 15
                }
            }></div>
            <PoweroffOutlined style={{fontSize: '24px', color: '#b71818'}}/>
            <div style={{
                display: 'flex',
                flex: 1,
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

            </div>
        </div>
    );
};