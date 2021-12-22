import httpRequest from '../lib/request'

/**
 * 获取文件列表
 * @param url 文件夹
 */
export const getFileListApi = (url = '') => {
    return httpRequest({
        url: '/file/fileList',
        method: 'GET',
        params: {
            path: url
        },
        headers: {
            isToken: true,
        }
    })
}

/**
 * 删除文件命令
 */
export const redeployApi = () => {
    return httpRequest({
        url: '/file/redeploy',
        method: 'GET',
    })
}

export const uploadFileApi = (params) => {
    return httpRequest({
        url: '/file/upload',
        method: 'POST',
        data: params,
        headers: {
            isToken: true,
            'Content-Type': 'multipart/form-data;charset=UTF-8',
        }
    })
}
