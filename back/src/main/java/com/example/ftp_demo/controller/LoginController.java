package com.example.ftp_demo.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.example.ftp_demo.entity.User;
import com.example.ftp_demo.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 1:57   <br/>
 * Description: 登录请求
 *
 * @author: chen
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public Msg login(@RequestBody User user) {
        log.info(user.toString());
        if ("chen".equals(user.getUsername()) && "Scnu@haha".equals(user.getPassword())) {
            StpUtil.login(10001);
            // 获取当前会话的token详细参数
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Msg.success().add(tokenInfo.getTokenName(), tokenInfo.getTokenValue());
        }
        return Msg.error(100, "登录失败");
    }

    @GetMapping("/test")
    public Msg test() {
        return Msg.success();
    }

    @GetMapping("/logout")
    public Msg logout() {
        StpUtil.logout();
        return Msg.success();
    }
}
