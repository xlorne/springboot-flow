import {LockOutlined, UserOutlined,} from '@ant-design/icons';
import {LoginForm, ProConfigProvider, ProFormCheckbox, ProFormText,} from '@ant-design/pro-components';
import {theme} from 'antd';
import {login} from "@/api/login";
import {useNavigate} from "react-router-dom";


const LoginPage =  () => {
    const { token } = theme.useToken();

    const navigate = useNavigate();

    return (
        <ProConfigProvider hashed={false}>
            <div style={{ backgroundColor: token.colorBgContainer }}>
                <LoginForm
                    logo="https://github.githubassets.com/favicons/favicon.png"
                    title="Flow Design"
                    subTitle="开源的流程引擎"
                    onFinish={async (values) => {
                        login(values).then((res) => {
                            if (res.success) {
                                localStorage.setItem('username', res.data.username);
                                localStorage.setItem('token', res.data.token);
                                navigate('/welcome');
                            }
                        });
                    }}
                >

                    <ProFormText
                        name="username"
                        fieldProps={{
                            size: 'large',
                            prefix: <UserOutlined className={'prefixIcon'} />,
                        }}
                        placeholder={'用户名'}
                        rules={[
                            {
                                required: true,
                                message: '请输入用户名!',
                            },
                        ]}
                    />
                    <ProFormText.Password
                        name="password"
                        fieldProps={{
                            size: 'large',
                            prefix: <LockOutlined className={'prefixIcon'} />,
                        }}
                        placeholder={'密码'}
                        rules={[
                            {
                                required: true,
                                message: '请输入密码！',
                            },
                        ]}
                    />

                    <div
                        style={{
                            marginBlockEnd: 24,
                        }}
                    >
                        <ProFormCheckbox noStyle name="autoLogin">
                            自动登录
                        </ProFormCheckbox>
                        <a
                            style={{
                                float: 'right',
                            }}
                        >
                            忘记密码
                        </a>
                    </div>
                </LoginForm>
            </div>
        </ProConfigProvider>
    );
};

export default LoginPage;
