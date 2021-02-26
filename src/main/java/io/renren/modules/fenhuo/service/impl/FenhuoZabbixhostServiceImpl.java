package io.renren.modules.fenhuo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Json;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoZabbixhostDao;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;


@Service("fenhuoZabbixhostService")
public class FenhuoZabbixhostServiceImpl extends ServiceImpl<FenhuoZabbixhostDao, FenhuoZabbixhostEntity> implements FenhuoZabbixhostService {


    @Autowired
    private ZabbixApiUtils zabbixApiUtils;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String zbusername = (String)params.get("zbusername");

        IPage<FenhuoZabbixhostEntity> page = this.page(
                new Query<FenhuoZabbixhostEntity>().getPage(params),
                new QueryWrapper<FenhuoZabbixhostEntity>().eq("zbusername",zbusername)
        );

        return new PageUtils(page);
    }

    @Override
    public boolean removeByIdsBySetIsDeleted(Collection<? extends Serializable> idList) {
        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            removeByIdsBySetIsDeleted(detetingId);
        }
        return true;
    }

    @Override
    public boolean removeByIdsBySetIsDeleted(Long id) {
        QueryWrapper<FenhuoZabbixhostEntity> zabbixhostQueryWrapper
                = new QueryWrapper<FenhuoZabbixhostEntity>()
                .eq("projectid", id);
        List<FenhuoZabbixhostEntity> zbxHosts = list(zabbixhostQueryWrapper);
        for(FenhuoZabbixhostEntity zbxhost : zbxHosts){
            zbxhost.setIsdeleted(1);
            updateById(zbxhost);
        }
        return true;
    }

    @Override
    public boolean authrizedTest(Map<String, Object> params) {
        String username = (String)params.get("zbxUsername");
        String password = (String)params.get("zbxPassword");

        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
//            ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();
            JSONObject loginresult = zabbixApiUtils.zabbixLogin(username, password);
            if(loginresult != null){
//                JSONObject jsonData =  zabbixApiUtils.getDataBySingleParam("host.get", "filter",null);
                if(loginresult.get("result") == null)
                    return false;
                else
                    return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

//    @Override
//    public JSONObject getItemsByHostid(Map<String, Object> params) {
//        String hostid = (String)params.get("hostid");
//
//        if(StringUtils.isNotBlank(hostid)){
//            if(fenhuolist.size() > 0){
//                ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();
//                FenhuoZabbixhostEntity zabbixhost = fenhuolist.get(0);
//                if(zabbixApiUtils.zabbixLogin(zabbixhost.getZbusername(),zabbixhost.getZbuserpwd())){
//                    JSONObject jsonData =  zabbixApiUtils.getDataBySingleParam("host.get", "filter",null);
//                    return jsonData;
//                }else{
//                    return null;
//                }
//            }else
//                return null;
//        }else {
//            return null;
//        }
//    }

    @Override
    public JSONObject loginAndGetHostsByProjectid(Map<String, Object> params) {
        String projectid = (String)params.get("projectid");

        if(StringUtils.isNotBlank(projectid)){
            QueryWrapper<FenhuoZabbixhostEntity> queryWrapper = new QueryWrapper<FenhuoZabbixhostEntity>();
            queryWrapper.eq("projectid", Long.valueOf(projectid));
            List<FenhuoZabbixhostEntity> fenhuolist = list(queryWrapper);
            if(fenhuolist.size() > 0){
//                ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();
                FenhuoZabbixhostEntity zabbixhost = fenhuolist.get(0);
                JSONObject loginresult = zabbixApiUtils.zabbixLogin(zabbixhost.getZbusername(),zabbixhost.getZbuserpwd());
                if(loginresult.getString("result") != null){
                    String auth = loginresult.getString("result");
                    Integer id = loginresult.getInteger("id");
                    JSONObject data = zabbixApiUtils.zabbixGetHosts(auth,id);
//                    JSONObject jsonData =  zabbixApiUtils.getDataBySingleParam("host.login", "filter",null);
                    JSONObject retJson = new JSONObject();
                    retJson.put("auth", auth);
                    retJson.put("id", id);
                    retJson.put("data", data.get("result"));
                    return retJson;
                }else{
                    return null;
                }
            }else
                return null;
        }else {
            return null;
        }
    }
    @Override
    public JSONObject getItemsByhostid(Map<String, Object> params) {
        String hostid = (String)params.get("hostid");
        String authkey = (String)params.get("authkey");
        Integer authid = Integer.valueOf((String)params.get("authid"));

        if(StringUtils.isNotBlank(hostid) && StringUtils.isNotBlank(authkey) && authid != null){

            JSONObject loginresult = zabbixApiUtils.zabbixGetHostItems(hostid,authkey,authid);

            return loginresult;

        }else {
            return null;
        }
    }

    @Override
    public JSONObject zabbixGetItemHistory(String itemid, String auth, Integer id, long f, long t) {
        JSONObject itemRet = zabbixApiUtils.zabbixGetItemHistory(itemid, auth, id, f, t);
        return itemRet;
//        if (itemRet.get("history") != null){
//            return (JSONObject)itemRet.get("history");
//        }else {
//            itemRet.put("result", new ArrayList<>());
//            return itemRet;
//        }
    }

}