import React from "react";
import {Layout, Space} from "antd";
import Routes from "@/compoments/Routes";
import "./index.scss";
import {UserOutlined} from "@ant-design/icons";
import LayoutHeader from "@/layout/header";
import LayoutMenu from "@/layout/menu";

const {Header, Footer, Sider, Content} = Layout;

const Home = () => {

    const [siderWidth, setSiderWidth] = React.useState('15%');

    return (
        <Layout className="layout-content">
            <Sider
                className="layout-sider"
                width={siderWidth}>
                <LayoutMenu/>
            </Sider>
            <Layout>
                <Header
                    className="layout-content-header header"
                >
                   <LayoutHeader/>
                </Header>
                <Content
                    className="layout-content-content"
                >
                    <Routes rootPath="/"/>
                </Content>
                <Footer
                    className="layout-content-footer"
                >
                </Footer>
            </Layout>
        </Layout>

    )
}

export default Home;
