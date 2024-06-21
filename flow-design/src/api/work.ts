import {page} from "@/api/index";

export async function todo(
    params: {
        [key in string]: any;
    },
    sort: any,
    filter: any,
    match: {
        key: string,
        type: string
    }[],
) {
    return await page('/api/work/todo', params, sort, filter, match);
}


export async function process(
    params: {
        [key in string]: any;
    },
    sort: any,
    filter: any,
    match: {
        key: string,
        type: string
    }[],
) {
    return await page('/api/work/process', params, sort, filter, match);
}
