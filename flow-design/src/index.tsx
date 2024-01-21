import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {ConfigProvider} from 'antd';

import {Page} from "./page";
import {New} from "./page/new";
import {Design} from "./Design";
import {App} from "./App";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <ConfigProvider>
        <BrowserRouter>
            <Routes>
                <Route path="index" element={<Page/>}/>
                <Route path="new" element={<New/>}/>
                <Route path="design" element={<Design/>}/>
                <Route path="/" element={<App/>}/>
            </Routes>
        </BrowserRouter>
    </ConfigProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
