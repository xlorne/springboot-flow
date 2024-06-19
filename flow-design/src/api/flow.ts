import {page} from "@/api/index";

export async function list(
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
    return await page('/api/flow/list', params, sort, filter, match);
}
