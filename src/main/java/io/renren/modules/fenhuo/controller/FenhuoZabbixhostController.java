package io.renren.modules.fenhuo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 项目主机表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-27 11:51:03
 */
@RestController
@RequestMapping("fenhuo/fenhuozabbixhost")
public class FenhuoZabbixhostController extends AbstractController {
    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @RequestMapping("/authAndgethosts")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:authAndgethosts")
    public R authAndgethosts(@RequestParam Map<String, Object> params){
        boolean islogined = fenhuoZabbixhostService.authrizedTest(params);
        if(islogined){
            return R.ok().put("auth", "success");
        }else {
            return R.error(400, "Not authorised");
        }
    }


    @RequestMapping("/zabbixHostsByProjectid")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:hostsmessage")
    public R hostsmessage(@RequestParam Map<String, Object> params){
        JSONObject hostMsgObject = fenhuoZabbixhostService.loginAndGetHostsByProjectid(params);
        if(hostMsgObject != null){
            return R.ok().put("auth", "success").put("hosts", hostMsgObject.get("data")).put("authKey", hostMsgObject.get("auth")).put("id", hostMsgObject.get("id"));
//            return R.ok().put("auth", "success");
        }else {
            return R.error(404, "zabbix username and password invalid or projectid id invalid");
        }
    }

    @RequestMapping("/zabbixItemsByHostid")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:itemessages")
    public R itemsmessage(@RequestParam Map<String, Object> params){
        JSONObject itemsMsgObject = fenhuoZabbixhostService.getItemsByhostid(params);
        if(itemsMsgObject != null){
            return R.ok().put("auth", "success").put("items", itemsMsgObject);
//            return R.ok().put("auth", "success");
        }else {
            return R.error(404, "zabbix username and password invalid or projectid id invalid");
        }
    }

//    @RequestMapping("/zbxhostiteminfo")
//    public R zabbixHostItemInfo(@RequestParam("itemid") String itemid,
//                                @RequestParam("auth") String auth,
//                                @RequestParam("id") Integer id,
//                                @RequestParam("from") String from,
//                                @RequestParam("till") String till){
@RequestMapping("/zbxhostiteminfo")
public R zabbixHostItemInfo(@RequestParam Map<String, Object> params){
        String itemid = (String)params.get("itemid");
        String auth = (String)params.get("auth");
        String id = (String)params.get("id");
        String from = (String)params.get("from");
        String till = (String)params.get("till");

        if(StringUtils.isNotBlank(itemid) &&
                StringUtils.isNotBlank(auth) &&
                StringUtils.isNotBlank(id) &&
                StringUtils.isNotBlank(from) &&
                StringUtils.isNotBlank(till)){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
            Date df = null;
            Date tf = null;
            try {
                df = simpleDateFormat.parse(from);
                tf = simpleDateFormat.parse(till);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            long f = df.getTime() / 1000;
            long t = tf.getTime() / 1000;

            JSONObject result = fenhuoZabbixhostService.zabbixGetItemHistory(itemid, auth, Integer.valueOf(id), f, t);

//            return R.ok().put("history", result.get("history"));
            return R.ok().put("history", result);
        }else{
            return R.error(500, "request parameter is not match!!!");
        }
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:list")
    public R list(@RequestParam Map<String, Object> params){
        if(getUser() instanceof FenhuoUsersEntity){
            String userID = String.valueOf(getUserId());
            FenhuoUsersEntity fenhuoUser = fenhuoUsersService.getById(userID);
            String userRole = fenhuoUser.getRoleid();
            String repx = "(^"+ userID +",)|(,"+userID+",)|(,"+userID+"$)";
            if (userRole.equals("2")){

                // 项目负责人
                List<FenhuoProjectinfoEntity> fenhuoinfoList = fenhuoProjectinfoService.getProjectinfoByHeadid(repx);
                List<Long> proids = new ArrayList<Long>();
                for(FenhuoProjectinfoEntity proinfo : fenhuoinfoList){
                    proids.add(proinfo.getProjectid());
                }
                QueryWrapper<FenhuoZabbixhostEntity> queryWrapper = new QueryWrapper<FenhuoZabbixhostEntity>()
                        .in("projectid", proids);
                List<FenhuoZabbixhostEntity> zabbixlist = fenhuoZabbixhostService.list(queryWrapper);
                
//                System.out.println(fenhuoinfoList.size());
            }else if(userRole.equals("1")){
                // 甲方负责人

            }else if(userRole.equals("3")){
                // 维护工程师
            }
        }

//        PageUtils page = fenhuoZabbixhostService.queryPage(params);

        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:info")
    public R info(@PathVariable("id") String id){
		FenhuoZabbixhostEntity fenhuoZabbixhost = fenhuoZabbixhostService.getById(id);

        return R.ok().put("fenhuoZabbixhost", fenhuoZabbixhost);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:save")
    public R save(@RequestBody FenhuoZabbixhostEntity fenhuoZabbixhost){
		fenhuoZabbixhostService.save(fenhuoZabbixhost);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:update")
    public R update(@RequestBody FenhuoZabbixhostEntity fenhuoZabbixhost){
		fenhuoZabbixhostService.updateById(fenhuoZabbixhost);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuozabbixhost:delete")
    public R delete(@RequestBody String[] ids){
		fenhuoZabbixhostService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
