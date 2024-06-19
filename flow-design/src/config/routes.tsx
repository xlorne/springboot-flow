import React from "react";
import FLowPage from "@/page/flow";
import {createHashRouter} from "react-router-dom";
import Login from "@/page/login";
import Home from "@/layout";
import Welcome from "@/page/welcome";
import NotFound from "@/layout/NotFound";
import User from "@/page/user";


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
        element: <User/>
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
