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
import {Button, Popconfirm} from "antd";
import {back, create, pass, recall, reject} from "@/api/leave";
import FlowSelector from "@/page/flow/selector";
import FlowBind from "@/page/work/bind";
import TextArea from "antd/es/input/TextArea";
import FlowConfirm from "@/page/work/confirm";

const WorkPage = () => {

    const [visible, setVisible] = React.useState(false);

    const actionRef = React.useRef<ActionType>();

    const handleCreate = (values: any) => {
        create(values).then(res=>{
            console.log(res);
            actionRef.current?.reload();
            setVisible(false);
        });
    }

    const handlePass = (recordId:number,opinion:string)=>{
        pass({
            recordId,
            opinion
        }).then(res=>{
            actionRef.current?.reload();
        })
    }


    const handleReject = (recordId:number,opinion:string)=>{
        reject({
            recordId,
            opinion
        }).then(res=>{
            actionRef.current?.reload();
        })
    }


    const handleRecall = (recordId:number,opinion:string)=>{
        recall({
            recordId,
            opinion
        }).then(res=>{
            actionRef.current?.reload();
        })
    }


    const handleBack = (recordId:number,opinion:string)=>{
        back({
            recordId,
            opinion
        }).then(res=>{
            actionRef.current?.reload();
        })
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
                if (recode.node.next) {
                    return recode.node.next.map((item:any)=>{
                        return item.name;
                    }).join(",");
                }
                return '-';
            }
        },
        {
            dataIndex: 'bind',
            title: '审批内容',
            search: false,
            render:(text:any,recode:any)=>{
                return <FlowBind text={recode.bind.reason} body={recode.bind}/>;
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
            valueEnum: {
                'WAIT': {
                    text: '待审批',
                    status: 'Processing'
                },
                'PASS': {
                    text: '已通过',
                    status: 'Success'
                },
                'REJECT': {
                    text: '已拒绝',
                    status: 'Error'
                },
                'BACK': {
                    text: '已退回',
                    status: 'Warning'
                },
            },
        },
        {
            dataIndex: 'option',
            title: '操作',
            valueType: 'option',
            render: (text: any, recode: any) => {
                return [
                    <FlowConfirm onConfirm={(content)=>{
                        handlePass(recode.id,content);
                    }} title="通过"/>,
                    <FlowConfirm onConfirm={(content)=>{
                        handleReject(recode.id,content);
                    }} title="拒绝"/>,
                    <FlowConfirm onConfirm={(content)=>{
                        handleRecall(recode.id,content);
                    }} title="撤回"/>,
                    <FlowConfirm onConfirm={(content)=>{
                        handleBack(recode.id,content);
                    }} title="退回"/>,

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
