import React from "react";
import {Popconfirm} from "antd";
import TextArea from "antd/es/input/TextArea";


interface FlowConfirmProps {
    title: string;
    onConfirm: (content:string) => void;
}

const FlowConfirm: React.FC<FlowConfirmProps> = (props) => {

    const [value, setValue] = React.useState<string>('');

    return (
        <Popconfirm
            title="流程审批"
            okText="提交"
            cancelText="取消"
            icon={null}
            onConfirm={(e) => {
                props.onConfirm(value);
                setValue('');
            }}
            description={
                <TextArea
                    value={value}
                    placeholder="请输入审批意见"
                    onChange={(e) => {
                        setValue(e.target.value);
                    }}/>
            }
            onCancel={() => {
                setValue('');
            }}
        >
            <a>{props.title}</a>
        </Popconfirm>
    )
}

export default FlowConfirm;
