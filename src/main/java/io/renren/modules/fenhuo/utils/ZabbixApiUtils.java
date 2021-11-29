package io.renren.modules.fenhuo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.github.hengyunabc.zabbix.api.DefaultZabbixApi;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import io.github.hengyunabc.zabbix.api.ZabbixApi;
import io.renren.common.utils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("zabbixApiUtils")
public class ZabbixApiUtils {
    private final String CPU_USAGE_KEY = "system.cpu.util[,user]";
    private final String MEM_USAGE_KEY = "vm.memory.size[pused]";
    private final String HDD_USAGE_KEY = "vfs.fs.size[/,pused]";
    private final String NET_TOTAL_USAGE_KEY = "net.if.total";
    private final String WINDOWS_CPU_LOAD_KEY = "system.cpu.load[percpu,avg1]";

    private final String NET_IN_USAGE_KEY = "net.if.in";
    private final String NET_OUT_USAGE_KEY = "net.if.out";

    private final int HISTORY_LIMIT_COUNT = 10;
//    private String zabbixUrl = "http://114.116.19.246/zabbix/api_jsonrpc.php";
    private String zabbixUrl = "http://124.71.84.163/zabbix/api_jsonrpc.php";
    private ZabbixApi zabbixApi;

    public ZabbixApiUtils() {
        zabbixApi = new DefaultZabbixApi(zabbixUrl);
        zabbixApi.init();
//        zabbixLogin("Admin","Fire@2019");
    }

//    public boolean zabbixLogin(String usename, String password){
//
//        boolean loginResult = zabbixApi.login(usename, password);
////        System.out.println(loginResult);
//
//        return loginResult;
//    }
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


//    public JSONObject getDataBySingleParam(String method, String paramkeyname, JSONObject filter){
//        Request getRequest = RequestBuilder.newBuilder()
//                .method(method)
//                .paramEntry(paramkeyname, filter)
//                .build();
//        JSONObject getResponse = zabbixApi.call(getRequest);
////        System.out.println(getResponse);
//        return getResponse;
//    }
    public JSONObject getDataBySingleParamArray(String method, String paramkeyname, String[] data){
        Request getRequest = RequestBuilder.newBuilder()
                .method(method)
                .paramEntry(paramkeyname, data)
                .build();
        JSONObject getResponse = zabbixApi.call(getRequest);
//        System.out.println(getResponse);
        return getResponse;
    }

    public JSONObject getDataByMethod(String method, JSONObject filter){
        RequestBuilder getRequestBuilder = RequestBuilder.newBuilder().method(method);
        Set<String> keysets = filter.keySet();
        for(String key: keysets){
            getRequestBuilder.paramEntry(key, filter.get(key));
        }
        Request getRequest = getRequestBuilder.build();

        JSONObject getResponse = zabbixApi.call(getRequest);
//        System.out.println(getResponse);
        return getResponse;
    }

    /**
     * 获取key_的监控项
     * @param key_
     * @param hostids
     * @return
     */
    public JSONArray zbxApiItemGet(String key_, String[] hostids){
        JSONObject params = new JSONObject();
        params.put("hostids", hostids);
        params.put("output", "extend");
        JSONObject filterdata = new JSONObject();
        filterdata.put("key_", key_);
        params.put("search", filterdata);
        params.put("monitored", true);
        JSONObject jsonData = getDataByMethod("item.get",params);
        JSONArray resarray = jsonData.getJSONArray("result");
        return resarray;
    }

    /**
     * 获取CPU利用率
     * @param hostids
     * @return
     */
    public JSONArray zbxApiUsage(String itemname,String[] hostids, String[] hostnames) {
        JSONArray resarray;
        switch (itemname) {
            case "cpusage":
                resarray = zbxApiItemGet(CPU_USAGE_KEY, hostids);
                if (resarray.size() <= 0) {
                    resarray = zbxApiItemGet(WINDOWS_CPU_LOAD_KEY, hostids);
                }
                break;
            case "memusage":
                resarray = zbxApiItemGet(MEM_USAGE_KEY, hostids);
                break;
            case "hddusage":
                resarray = zbxApiItemGet(HDD_USAGE_KEY, hostids);
                break;
            case "ifusage":
                resarray = zbxApiItemGet(NET_TOTAL_USAGE_KEY, hostids);
                break;
            default:
                resarray = new JSONArray();
        }
        List<String> hostidsList = Arrays.asList(hostids);

        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < resarray.size(); i++) {
            String itemid = (String) resarray.getJSONObject(i).get("itemid");
            String hostid = (String) resarray.getJSONObject(i).get("hostid");
            JSONObject historyqueryjson = new JSONObject();
            JSONObject hostid2jsonobjct = new JSONObject();

            historyqueryjson.put("output", "extend");
            if (itemname.equals("ifusage")) {
                historyqueryjson.put("history", 3);
            } else {
                historyqueryjson.put("history", 0);
            }
            historyqueryjson.put("itemids", new String[]{itemid});
            historyqueryjson.put("sortfield", "clock");
            historyqueryjson.put("sortorder", "DESC");
            historyqueryjson.put("limit", HISTORY_LIMIT_COUNT);

            JSONObject historyjsonData = getDataByMethod("history.get", historyqueryjson);
            JSONArray historyObj = (JSONArray) historyjsonData.get("result");

            int hosidindex = hostidsList.indexOf(hostid);
            hostid2jsonobjct.put("hostid", hostid);
            hostid2jsonobjct.put("hostname", hostnames[hosidindex]);
            hostid2jsonobjct.put("array", historyObj);
            resultArray.add(hostid2jsonobjct);
        }

        return resultArray;
    }





    private static void writetojson(String content) throws IOException {
        File file =new File("item_output.json");


        if(!file.exists()){
            file.createNewFile();
        }

        //使用true，即进行append file

        FileWriter fileWritter = new FileWriter(file.getName(),true);


        fileWritter.write(content);

        fileWritter.close();

        System.out.println("finish");

    }

    public static void main(String[] args) {
        ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();
        JSONObject ret = zabbixApiUtils.zabbixLogin("Admi", "zabbix");
        System.out.print(ret);
    }



}
