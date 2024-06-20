import moment from "moment/moment";
import React from "react";

export const flowColumns  = [
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
                <span>{record.creator.username}</span>
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
]
