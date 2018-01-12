package org.logink.cloud.api.gateway.demo.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.logink.cloud.api.gateway.demo.util.HttpUtils;
import org.logink.cloud.api.gateway.demo.util.SignUtils;

/**
 * 普通短信发送demo
 *
 */
public class SendSmsDemo {
    public static void main(String[] args) {
	try {
	    String mobiles = "手机号"; // 多个手机号以英文逗号隔开
	    String content = "短信内容";
	    String appkey = "你自己的AppKey";
	    String appsecret = "你自己的秘钥";
	    String provideCode = "你所属短信账号"; // 短信供应商编号
					    // （wmin:索游短信；vcom：集时通短信；weinaduo：维那多短信）
	    String account = "你的短信账号"; //
	    String password = "你的短信密码"; //
	    //content = StringEscapeUtils.escapeJava(content); 
	    StringBuffer strBuffer = new StringBuffer();
	    strBuffer.append("{");
	    strBuffer.append("\"mobiles\":\"" + mobiles + "\",");
	    strBuffer.append("\"content\":\"" + content + "\",");
	    strBuffer.append("\"provideCode\":\"" + provideCode + "\",");
	    strBuffer.append("\"account\":\"" + account + "\",");
	    strBuffer.append("\"password\":\"" + password + "\"");
	    strBuffer.append("}");
	    String clientId = UUID.randomUUID().toString();

	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Accept", "application/json; charset=UTF-8");
	    headers.put("Content-Type", "application/json; charset=UTF-8");
	    //时间戳
	    headers.put("X-Ca-Timestamp", String.valueOf(new Date().getTime()));
	    //随机串
	    headers.put("X-Ca-Nonce", clientId);
	    //appkey
	    headers.put("X-Ca-Key", appkey);
	    List<String> signHeaderPrefixList = new ArrayList<String>();
		/**
		* 重要提示如下:
		* SignUtils请从
		* https://github.com/huojuntao/logink-cloud-api-message-demo-java/tree/master/src/main/java/org/logink/cloud/api/gateway/demo/util/SignUtils.java
		* 下载
		*/
	    //签名
	    headers.put("X-Ca-Signature", SignUtils.sign(appsecret, headers, signHeaderPrefixList));

	    String host = "https://api.logink.org";
	    String path = "/message/sms/sendSms";
	    String body = strBuffer.toString();
	    Map<String, String> querys = new HashMap<String, String>();
	    HttpResponse response = HttpUtils.httpPost(host, path, 9000, headers, querys, body);
	    String json = EntityUtils.toString(response.getEntity());
	    System.out.println(json);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
