import React from "react";
import FLowPage from "@/page/flow";
import {createHashRouter} from "react-router-dom";
import Login from "@/page/login";
import Home from "@/layout";
import Welcome from "@/page/welcome";
import NotFound from "@/layout/NotFound";
import UserPage from "@/page/user";
import WorkPage from "@/page/work";


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
        element: <WorkPage/>
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
