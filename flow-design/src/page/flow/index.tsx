import React from "react";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import {Button} from "antd";
import {list} from "@/api/flow";
import {flowColumns} from "@/page/flow/type";

const FLowPage = () => {

    const columns: any[] = [
        ...flowColumns,
        {
            valueType: 'option',
            title: '操作',
            render: () => [
                <a>查看</a>,
                <a>编辑</a>,
                <a>删除</a>,
            ]
        },
    ]


    return (
        <PageContainer>
            <ProTable
                rowKey={"id"}
                columns={columns}
                toolBarRender={() => {
                    return [
                        <Button onClick={() => {
                        }}>新建流程</Button>
                    ]
                }}
                request={async (params, sort, filter) => {
                    return list(params, sort, filter, []);
                }}
            />
        </PageContainer>
    )
}

export default FLowPage;
