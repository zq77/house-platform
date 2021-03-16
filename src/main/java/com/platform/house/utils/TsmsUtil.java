package com.platform.house.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Office on 2019/3/18.
 */
public class TsmsUtil {
    public final static Logger log = Logger.getLogger(TsmsUtil.class);

    // 短信应用SDK AppID
    private static Integer APP_ID = 1400192696; // 1400开头
    // 短信应用SDK AppKey
    private static String APP_KEY = "ebe757cbf7a9f5fa88bfb0b978da5d9e";
    // 短信模板ID，需要在短信应用中申请
    private static Integer TEMP_ID = 295377; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    // 签名
    private static String SMS_SIGN = "Today好房"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID

    public static Integer VALIDATE_TIME = 5; // 有效时长为*分钟

    /**
     *     * 发送短信验证码
     *     *
     *     * @paramtel 电话号码
     *     * @paramverifyCode 验证码
     *     * @return
     *    
     */
    public static void sendCaptcha(String phoneNumber, String verifyCode) {
        try {

            // 单发短信
            // SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            // SmsSingleSenderResult result = ssender.send(0, "86", phoneNumber, "您正在注册成为好学堂用户，您的验证码为：" + verifyCode + "，请在10分钟内完成验证，感谢您的支持！", "", "");

            // 指定模板ID单发短信
            String[] params = {verifyCode};
            SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_KEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber, TEMP_ID, params, SMS_SIGN, "", "");
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            log.info("+++++++++++++++++ 手机号：" + phoneNumber + "，验证码: " + verifyCode + "+++++++++++++++++++++++");
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public static String generateVerifyCode() {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        return verifyCode;
    }
}
