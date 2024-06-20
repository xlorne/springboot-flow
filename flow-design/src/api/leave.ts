import {post} from "@/api/index";

export async function create(body:any) {
    return post('/api/leave/create', body);
}
