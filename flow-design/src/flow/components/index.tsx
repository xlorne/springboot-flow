import {Start, StartDefaultConfig} from "./Start";
import {Over, OverDefaultConfig} from "./Over";
import {Single, SingleDefaultConfig} from "./Single";
import {Multiple, MultipleDefaultConfig} from "./Multiple";

export const components = [
    {
        component: Start,
        popover: () => <div>开始</div>,
        ...StartDefaultConfig
    },
    {
        component: Multiple,
        popover: () => <div>会签节点</div>,
        ...MultipleDefaultConfig
    },
    {
        component: Single,
        popover: () => <div>或签节点</div>,
        ...SingleDefaultConfig
    },

    {
        component: Over,
        popover: () => <div>结束</div>,
        ...OverDefaultConfig
    },
]