import React from "react";
import {ActionType, PageContainer, ProTable} from "@ant-design/pro-components";
import {process} from "@/api/work";
import {back} from "@/api/leave";
import FlowBind from "@/page/work/bind";
import FlowConfirm from "@/page/work/confirm";

const ProcessPage = () => {

    const [visible, setVisible] = React.useState(false);

    const actionRef = React.useRef<ActionType>();

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
                        handleBack(recode.id,content);
                    }} title="退回"/>,

                ]
            }
        },

    ]


    return (
        <PageContainer
            title="已办事项"
        >
            <ProTable
                rowKey={"id"}
                actionRef={actionRef}
                columns={columns}
                request={async (params, sort, filter) => {
                    return process(params, sort, filter, []);
                }}
            />

        </PageContainer>
    )
}

export default ProcessPage;
