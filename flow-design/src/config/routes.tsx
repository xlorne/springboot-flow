import React from "react";
import {Page} from "../page";
import {New} from "../page/new";
import {Design} from "../Design";
import {App} from "../App";
import {createHashRouter} from "react-router-dom";
import Login from "../page/login";


const routes = [
    {
        path: "/index",
        element: <Page/>
    },
    {
        path: "/new",
        element: <New/>
    },
    {
        path: "/design",
        element: <Design/>
    },
    {
        path: "/login",
        element: <Login/>
    },
    {
        path: "/",
        element: <App/>
    },
]

export const hashRoutes = createHashRouter(
    [
        ...routes
    ]
);
