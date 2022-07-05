package xyz.supergao.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

import java.util.Random;

public class SmsMessageUtil {
    // 实例化一个认证对象
    private Credential cred;
    // 实例化一个http选项
    private HttpProfile httpProfile;
    // 实例化一个client选项
    private ClientProfile clientProfile;
    // 实例化要请求产品的client对象
    private SmsClient client;
    //短信 SdkAppId，在 短信控制台 添加应用后生成的实际 SdkAppId
    private String smsSdkAppId;
    //模板 ID，必须填写已审核通过的模板 ID
    private String templateId;
    //短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名
    private String signName;

    /**
     *
     * @param secretId 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
     * @param secretKey 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
     * @param region  region此值可以在https://console.cloud.tencent.com/api/explorer?Product=sms网站上查看 (例如 ap-guangzhou,ap-nanjing)
     * @param smsSdkAppId 短信 SdkAppId，在 短信控制台 添加应用后生成的实际 SdkAppId
     * @param templateId 模板 ID，必须填写已审核通过的模板 ID
     * @param signName 短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名
     */
    public SmsMessageUtil(String secretId,String secretKey,String region,String smsSdkAppId,String templateId,String signName) {
        this.cred = new Credential(secretId,secretKey);
        this.httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        this.clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        this.client = new SmsClient(cred, region, clientProfile);
        this.smsSdkAppId = smsSdkAppId;
        this.templateId = templateId;
        this.signName = signName;
    }
    //手机短信服务验证码，返回验证码4位数字转换的字符串
    public String sendSmsMessageVerificationCode(String phone){
        int code = 0;
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            //下发手机号码数组
            req.setPhoneNumberSet(new String[]{phone});
            //短信 SdkAppId，在 短信控制台 添加应用后生成的实际 SdkAppId，示例如1400006666。
            req.setSmsSdkAppId(this.smsSdkAppId);
            //模板 ID，必须填写已审核通过的模板 ID。
            req.setTemplateId(this.templateId);
            //短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名
            req.setSignName(this.signName);
            //模板参数，若无模板参数，则设置为空。验证码随机4位数
            code = new Random().nextInt(8999)+1000;
            req.setTemplateParamSet(new String[]{String.valueOf(code)});
            //短信码号扩展号，默认未开通
            //req.setExtendCode();
            //用户的 session 内容，可以携带用户侧 ID 等上下文信息，server 会原样返回。
            //req.setSessionContext();
            //国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。
            //req.setSenderId();
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        }catch (TencentCloudSDKException e) {
            System.out.println(e);
        }
        return String.valueOf(code);
    }
}
