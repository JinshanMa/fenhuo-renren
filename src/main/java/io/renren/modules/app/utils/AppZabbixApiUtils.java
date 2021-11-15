package io.renren.modules.app.utils;

import com.alibaba.fastjson.JSONObject;
import io.github.hengyunabc.zabbix.api.DefaultZabbixApi;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import io.github.hengyunabc.zabbix.api.ZabbixApi;
import org.springframework.stereotype.Component;

@Component("appZabbixApiUtils")
public class AppZabbixApiUtils {


    private String zabbixUrl = "http://124.71.84.163/index.php";
    private ZabbixApi zabbixApi;

    public AppZabbixApiUtils() {
        zabbixApi = new DefaultZabbixApi(zabbixUrl);
        zabbixApi.init();
    }

    /**
     * 登录
     * @param username
     * @param pwd
     * @return
     */
    public JSONObject zabbixLogin(String username,String pwd){
        Request getRequest = RequestBuilder.newBuilder()
                .version("2.0")
                .method("user.login")
                .paramEntry("user", username)
                .paramEntry("password", pwd)
                .build();
        JSONObject getResponse = zabbixApi.call(getRequest);
        return getResponse;
    }

    /**
     * 获取账号下主机
     * @param auth
     * @param id
     * @return
     */
    public JSONObject zabbixGetHosts(String auth,Integer id){
        String[] output = {"hostid","name"};
        Request getRequest = RequestBuilder.newBuilder()
                .version("2.0")
                .method("host.get")
                .paramEntry("output", output)
                .auth(auth)
                .id(id)
                .build();
        JSONObject getResponse = zabbixApi.call(getRequest);
        return getResponse;
    }

    /**
     * 获取主机所有监控项
     * @param hostid
     * @param auth
     * @param id
     * @return
     */
    public JSONObject zabbixGetHostItems(String hostid,String auth,Integer id){
        String[] output = {"itemids","key_"};
        Request getRequest = RequestBuilder.newBuilder()
                .version("2.0")
                .method("item.get")
                .paramEntry("output", output)
                .paramEntry("hostids", hostid)
                .paramEntry("monitored", true)
                .auth(auth)
                .id(id)
                .build();
        JSONObject getResponse = zabbixApi.call(getRequest);
        return getResponse;
    }


    public JSONObject zabbixGetItemHistory(String itemids,String auth,Integer id,long from,long till){
        Request getRequest = RequestBuilder.newBuilder()
                .version("2.0")
                .method("history.get")
                .paramEntry("output", "extend")
                .paramEntry("itemids", itemids)
                .paramEntry("time_from", from)
                .paramEntry("time_till", till)
                .paramEntry("sortfield", "clock")
                .paramEntry("limit",30)
                .auth(auth)
                .id(id)
                .build();
        JSONObject getResponse = zabbixApi.call(getRequest);
        return getResponse;
    }




    public static  void main(String[] args){
        AppZabbixApiUtils utils = new AppZabbixApiUtils();
        JSONObject r = utils.zabbixLogin("Admin","Fire@2019");

//        System.out.println(r);
        System.out.print("-----------------------------------------------------------------------");
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        thread.start();
//        JSONObject r3 = utils.zabbixLogin("Admin","Fire@2019");
//        String result  = (String)r3.get("result");
//        System.out.print(result);
//        System.out.print(r3);
        String auth = r.getString("result");
        Integer id = r.getInteger("id");
//
//        JSONObject r2 =  utils.zabbixGetHosts(auth,id);
//
//        JSONObject r3 =  utils.zabbixGetItemHistory("28687",auth,id);
//
//        System.out.println(r3);
    }

}
