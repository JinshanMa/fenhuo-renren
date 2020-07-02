package io.renren.modules.fenhuo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 获取平台信息控制类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:10
 */
@RestController
@RequestMapping("fenhuo/platform")
public class FenhuoPlatformController extends AbstractController {

    @Autowired
    FenhuoUsersService fenhuoUsersService;

    @Autowired
    FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    FenhuoZabbixhostService fenhuoZabbixhostService;

    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:platform:list")
    public R getPlatformMsg(){

        Object userObj = getUser();
        JSONObject proinfojson = new JSONObject();
        JSONObject userinfojson = new JSONObject();
        JSONObject registuserjson =new JSONObject();
//        if(userObj instanceof SysUserEntity){
//            proinfojson = new JSONObject();
//            userinfojson = new JSONObject();
            QueryWrapper<FenhuoProjectinfoEntity> queryInfo =
                    new QueryWrapper<FenhuoProjectinfoEntity>().eq("isactive", 0);
//            fenhuoProjectinfoService.list(queryInfo);
            int noActiveCount = fenhuoProjectinfoService.count(queryInfo);
            int activeCount = fenhuoProjectinfoService.count() - noActiveCount;
            queryInfo = new QueryWrapper<FenhuoProjectinfoEntity>().eq("auditstatus", 103);
            int noauthed = fenhuoProjectinfoService.count(queryInfo);
            proinfojson.put("noactive", noActiveCount);
            proinfojson.put("active", activeCount);
            proinfojson.put("closed", noActiveCount);
            proinfojson.put("noauthed", noauthed);

            ////////////////////////////////////
            int userTotalCount = fenhuoUsersService.count();
            QueryWrapper<FenhuoUsersEntity> usersInfowrapper =
                    new QueryWrapper<FenhuoUsersEntity>().eq("roleid", 2);
            int promngCount = fenhuoUsersService.count(usersInfowrapper);

            usersInfowrapper =
                    new QueryWrapper<FenhuoUsersEntity>().eq("roleid", 1);

            int partyAcount = fenhuoUsersService.count(usersInfowrapper);

            usersInfowrapper =
                    new QueryWrapper<FenhuoUsersEntity>().eq("roleid", 3);
            int mantaincount = fenhuoUsersService.count(usersInfowrapper);
            userinfojson.put("userTotalcount", userTotalCount);
            userinfojson.put("promngCount", promngCount);
            userinfojson.put("partyAcount", partyAcount);
            userinfojson.put("mantaincount", mantaincount);

            usersInfowrapper =
                    new QueryWrapper<FenhuoUsersEntity>().eq("roleid", 5);

            int registMaintrainCount = fenhuoUsersService.count(usersInfowrapper);
            usersInfowrapper =
                    new QueryWrapper<FenhuoUsersEntity>().eq("roleid", 6);
            int registUserCount = fenhuoUsersService.count(usersInfowrapper);

            registuserjson.put("registMaintrainCount", registMaintrainCount);
            registuserjson.put("registUserCount", registUserCount);

//        }else {
//            String userID = String.valueOf(getUserId());
//            FenhuoUsersEntity fenhuoUser = fenhuoUsersService.getById(userID);
//            String repx = "(^"+ userID +",)|(,"+userID+",)|(,"+userID+"$)";
//
//            int isActiveCount = 0;
//            int noauthed = 0;
//            // 项目负责人
//            List<FenhuoProjectinfoEntity> fenhuoinfoList = fenhuoProjectinfoService.getProjectinfoByHeadid(repx);
//            int totalCount = fenhuoinfoList.size();
//            for(FenhuoProjectinfoEntity projectinfo: fenhuoinfoList){
//                if(projectinfo.getIsactive() == 1) {
//                    isActiveCount += 1;
//                }
//                if(projectinfo.getAuditstatus() == 103){
//                    noauthed += 1;
//                }
//            }
//            proinfojson = new JSONObject();
//            proinfojson.put("noauthed", noauthed);
//            proinfojson.put("active", isActiveCount);
//            proinfojson.put("closed", totalCount - isActiveCount);
//            proinfojson.put("noauthed", noauthed);
//        }
        return R.ok().put("proinfo", proinfojson).put("userinfo", userinfojson).put("registinfo", registuserjson);
    }

    /**
     * 获取所有的 zabbix主机的hostid集合无重复
     * @return
     */
    private Set<String> getFenhuoHeaidAllhostids(){
        String userID = String.valueOf(getUserId());
        FenhuoUsersEntity fenhuoUser = fenhuoUsersService.getById(userID);
        String userRole = fenhuoUser.getRoleid();
        String repx = "(^"+ userID +",)|(,"+userID+",)|(,"+userID+"$)";


        // 项目负责人
        List<FenhuoProjectinfoEntity> fenhuoinfoList = fenhuoProjectinfoService.getProjectinfoByHeadid(repx);
        List<Long> proids = new ArrayList<Long>();
        for(FenhuoProjectinfoEntity proinfo : fenhuoinfoList){
            proids.add(proinfo.getProjectid());
        }
        QueryWrapper<FenhuoZabbixhostEntity> queryWrapper = new QueryWrapper<FenhuoZabbixhostEntity>()
                .in("projectid", proids);
        List<FenhuoZabbixhostEntity> zabbixlist = fenhuoZabbixhostService.list(queryWrapper);
        Set<String> hostidSet = new HashSet<String>();
        for(FenhuoZabbixhostEntity zabbix : zabbixlist){
            String[] hostids = zabbix.getHostids().split(",");
            List<String> hostidlist = Arrays.asList(hostids);
            hostidSet.addAll(hostidlist);
        }
        return hostidSet;
    }
}
