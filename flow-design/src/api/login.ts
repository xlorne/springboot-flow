import {get, post} from "@/api/index";

export async function login(body:any) {
    return post('/user/login', body);
}
