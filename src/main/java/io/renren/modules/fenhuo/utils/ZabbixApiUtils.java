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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private String zabbixUrl = "http://114.116.19.246/zabbix/api_jsonrpc.php";
    private ZabbixApi zabbixApi;

    public ZabbixApiUtils() {
        zabbixApi = new DefaultZabbixApi(zabbixUrl);
        zabbixApi.init();
//        zabbixLogin("Admin","Fire@2019");
    }

    public boolean zabbixLogin(String usename, String password){

        boolean loginResult = zabbixApi.login(usename, password);
//        System.out.println(loginResult);

        return loginResult;
    }

    public JSONObject getDataBySingleParam(String method, String paramkeyname, JSONObject filter){
        Request getRequest = RequestBuilder.newBuilder()
                .method(method)
                .paramEntry(paramkeyname, filter)
                .build();
        JSONObject getResponse = zabbixApi.call(getRequest);
//        System.out.println(getResponse);
        return getResponse;
    }
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



    public static void main3(String[] args){

        ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();


        boolean anotherOk = zabbixApiUtils.zabbixLogin("Admin","Fire@2019");


        JSONArray resarray = zabbixApiUtils.zbxApiItemGet("system.cpu.util[,user]", new String[]{"10264"});
//        JSONArray resarray = zabbixApiUtils.zbxApiUsage("hddusage",new String[]{"10264"}, new String[]{"江民测试服"});


        String historyprettyjson = JSON.toJSONString(resarray, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        try {
            writetojson(historyprettyjson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(resarray.size());

    }

    public static void main4(String[] args){

        ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();


        boolean anotherOk = zabbixApiUtils.zabbixLogin("Admin","Fire@2019");


//        JSONArray resarray = zabbixApiUtils.zbxApiItemGet("system.cpu.util[,user]", new String[]{"10264"});
        JSONArray resarray = zabbixApiUtils.zbxApiUsage("ifusage",new String[]{"10264"}, new String[]{"江民测试服2"});


        String historyprettyjson = JSON.toJSONString(resarray, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
//        try {
//            writetojson(historyprettyjson);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(resarray.size());

    }

    public static void main(String[] args) {
        ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();


        boolean anotherOk = zabbixApiUtils.zabbixLogin("Admin","Fire@2019");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostids", new String[] {"10084","10264"});

        System.out.println(zabbixApiUtils.getDataBySingleParamArray("host.get", "hostids",new String[] {"10084","10264","10267","10270"}));
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



}
