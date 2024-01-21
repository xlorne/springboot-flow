import {Flow} from "../flow";
import React from "react";

export const New = () => {
    return (
        <Flow
            onSave={(data) => {
                console.log(data)
                console.log(JSON.stringify(data))
            }}
        />
    )
}