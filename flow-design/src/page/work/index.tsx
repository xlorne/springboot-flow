import React from "react";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import {todo} from "@/api/work";

const WorkPage = () => {

    const columns: any[] = [
        {
            dataIndex: 'id',
            title: '编号',
            search: false,
        },
        {
            dataIndex: 'name',
            title: '账户',
        },
        {
            dataIndex: 'password',
            title: '密码',
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

    ]


    return (
        <PageContainer>
            <ProTable
                rowKey={"id"}
                columns={columns}
                request={async (params,sort,filter) => {
                    return todo(params,sort,filter,[]);
                }}
            />
        </PageContainer>
    )
}

export default WorkPage;
