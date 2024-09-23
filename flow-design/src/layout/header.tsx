import React, {useEffect} from "react";
import "./header.scss";
import {UserOutlined} from "@ant-design/icons";
import {Dropdown, MenuProps, Space} from "antd";
import {useNavigate} from "react-router";

const LayoutHeader = () => {
    const navigate = useNavigate();


    const username = localStorage.getItem('username');
    const items: MenuProps['items'] = [
        {
            key: 'logout',
            label: '退出登录',
        },
    ];

    return (
        <div className={'header-body'}>
            <Dropdown
                className="nodrag-region"
                menu={{
                    items,
                    onClick: (key) => {
                        if (key.key === 'logout') {
                            localStorage.clear();
                            navigate('/login', {replace: true});
                        }
                    }
                }}
            >
                <Space>
                    <UserOutlined/>
                    {username}
                </Space>
            </Dropdown>
        </div>
    )
}

export default LayoutHeader;
