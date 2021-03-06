package org.whh.bz.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.whh.bz.config.WechatTrustManager;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Configuration
public class WeChatUtils {
    private  static transient final String APPSECRET = "6861067c0e52a07136d001e12d54e58a";
    private  static transient final String APPID = "wxe637125bd257100b";
    private  static transient final String SCOPE = "snsapi_userinfo";
    private  static transient final String snsapi_login = "snsapi_login";
    private  static transient final String REDIRECT_URI = "https://42s4204l85.goho.co/wxTest";
    //to get qrccode
    private static final String getWeChatCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //to get token
    private static final String getWechatToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //to get user info
    private static final String  getWechatUserinfo= "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    private static final String urlToQRCode= "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
//    zkmFw3PQgXsXd62mVSJlvoqinehtcdlRIE8Cf9lttne
//    ???????????????


    public static String urlToQRCode(String sessionID){
        return urlToQRCode.replace("APPID",APPID).replace("REDIRECT_URI",REDIRECT_URI).replace("SCOPE",snsapi_login).replace("STATE",sessionID);
    }
    /**
     * ??????access-token
     * @param code
     * @return
     */
    public static String getWeChatToken( String code, String State){
        JSONObject jsonObject = null;
        String url = getWechatToken.replace("APPID",APPID).replace("SECRET",APPSECRET).replace("CODE",code);
        return url;
    }
    public static String getWeChatUserinfo(String token,String openID){
        JSONObject jsonObject = null;
        String s = getWechatUserinfo.replace("ACCESS_TOKEN",token).replace("OPENID",openID);
        return s;
    }

    /**
     *
     * @param session cookie
     * @param resp
     * @throws IOException
     * @throws WriterException
     */
    public  static boolean writeQRCodeToStream(String session,HttpServletResponse resp) throws IOException, WriterException {
        String url=getWeChatCode.replace("APPID",APPID).replace("REDIRECT_URI",REDIRECT_URI).replace("SCOPE",SCOPE).replace("STATE",session);
        resp.setHeader("Pragma", "No-cache"); // ????????????????????????????????????????????????????????????
        resp.setHeader("Cache-Control", "no-cache");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("image/*");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 350, 350);
        MatrixToImageWriter.writeToStream(bitMatrix,"png",resp.getOutputStream());
        return true;
    }

    /**
     *
     * @param requestUrl
     * @param requestMethod  get/post/put/delete
     * @param outputStr
     * @return
     */
    public static JSONObject httpsRequest(String requestUrl,
                                           String requestMethod,
                                           String outputStr) {
            JSONObject jsonObject = null;
            try {
                // ??????SSLContext?????????????????????????????????????????????????????????
                TrustManager[] tm = {new WechatTrustManager()};
                SSLContext sslContext = SSLContext
                        .getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new SecureRandom());
                // ?????????SSLContext???????????????SSLSocketFactory??????
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                URL url = new URL(requestUrl);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(ssf);

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                // ?????????????????????GET/POST???
                conn.setRequestMethod(requestMethod);

                // ???outputStr??????null????????????????????????
                if (null != outputStr) {
                    OutputStream outputStream = conn.getOutputStream();
                    // ??????????????????
                    outputStream.write(outputStr.getBytes(StandardCharsets.UTF_8));
                    outputStream.close();
                }

                // ??????????????????????????????
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);
                }

                // ????????????
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                conn.disconnect();
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (ConnectException ce) {
                System.err.printf("???????????????{}", ce);
            } catch (Exception e) {
                System.err.printf("https???????????????{}", e);
            }
            return jsonObject;

    }
}
