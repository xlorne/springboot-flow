import React from "react";
import "./menu.scss";
import {Menu, MenuProps} from "antd";
import {useNavigate} from "react-router";

const LayoutMenu = () => {

    const navigate = useNavigate();

    const onClick: MenuProps['onClick'] = (e) => {
        navigate(e.key);
    };

    return (
        <div className="menu-body">

            <div className="menu-header drag-region">
                <div className="menu-header-title">Flow Engine</div>
            </div>


            <Menu
                className={"header-menu"}
                onClick={onClick}
                mode="inline"
                items={[
                    {
                        key: 'welcome',
                        label: '首页',
                    },
                    {
                        key: 'flow',
                        label: '流程设计',
                    },
                    {
                        key: 'user',
                        label: '用户管理',
                    },
                    {
                        key: 'work',
                        label: '工作台',
                        children: [
                            {
                                key: 'work/my',
                                label: '我的发起',
                            },
                            {
                                key: 'work/todo',
                                label: '待办事项',
                            },
                            {
                                key: 'work/process',
                                label: '已办事项',
                            }
                        ]
                    },

                ]}
            />

        </div>
    )
}

export default LayoutMenu;
