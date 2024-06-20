import React from "react";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import {todo} from "@/api/work";
import {Button} from "antd";

const WorkPage = () => {

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
            render: (text: any,recode:any) => {
                if(recode.node){
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
            render: (text: any,recode:any) => {
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
                columns={columns}
                toolBarRender={() => {
                    return [
                        <Button type={'primary'} onClick={()=>{
                        }}>发起请假</Button>
                    ]
                }}
                request={async (params,sort,filter) => {
                    return todo(params,sort,filter,[]);
                }}
            />
        </PageContainer>
    )
}

export default WorkPage;
