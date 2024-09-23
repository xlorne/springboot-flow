import React from "react";
import {CheckCard, ModalForm, PageContainer, ProFormDigit, ProFormTextArea} from "@ant-design/pro-components";
import {Button, message} from "antd";
import FlowSelector from "@/page/flow/selector";
import {create} from "@/api/leave";

const Welcome = () => {

    const [visible, setVisible] = React.useState(false);

    const handleCreate = (values: any) => {
        create(values).then(res => {
            setVisible(false);
            message.success("流程发起成功").then();
        });
    }

    return (
        <PageContainer
            title={"业务办理"}
            extra={(
                <Button
                    type={"primary"}
                    onClick={() => {
                        setVisible(true);
                    }}>
                    发起流程
                </Button>
            )}
        >
            <div>
                <CheckCard.Group
                    onChange={(value) => {
                        console.log('value', value);
                    }}
                    defaultValue="leave"
                >
                    <CheckCard title="请求" description="发起请假流程" value="leave"/>

                </CheckCard.Group>
            </div>

            <ModalForm
                title="发起请假"
                open={visible}
                modalProps={{
                    onCancel: () => {
                        setVisible(false);
                    },
                    destroyOnClose: true,
                }}
                onFinish={async (values) => {
                    handleCreate(values)
                    return true;
                }}
            >
                <FlowSelector
                    name="workId"
                    label="流程"
                    placeholder="请选择流程"
                />

                <ProFormDigit
                    min={0}
                    name="days"
                    label="请假天数"
                    placeholder="请输入请假天数"
                />

                <ProFormTextArea
                    name="reason"
                    fieldProps={{
                        rows:5
                    }}
                    label="请假原因"
                    placeholder="请输入请假原因"
                />

            </ModalForm>
        </PageContainer>
    )
}

export default Welcome;
