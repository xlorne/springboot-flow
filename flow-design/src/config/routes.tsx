import React from "react";
import FLowPage from "@/page/flow";
import {createHashRouter} from "react-router-dom";
import Login from "@/page/login";
import Home from "@/layout";
import Welcome from "@/page/welcome";
import NotFound from "@/layout/NotFound";
import UserPage from "@/page/user";
import TodoPage from "@/page/work/todo";
import Routes from "@/compoments/Routes";
import ProcessPage from "@/page/work/process";
import MyProcessPage from "@/page/work/my";


export const layoutRoutes = [
    {
        path: "/flow",
        element: <FLowPage/>
    },
    {
        path: "/welcome",
        element: <Welcome/>
    },
    {
        path: "/user",
        element: <UserPage/>
    },
    {
        path: "/work",
        element:  <Routes rootPath="/work"/>,
        children: [
            {
                path: "/work/my",
                element: <MyProcessPage/>
            },
            {
                path: "/work/todo",
                element: <TodoPage/>
            },
            {
                path: "/work/process",
                element: <ProcessPage/>
            }
        ]
    },
    {
        path: "*",
        element: <NotFound/>
    }
]

const routes = [
    {
        path: '/',
        element: <Home/>,
        children: layoutRoutes
    },
    {
        path: "/login",
        element: <Login/>
    },
    {
        path: "*",
        element: <NotFound/>
    }
]

export const hashRoutes = createHashRouter(
    [
        ...routes
    ]
);
