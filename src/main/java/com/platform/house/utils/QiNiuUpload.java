package com.platform.house.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Created by Office on 2019/1/11.
 */
public class QiNiuUpload {
    public final static Logger log = Logger.getLogger(QiNiuUpload.class);
    public final static String ACCESS_KEY = "UwsGoFwD-HKi-vuTScIR8sUPXi03e-Coo1QZsIrH";
    public final static String SECRET_KEY = "231vpKkonM5KP6uGOJlaFf68-uflMczgsRtNeXfZ";
    public final static String QINIU_WEBSITE = "http://image.jr-hf.cn/";
    public final static String BUCKET = "test";

    public static String getfileKeyFromUrl(String imageUrl) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = ACCESS_KEY;
        String secretKey = SECRET_KEY;
        String bucket = BUCKET;
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        String fileKey = null;
        try {
            URL uri = new URL(imageUrl);
            InputStream inputStream = uri.openStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                fileKey = putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return fileKey;
    }

    public static void main(String[] args) {
        String fileKey = getfileKeyFromUrl("https://ke-image.ljcdn.com/newhouse-user-image/0fcde85e501e9b71d281dfa58f06cc37.jpg");
        log.info(fileKey);
    }
}
