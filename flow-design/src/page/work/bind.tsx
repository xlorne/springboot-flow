import React, {useEffect, useState} from "react";
import {Button, List, Modal, Tabs, TabsProps} from "antd";
import {ProDescriptions, ProTable} from "@ant-design/pro-components";
import moment from "moment/moment";
import {detail} from "@/api/work";

interface FlowBindDescriptionProps {
    body: any;
}

const FlowBindDescription: React.FC<FlowBindDescriptionProps> = (props) => {
    const {body} = props;
    if (body === null) {
        return (
            <></>
        )
    }
    const list = body.list;
    const bindData = list[0].bind;

    return (
        <>
            {bindData && (
                <ProDescriptions column={2}>
                    <ProDescriptions.Item label="申请人" key="username">
                        {bindData.username}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="创建时间" key="createTime">
                        {moment(bindData.createTime).format("YYYY-MM-DD HH:mm:ss")}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="请假原因" key="reason">
                        {bindData.reason}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="请假天数(天)" key="days">
                        {bindData.days}
                    </ProDescriptions.Item>
                </ProDescriptions>
            )}
        </>
    )
}

interface FlowNodeDescriptionProps {
    body: any;
}

const FlowNodeDescription: React.FC<FlowBindDescriptionProps> = (props) => {
    const {body} = props;
    if (body === null) {
        return (
            <></>
        )
    }
    const list = body.list;

    const columns = [
        {
            dataIndex: 'name',
            title: '节点名称',
        },
        {
            dataIndex: 'users',
            title: '审批人',
            render: (text: any, recode: any) => {
                if (recode.users) {
                    return recode.users.map((item:any)=>{
                        return item.username;
                    }).join(",");
                }
                return '-';
            }
        },
        {
            dataIndex: 'opinion',
            title: '审批意见',
        },
        {
            dataIndex: 'createTime',
            title: '发起时间',
            valueType:'dateTime'
        },
        {
            dataIndex: 'approval',
            title: '审批状态',
            render: (text: any, recode: any) => {
                return text?"通过":"拒绝";
            }
        },
        {
            dataIndex: 'updateTime',
            title: '审批时间',
            valueType:'dateTime'
        },
    ]

    return (


        <ProTable
            columns={columns}
            dataSource={list}
            search={false}
            options={false}
        />
    )
}

interface FlowBindProps {
    text: string
    processId: string;
}

const FlowBind: React.FC<FlowBindProps> = (props) => {

    const [visible, setVisible] = React.useState<boolean>(false);

    const [body, setBody] = useState<any>(null);

    const {processId, text} = props;

    useEffect(() => {
        if (visible) {
            detail(processId).then(res => {
                if (res.success) {
                    setBody(res.data);
                }
            });
        }
    }, [visible]);


    const items: TabsProps['items'] = [
        {
            key: '1',
            label: '流程详情',
            children: <FlowBindDescription body={body}/>,
        },
        {
            key: '2',
            label: '流程信息',
            children: <FlowNodeDescription body={body}/>,
        }
    ];

    return (
        <>
            <a onClick={() => {
                setVisible(true);
            }}>{text}</a>

            <Modal
                open={visible}
                title="审批详情"
                width={"60%"}
                onCancel={() => setVisible(false)}
                onOk={() => setVisible(false)}
                footer={(
                    <Button type={"primary"} onClick={() => {
                        setVisible(false);
                    }}>确定</Button>
                )}
            >
                <Tabs defaultActiveKey="1" items={items}/>
            </Modal>
        </>
    )

}

export default FlowBind;
