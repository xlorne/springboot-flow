import React from 'react';
import {Flow} from "./flow";
import * as initData from "./data/data.json";

export const App = () => {

    return (
        <Flow
            data={initData}
            title={"流程设计"}
            onSave={(data) => {
                console.log(data);
                console.log(JSON.stringify(data));
            }}
        />
    )
}