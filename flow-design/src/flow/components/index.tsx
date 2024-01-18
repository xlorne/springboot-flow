import {Node} from "./Node";

export const components = [
    {
        component: Node,
        popover: () => <div>指标节点</div>,
        name: 'custom-node-indicator',
        width: 120,
        height: 50,
        label: '自定义节点',
        stroke: '#ccc',
        fill: '#fff',
        fontFill: '#000000',
        fontSize: 12,
        script:'123'
    }
]