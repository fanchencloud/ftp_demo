import httpRequest from '../lib/request'

/**
 * 登录请求API
 * @param user 用户登录信息
 */
export const loginApi = (user) => {
    return httpRequest.request({
        url: '/login',
        method: 'POST',
        data: user,
    })
}

export const logoutApi = () => {
    return httpRequest({
        url: '/logout',
        method: 'GET',
    })
}
