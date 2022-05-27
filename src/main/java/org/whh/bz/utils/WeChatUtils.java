package org.whh.bz.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.json.JSONObject;
import org.whh.bz.config.WechatTrustManager;

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

public class WeChatUtils {
    private  static final String APPSECRET = "0f21d5a489c6395b3f6a33dedd73721e";
    private  static final String APPID = "wx1de4bce0ab328e44";
    private  static final String SCOPE = "snsapi_userinfo";
    private  static final String snsapi_login = "snsapi_login";
    private  static final String REDIRECT_URI ="https://42s4204l85.goho.co/page/1";
    //to get qrccode
    private static final String getWeChatCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //to get token
    private static final String getWechatToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //to get user info
    private static final String  getWechatUserinfo= "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    private static final String urlToQRCode= "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    public static String urlToQRCode(String sessionID){
        return urlToQRCode.replace("APPID",APPID).replace("REDIRECT_URI",REDIRECT_URI).replace("SCOPE",snsapi_login).replace("STATE",sessionID);
    }
    /**
     * 获取access-token
     * @param code
     * @param req
     * @return
     */
    public static JSONObject getWeChatToken(String code,String State,HttpServletRequest req){
        JSONObject jsonObject = null;
        String s = getWeChatCode.replace("APPID",APPID).replace("SECRET",APPSECRET).replace("CODE",code);
        jsonObject = httpsRequest(s, "get",null);
        return jsonObject;
    }
    public static JSONObject getWeChatUserinfo(String token,String openID,HttpServletRequest req){
        JSONObject jsonObject = null;
        String s = getWechatUserinfo.replace("ACCESS_TOKEN",token).replace("OPENID",openID);
        jsonObject = httpsRequest(s, "get",null);
        return jsonObject;
    }

    /**
     *
     * @param session cookie
     * @param resp
     * @throws IOException
     * @throws WriterException
     */
    public  static void writeQRCodeToStream(String session,HttpServletResponse resp) throws IOException, WriterException {
        String url=getWeChatCode.replace("APPID",APPID).replace("REDIRECT_URI",REDIRECT_URI).replace("SCOPE",SCOPE).replace("STATE",session);
        resp.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此内容
        resp.setHeader("Cache-Control", "no-cache");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("image/*");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 350, 350);
        MatrixToImageWriter.writeToStream(bitMatrix,"png",resp.getOutputStream());
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
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = {new WechatTrustManager()};
                SSLContext sslContext = SSLContext
                        .getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                URL url = new URL(requestUrl);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(ssf);

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                // 设置请求方式（GET/POST）
                conn.setRequestMethod(requestMethod);

                // 当outputStr不为null时向输出流写数据
                if (null != outputStr) {
                    OutputStream outputStream = conn.getOutputStream();
                    // 注意编码格式
                    outputStream.write(outputStr.getBytes(StandardCharsets.UTF_8));
                    outputStream.close();
                }

                // 从输入流读取返回内容
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);
                }

                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                conn.disconnect();
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (ConnectException ce) {
                System.err.printf("连接超时：{}", ce);
            } catch (Exception e) {
                System.err.printf("https请求异常：{}", e);
            }
            return jsonObject;

    }
}
