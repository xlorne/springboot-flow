import {Node, NodeDefaultConfig} from "./Node";
import {Start, StartDefaultConfig} from "./Start";
import {Over, OverDefaultConfig} from "./Over";

export const components = [
    {
        component: Start,
        popover: () => <div>开始</div>,
        ...StartDefaultConfig
    },
    {
        component: Over,
        popover: () => <div>结束</div>,
        ...OverDefaultConfig
    },
    {
        component: Node,
        popover: () => <div>流程节点</div>,
        ...NodeDefaultConfig
    },
]