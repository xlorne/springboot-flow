import React from "react";
import {Route as ReactRoute, Routes as ReactRoutes} from "react-router";
import {layoutRoutes} from "@/config/routes";
import NotFound from "@/layout/NotFound";

interface RoutesProps {
    rootPath: string;
}


interface RouterItem {
    path: string;
    element: React.ReactNode;
    children?: RouterItem[];
    redirectTo?: string;
}


const getRoutes = (rootPath: string) => {
    const routes = layoutRoutes.filter(item => item.path.startsWith(rootPath || ''));

    const loadRoutes = (routes: RouterItem[]) => {
        return routes.map(item => {
            if (item.children) {
                return (
                    <ReactRoute
                        key={item.path}
                        path={item.path}
                        element={item.element}
                        children={loadRoutes(item.children)}
                    />
                )
            } else {
                let path = item.path;
                if (path.startsWith(rootPath)) {
                    path = path.replace(rootPath, '/').replaceAll('//', '/');
                }

                return (
                    <ReactRoute
                        key={item.path}
                        path={path}
                        element={item.element}
                    />
                );
            }
        });
    }

    if (routes.length === 1) {
        const root = routes[0];
        //@ts-ignore
        const children = root['children'] || [];
        return loadRoutes(children);
    }

    return loadRoutes(routes);
}


const Routes: React.FC<RoutesProps> = (props) => {
    const {rootPath} = props;

    const routes = getRoutes(rootPath);

    const notFoundPath = rootPath === '/' ? '/*' : `${rootPath}/*`;
    return (
        <ReactRoutes>
            {routes && routes.map(item => item)}
            <ReactRoute path={notFoundPath} element={<NotFound/>}/>
        </ReactRoutes>
    )
}

export default Routes;
