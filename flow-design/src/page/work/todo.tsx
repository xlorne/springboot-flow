import React from "react";
import {ActionType, PageContainer, ProTable} from "@ant-design/pro-components";
import {todo} from "@/api/work";
import {pass, recall, reject} from "@/api/leave";
import FlowBind from "@/page/work/bind";
import FlowConfirm from "@/page/work/confirm";

const TodoPage = () => {

    const actionRef = React.useRef<ActionType>();

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
                return <FlowBind text={recode.bind.reason} processId={recode.processId}/>;
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

                ]
            }
        },

    ]


    return (
        <PageContainer
            title="待办事项"
        >
            <ProTable
                rowKey={"id"}
                actionRef={actionRef}
                columns={columns}
                request={async (params, sort, filter) => {
                    return todo(params, sort, filter, []);
                }}
            />
        </PageContainer>
    )
}

export default TodoPage;
