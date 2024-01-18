import {Node} from "./Node";
import React from "react";

export const components = [
    {
        component: Node,
        popover: () => <div>指标节点</div>,
        name: 'custom-node-indicator',
        width: 120,
        height: 50,
        label: '自定义节点',
    }
]