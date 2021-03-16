package com.platform.house.controller;

import com.platform.house.utils.ResponseUtil;
import com.platform.house.utils.TsmsUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Office on 2019/3/18.
 */
@RestController
public class TsmsController {

    @GetMapping("/api/tsms/send")
    public ResponseEntity getToken(@PathVariable String phone, HttpServletRequest req) {
        String verifyCode = TsmsUtil.generateVerifyCode();
        // 发送短信验证码
        TsmsUtil.sendCaptcha(phone, verifyCode);
        // 将验证码保存到session中
        HttpSession session = req.getSession();
        session.setAttribute(phone, verifyCode);

        // 5分钟后情况将对应的session移除
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                session.removeAttribute(phone);
                timer.cancel();
            }
        }, 1000 * 60 * TsmsUtil.VALIDATE_TIME);

        return ResponseUtil.success();
    }

}
