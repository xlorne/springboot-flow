import React, {useEffect} from "react";
import {ProFormSelect} from "@ant-design/pro-components";
import {all} from "@/api/flow";
import {ProFormSelectProps} from "@ant-design/pro-form/es/components/Select";


const FlowSelector:React.FC<ProFormSelectProps> = (props) => {

    const [list, setList] = React.useState<any[]>([]);

    useEffect(() => {
        all().then(res => {
            if(res.success){

                const list = res.data.list;

                setList(list.map((item:any)=>{
                    return {
                        label: item.title,
                        value: item.id
                    }

                }));
            }
        })
    }, []);


    return (
        <ProFormSelect
            {...props}
            options={list}
        />
    )
}

export default FlowSelector;
