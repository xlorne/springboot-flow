import React from "react";
import {ModalForm, ProTable} from "@ant-design/pro-components";
import {list} from "@/api/flow";
import {flowColumns} from "@/page/flow/type";

interface FLowSelectorProps {
    visible: boolean;
    setVisible: (visible: boolean) => void;
    onFinished: (values: any) => void;
}

const FLowModalSelector:React.FC<FLowSelectorProps> = (props) => {

    const columns: any[] = [
        ...flowColumns
    ]

    const [selectedRowKeys, setSelectedRowKeys] = React.useState<React.Key[]>([]);

    return (
            <ModalForm
                title="选择流程"
                open={props.visible}
                modalProps={{
                    onCancel: () => {
                        props.setVisible(false);
                    },
                    destroyOnClose: true,
                    width: 800
                }}
                onFinish={async (values) => {
                    props.onFinished(selectedRowKeys);
                    props.setVisible(false);
                    return true;
                }}
            >
                <ProTable
                    rowKey={"id"}
                    columns={columns}
                    rowSelection={{
                        type: 'radio',
                        onChange: (selectedRowKeys, selectedRows) => {
                            setSelectedRowKeys(selectedRowKeys);
                        }
                    }}
                    request={async (params,sort,filter) => {
                        return list(params,sort,filter,[]);
                    }}
                />
            </ModalForm>
    )
}

export default FLowModalSelector;
