import React from 'react';
import {Flow} from "./flow";
import * as data from "./data/data.json";

export const App = () => {
    return (
        <Flow
            data={data}
            onSave={(data) => {
                console.log(data);
                console.log(JSON.stringify(data));
            }}
        />
    )
}