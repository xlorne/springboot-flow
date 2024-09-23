import React from "react";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import {list} from "@/api/user";

const UserPage = () => {

    const columns: any[] = [
        {
            dataIndex: 'id',
            title: '编号',
            search: false,
        },
        {
            dataIndex: 'username',
            title: '账户',
        },
        {
            dataIndex: 'password',
            title: '密码',
        },
        {
            dataIndex: 'role',
            title: '权限',
            search: false,
        },
    ]


    return (
        <PageContainer>
            <ProTable
                rowKey={"id"}
                columns={columns}
                request={async (params,sort,filter) => {
                    return list(params,sort,filter,[{
                        key: 'username',
                        type: 'LIKE'
                    }]);
                }}
            />
        </PageContainer>
    )
}

export default UserPage;
