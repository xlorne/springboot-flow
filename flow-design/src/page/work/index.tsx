import React from "react";
import {
    ActionType,
    ModalForm,
    PageContainer,
    ProFormDigit,
    ProFormTextArea,
    ProTable
} from "@ant-design/pro-components";
import {todo} from "@/api/work";
import {Button} from "antd";
import {create} from "@/api/leave";
import FlowSelector from "@/page/flow/selector";

const WorkPage = () => {

    const [visible, setVisible] = React.useState(false);

    const actionRef = React.useRef<ActionType>();

    const handleCreate = (values: any) => {
        console.log(values);
        create(values).then(res=>{
            console.log(res);
            actionRef.current?.reload();
            setVisible(false);
        });
    }

    const columns: any[] = [
        {
            dataIndex: 'id',
            title: '编号',
            search: false,
        },
        {
            dataIndex: 'name',
            title: '节点名称',
        },
        {
            dataIndex: 'node',
            title: '下一节点',
            search: false,
            render: (text: any, recode: any) => {
                if (recode.node) {
                    return recode.node.name;
                }
                return '-';
            }
        },
        {
            dataIndex: 'createTime',
            title: '创建时间',
            valueType: 'dateTime',
            search: false,
        },
        {
            dataIndex: 'updateTime',
            title: '更新时间',
            valueType: 'dateTime',
            search: false,
        },
        {
            dataIndex: 'opinion',
            title: '审批意见',
            valueType: 'dateTime',
            search: false,
        },
        {
            dataIndex: 'state',
            title: '审批状态',
            search: false,
        },
        {
            dataIndex: 'option',
            title: '操作',
            valueType: 'option',
            render: (text: any, recode: any) => {
                return [
                    <a key="pass">通过</a>,
                    <a key="reject">拒绝</a>,
                    <a key="recall">召回</a>,
                    <a key="back">退回</a>,
                ]
            }
        },

    ]


    return (
        <PageContainer>
            <ProTable
                rowKey={"id"}
                actionRef={actionRef}
                columns={columns}
                toolBarRender={() => {
                    return [
                        <Button type={'primary'}
                                onClick={() => {
                                    setVisible(true);
                                }}
                        >发起请假</Button>
                    ]
                }}
                request={async (params, sort, filter) => {
                    return todo(params, sort, filter, []);
                }}
            />

            <ModalForm
                title="发起请假"
                open={visible}
                modalProps={{
                    onCancel: () => {
                        setVisible(false);
                    },
                    destroyOnClose: true,
                }}
                onFinish={async (values) => {
                    handleCreate(values)
                    return true;
                }}
            >
                <FlowSelector
                    name="workId"
                    label="流程"
                    placeholder="请选择流程"
                />
                <ProFormTextArea
                    name="reason"
                    label="请假原因"
                    placeholder="请输入请假原因"
                />

                <ProFormDigit
                    min={0}
                    name="days"
                    label="请假天数"
                    placeholder="请输入请假天数"
                />

            </ModalForm>

        </PageContainer>
    )
}

export default WorkPage;
