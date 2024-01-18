import React from "react";
import {Property} from "csstype";

interface NodeProps {
    data: {
        label: string,
        stroke: Property.BorderColor,
        fill: Property.BorderColor,
        fontFill: Property.BorderColor,
        fontSize: Property.FontSize,
        script: string
    }
    size: {
        width: number,
        height: number
    }
}

export const Node: React.FC<NodeProps> = (props) => {
    const {
        size = {
            width: 120,
            height: 50
        },
        data
    } = props;
    const {width, height} = size;
    const {
        label = '自定义节点',
        stroke = '#ccc',
        fill = '#fff',
        fontFill = '#000000',
        fontSize = 12,
        script
    } = data;
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