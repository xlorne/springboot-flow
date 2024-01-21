import React from 'react';
import {Button, Space} from "antd";
import {useNavigate} from "react-router-dom";

export const App = () => {

    const navigate = useNavigate();

    return (
        <div>
            <h1>Flow Design</h1>
            <Space>
                <Button onClick={() => {
                    navigate('/index');
                }}>flows</Button>

                <Button onClick={() => {
                    navigate('/design');
                }}>design</Button>
            </Space>
        </div>
    )
}