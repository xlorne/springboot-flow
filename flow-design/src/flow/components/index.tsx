import {Node, NodeDefaultConfig} from "./Node";

export const components = [
    {
        component: Node,
        popover: () => <div>测试节点</div>,
        ...NodeDefaultConfig
    }
]