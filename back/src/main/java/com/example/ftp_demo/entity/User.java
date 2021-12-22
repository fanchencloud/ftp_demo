package com.example.ftp_demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 4:05   <br/>
 * Description: 用户类
 *
 * @author: chen
 */
@Getter
@Setter
@ToString
public class User {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
