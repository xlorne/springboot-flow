import React from "react";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import moment from "moment";
import {useNavigate} from "react-router-dom";
import {list} from "@/api/user";

const UserPage = () => {
    const navigate = useNavigate();

    const columns: any[] = [
        {
            dataIndex: 'id',
            title: 'ID',
            search: false,
        },
        {
            dataIndex: 'username',
            title: '账户',
        },
        {
            dataIndex: 'role',
            title: '权限',
            search: false,
        },
        {
            dataIndex: 'createTime',
            title: '创建时间',
            search: false,
            render: (text: any, record: any, index: any) => {
                return (
                    <span>{moment(record['createTime']).format('YYYY-MM-DD HH:mm:ss')}</span>
                )
            }
        },
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
                request={async (params,sort,filter) => {
                    return list(params,sort,filter,[]);
                }}
            />
        </PageContainer>
    )
}

export default UserPage;
