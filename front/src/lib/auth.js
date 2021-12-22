const TokenKey = 'token'

export const getToken = () => {
    return localStorage.getItem(TokenKey);
}

export const setToken = (val) => {
    localStorage.setItem(TokenKey, val)
}

export const getData = (key) => {
    return localStorage.getItem(key)
}

export const setData = (key, val) => {
    localStorage.setItem(key, val)
}

export const removeData = (key) => {
    localStorage.removeItem(key)
}

export const removeToken = () => {
    localStorage.removeItem(TokenKey)
}
