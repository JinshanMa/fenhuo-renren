package io.renren.modules.fenhuo.controller;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import io.renren.common.utils.DateUtils;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 故障申报表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:42:15
 */
@RestController
@RequestMapping("fenhuo/fenhuofault")
public class FenhuoFaultController extends AbstractController {
    @Autowired
    private FenhuoFaultService fenhuoFaultService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private ZabbixApiUtils zabbixApiUtils;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuofault:list")
    public R list(@RequestParam Map<String, Object> params){

        Object userObj = getUser();
        String fenhuouserId;
        FenhuoUsersEntity fenhuouser = null;
        if (userObj instanceof SysUserEntity){
            SysUserEntity sysuser = (SysUserEntity)userObj;
            fenhuouserId = String.valueOf(-sysuser.getUserId());
        } else {
            fenhuouser = (FenhuoUsersEntity)userObj;
            fenhuouserId = String.valueOf(fenhuouser.getUserid());
        }
        Long longuserid = Long.valueOf(fenhuouserId);
        if(longuserid > 0) {
            String roleid = fenhuouser.getRoleid();
            if(roleid.equals("2")){
                //项目负责人
                params.put("headid", fenhuouserId);
            }else if(roleid.equals("1")){
                //甲方负责人
                params.put("partyaid", fenhuouserId);
            }else if(roleid.equals("3")){
                //维护工程师
                params.put("servicemid", fenhuouserId);
            }
        }
        PageUtils page = fenhuoFaultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/seletedlist")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:list")
    public R selectedProjectDevList(@RequestParam Map<String, Object> params){
        String projectids = (String)params.get("projectid");
        String[] projectidarray = projectids.split(",");

        QueryWrapper<FenhuoZabbixhostEntity> queryWrapper =
                new QueryWrapper<FenhuoZabbixhostEntity>().in("projectid", projectidarray);
        List<FenhuoZabbixhostEntity> zabbixhostEntityList =  fenhuoZabbixhostService.list(queryWrapper);


        List<String> list = new ArrayList<String>();
        for (FenhuoZabbixhostEntity zabbixhost : zabbixhostEntityList){
            String hosids =  zabbixhost.getHostids();
            String[] tmpHostids = hosids.split(",");
            list.addAll(Arrays.asList(tmpHostids));
        }
        Set<String> hashset = new HashSet<>(list);

        String[] hostidArray = hashset.toArray(new String[]{});
        zabbixApiUtils.zabbixLogin("Admin","Fire@2019");
        JSONObject jsonData = zabbixApiUtils.getDataBySingleParamArray("host.get", "hostids",hostidArray);

        JSONArray hostarray = (JSONArray)jsonData.get("result");
        JSONArray retJsonArray = new JSONArray();
        for(int i = 0;i < hostarray.size();i++){
            String hostid = (String)hostarray.getJSONObject(i).get("hostid");
            String hostname = (String)hostarray.getJSONObject(i).get("host");
            JSONObject jsobj = new JSONObject();
            jsobj.put("hostid", hostid);
            jsobj.put("hostname", hostname);
            retJsonArray.add(jsobj);
        }
        return  R.ok().put("zbxarray", retJsonArray);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultid}")
    @RequiresPermissions("fenhuo:fenhuofault:info")
    public R info(@PathVariable("faultid") String faultid){
		FenhuoFaultEntity fenhuoFault = fenhuoFaultService.getById(faultid);

        return R.ok().put("fenhuoFault", fenhuoFault);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuofault:save")
    public R save(@RequestBody FenhuoFaultEntity fenhuoFault){

        // 申报人id
        Long userid = getUserId();
        Object userObj = getUser();
        fenhuoFault.setDeclarer(String.valueOf(userid));
        if(userObj instanceof FenhuoUsersEntity){
//            FenhuoUsersEntity userMsg = fenhuoUsersService.getById(userid);

            // 设置申报人姓名
            fenhuoFault.setDeclarername(((FenhuoUsersEntity)userObj).getRealname());

        }else{
            fenhuoFault.setDeclarername(((SysUserEntity)userObj).getUsername());
        }

        fenhuoFaultService.savefenhuofault(fenhuoFault);
//        fenhuoFaultService.confirmToFaultdefend(faultdefend);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuofault:update")
    public R update(@RequestBody FenhuoFaultEntity fenhuoFault){
		fenhuoFaultService.updateById(fenhuoFault);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuofault:delete")
    public R delete(@RequestBody String[] faultids){
		fenhuoFaultService.removeByIds(Arrays.asList(faultids));

        return R.ok();
    }
    /**
     * 维护人员操作
     */
    @RequestMapping("/maintain/{action}")
    @RequiresPermissions("fenhuo:fenhuofault:confirm")
    public R maintainupdate(@PathVariable("action") String action, @RequestParam Map<String, Object> params){

        if (getUser() instanceof SysUserEntity) {
            return R.error().put("msg", "you are not common user");
        }
//        }else {
//            FenhuoUsersEntity fenhuoUser = (FenhuoUsersEntity)getUser();
//            if (!fenhuoUser.getRoleid().equals("3")){
//                return R.error().put("msg", "you are not maintainer");
//            }
//        }
        if(action.equals("confirm")) {
            String faultid = (String) params.get("faultid");
            FenhuoUsersEntity fenhuoUser = (FenhuoUsersEntity) getUser();
            FenhuoFaultdefendEntity faultdefend = fenhuoFaultService.confirmeById(faultid, fenhuoUser);

            Date beginDate = DateUtils.stringToDate((String) params.get("begindate"), DateUtils.DATE_TIME_PATTERN);
            Date endate = DateUtils.stringToDate((String) params.get("endate"), DateUtils.DATE_TIME_PATTERN);
            fenhuoFaultService.confirmToFaultdefend(faultdefend, beginDate, endate);
        } else if(action.equals("postvalidate")){
            String faultid = (String) params.get("faultid");
            fenhuoFaultService.postvalidate(faultid);
        } else if(action.equals("noideamaintain")){
            String faultid = (String) params.get("faultid");
            fenhuoFaultService.noideamaintain(faultid);
        }
        return R.ok();
    }



    /**
     * 确认维护操作
     */
    @RequestMapping("/valid/{action}")
    @RequiresPermissions("fenhuo:fenhuofaultdefend:valid")
    public R validupdate(@PathVariable("action") String action, @RequestParam Map<String, Object> params){

        if (getUser() instanceof SysUserEntity) {
            return R.error().put("msg", "you are not common user");
        }
//        }else {
//            FenhuoUsersEntity fenhuoUser = (FenhuoUsersEntity)getUser();
//            if (!fenhuoUser.getRoleid().equals("3")){
//                return R.error().put("msg", "you are not maintainer");
//            }
//        }
        if(action.equals("passvalid")){
            String faultid = (String) params.get("faultid");
            fenhuoFaultService.passvalid(faultid);
        } else if(action.equals("validfail")){
            String faultid = (String) params.get("faultid");
            fenhuoFaultService.validfail(faultid);
        }
        return R.ok();
    }


}
