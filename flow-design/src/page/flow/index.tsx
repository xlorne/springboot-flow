import React from "react";
import axios from "axios";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import moment from "moment";
import {Button} from "antd";
import {useNavigate} from "react-router-dom";

const FLowPage = () => {
    const navigate = useNavigate();

    const columns: any[] = [
        {
            dataIndex: 'id',
            title: 'ID',
            search: false,
        },
        {
            dataIndex: 'title',
            title: '流程标题',
        },
        {
            dataIndex: 'description',
            title: '流程描述',
            search: false,
        },
        {
            dataIndex: 'creator',
            title: '创建者',
            search: false,
            render: (text: any, record: any, index: any) => {
                return (
                    <span>{record['creator'].name}</span>
                )
            }
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
                columns={columns}
                toolBarRender={() => {
                    return [
                        <Button onClick={()=>{

                        }}>新建流程</Button>
                    ]
                }}
                request={async (params) => {
                    const res = await axios.get('http://localhost:8090/design/list', {
                        params: params
                    });
                    return {
                        data: res.data.data.list,
                        success: true,
                        total: res.data.data.total,
                    }
                }
                }
            />
        </PageContainer>
    )
}

export default FLowPage;
