package io.renren.modules.fenhuo.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JGPushUtil {
    private static Logger log = LoggerFactory.getLogger(JGPushUtil.class);
    private static String testPushId = "140fe1da9e2616ec26c";



    private static String MASTER_SECRET = "da66ef1df409fb8ac4883bd4";
//    private static String APP_KEY = "b61962504d0230c809a94fb3";
    private static String APP_KEY = "0d0a2c0d949aed0aa51a3ca1";

    public static boolean pushMsgByRegID(String registerId,String title,String content, Map<String,String> extras){

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        PushPayload payload = buildPushObject_all_alias_alert(registerId,title, content, extras);
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("极光推送结果 - " + result);
            Thread.sleep(2000);
            // 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
            jpushClient.close();
        } catch(InterruptedException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static PushPayload buildPushObject_all_alias_alert(String registerId, String title, String content, Map<String,String> extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registerId))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras).build()
                        ).addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .setBadge(0)
                                .setSound("")
                                .setAlert(content)
                                .addExtras(extras).build())
                        .build()
                )
                .setMessage(Message.newBuilder().setTitle(title)
                        .setMsgContent(content).addExtras(extras)
                        .build())
                .build();
    }

    public static void main(String[] args) {
        Map<String,String> extras = new HashMap<>();
        extras.put("content","extras-content");
        extras.put("projectId","extra-projectId");
        extras.put("projectName","extra-projectName");
        extras.put("msgType","extra-msgType");
        boolean isok = pushMsgByRegID(testPushId, "烽火测试极光推送标题", "烽火测试极光推送文本内容",extras);
        System.out.println(isok);
    }
}
