import React from "react";
import {Modal} from "antd";
import {ProDescriptions} from "@ant-design/pro-components";
import moment from "moment/moment";

interface FlowBindProps {
    text: string;
    body: any;
}

const FlowBind:React.FC<FlowBindProps> = (props)=>{

    const {text,body} = props;

    const [visible, setVisible] = React.useState<boolean>(false);

    return (
        <>
            <a onClick={()=>{
                setVisible(true);
            }}>{text}</a>

            <Modal
                open={visible}
                title="审批内容"
                width={800}
                onCancel={()=>setVisible(false)}
                onOk={()=>setVisible(false)}
            >
                <ProDescriptions>
                    <ProDescriptions.Item label="编号" key="id">
                        {body.id}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="申请人" key="userId">
                        {body.userId}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="创建时间" key="createTime">
                        {moment(body.createTime).format("YYYY-MM-DD HH:mm:ss")}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="请假原因" key="reason">
                        {body.reason}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="请假天数" key="days">
                        {body.days}
                    </ProDescriptions.Item>
                </ProDescriptions>
            </Modal>
        </>
    )

}

export default FlowBind;
