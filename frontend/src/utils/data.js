export const type = {
    userBasicInfo:'',
    token:''
}

// 菜单点击切换，修改面包屑名称
export function setUserInfo(userInfo) {
    type.userBasicInfo=userInfo
}

export function getUserInfo() {
    return type.userBasicInfo;
}

export function getToken(){
    return localStorage.getItem('token');
}

export function setToken(token){
    localStorage.setItem('token', token);
}

export function getUserName(name){
    return localStorage.getItem('userName');
}

export function setUserName(name){
    localStorage.setItem('userName', name);
}