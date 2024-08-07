import React from "react";
import {PageContainer, ProTable} from "@ant-design/pro-components";
import {Button, Drawer} from "antd";
import {list} from "@/api/flow";
import {flowColumns} from "@/page/flow/type";
import {Design} from "@/page/flow/design";

const FLowPage = () => {

    const [designVisible, setDesignVisible] = React.useState<boolean>(false);

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
                            setDesignVisible(true)
                        }}>新建流程</Button>
                    ]
                }}
                request={async (params, sort, filter) => {
                    return list(params, sort, filter, []);
                }}
            />

            <Drawer
                title={"新建流程"}
                width={"90%"}
                open={designVisible}
                onClose={()=>{
                    setDesignVisible(false);
                }}
                destroyOnClose={true}
            >
                <Design/>
            </Drawer>
        </PageContainer>
    )
}

export default FLowPage;
