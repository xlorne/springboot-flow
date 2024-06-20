import {post} from "@/api/index";

export async function create(body:any) {
    return post('/api/leave/create', body);
}

export async function pass(body:any) {
    return post('/api/leave/pass', body);
}


export async function reject(body:any) {
    return post('/api/leave/reject', body);
}


export async function recall(body:any) {
    return post('/api/leave/recall', body);
}


export async function back(body:any) {
    return post('/api/leave/back', body);
}
