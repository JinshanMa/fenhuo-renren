package io.renren.modules.app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.config.UploadFileConfig;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.utils.AppZabbixApiUtils;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.obj.FenhuoProjectinfoRequest;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("app/project")
public class AppProjectController extends AbstractController {

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private ZabbixApiUtils zabbixApiUtils;

    @Autowired
    private AppZabbixApiUtils appZabbixApiUtils;


    @Autowired
    private UploadFileConfig uploadFileConfig;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    public R list(@RequestParam Map<String,Object> param){

        FenhuoUsersEntity fenhuouser = fenhuoUsersService.queryByUserId(param.get("userId").toString());
        if (fenhuouser != null){
            String roleId = fenhuouser.getRoleid();
            if (roleId.equals("2")){
                param.put("headid", fenhuouser.getUserid().toString());
            }else if (roleId.equals("1")){
                param.put("partyaid", fenhuouser.getUserid().toString());
            }else{
                param.put("servicemid", fenhuouser.getUserid().toString());
            }
        }

        PageUtils page = fenhuoProjectinfoService.queryPage(param);

        return R.ok().put("page", page);
    }

    @Login
    @RequestMapping("/admin/list")
    public R adminList(@RequestParam Map<String,Object> param){

        PageUtils page = fenhuoProjectinfoService.queryPageWithParam(param);

        return R.ok().put("page", page);
    }


    @Login
    @RequestMapping("/reject/apply")
    public R rejectApplyProject(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");
        return  operateProject(projectid,102);

    }


    @Login
    @RequestMapping("/active")
    public R activeProject(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");

        FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);
        if (projectinfoEntity != null){

        }

        return  operateProject(projectid,104);

    }

    @Login
    @RequestMapping("/apply/active")
    public R applyActiveProject(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");
        return  operateProject(projectid,103);

    }

    @Login
    @RequestMapping("/close")
    public R closeProject(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");
        return  operateProject(projectid,106);

    }

    @Login
    @RequestMapping("/apply/close")
    public R applyCloseProject(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");
        return  operateProject(projectid,105);

    }

    @Login
    @RequestMapping("/reapply")
    public R reapply(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");
        return  operateProject(projectid,101);

    }


    private R operateProject(String projectid,int statu){
        if (StringUtils.isNotBlank(projectid)){
            ArrayList<String> ids = new ArrayList<String>();
            ids.add(projectid);
            fenhuoProjectinfoService.updateProjectInfoByIds(ids,statu);
            return R.ok();
        }else{
            return R.error(500,"参数错误");
        }
    }

    @Login
    @RequestMapping("/delete")
    public R delete(@RequestParam Map<String,Object> param){

        String projectid = (String)param.get("projectid");
        boolean isDelete = fenhuoUsersService.isDeleteByIds(Arrays.asList(projectid));
        if (isDelete){
            return R.ok();
        }else{
            return R.error();
        }

    }


    @Login
    @RequestMapping("/info/{projectid}")
    public R info(@PathVariable("projectid") String projectid) {
        FenhuoProjectinfoEntity fenhuoProjectinfo = fenhuoProjectinfoService.getById(projectid);

        Map<String, Object> map = new HashMap<>();
        map.put("projectid", Long.valueOf(projectid));
        List<FenhuoZabbixhostEntity> zbxhostList = (List<FenhuoZabbixhostEntity>) fenhuoZabbixhostService.listByMap(map);

        String headid = fenhuoProjectinfo.getHeadid();
        String partyaid = fenhuoProjectinfo.getPartyaid();
        String servicemid = fenhuoProjectinfo.getServicemid();

        List<FenhuoUsersEntity> projectUsers = new ArrayList<>();

        String[] headids = headid.split(",");
        for (String hid: headids){
            FenhuoUsersEntity usersEntity = fenhuoUsersService.getById(hid);
            projectUsers.add(usersEntity);
        }

        String[] partyaids = partyaid.split(",");
        for (String aid: partyaids) {
            FenhuoUsersEntity usersEntity = fenhuoUsersService.getById(aid);
            projectUsers.add(usersEntity);
        }

        String[] servicemids = servicemid.split(",");
        for (String mid:servicemids) {
            FenhuoUsersEntity usersEntity = fenhuoUsersService.getById(mid);
            projectUsers.add(usersEntity);
        }

        return R.ok().put("projectinfo", fenhuoProjectinfo).put("zabbixhost", zbxhostList).put("users", projectUsers);
    }



    @Login
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoProjectinfoRequest fenhuoProjectinfoReq){

        FenhuoProjectinfoEntity fenhuoProjectinfo = fenhuoProjectinfoReq.getProjectinfo();
        FenhuoZabbixhostEntity fenhuoZabbixhost = fenhuoProjectinfoReq.getZabbixhost();


        boolean isOk = fenhuoProjectinfoService.saveProjectInfo(fenhuoProjectinfo);
        if(isOk){
            fenhuoZabbixhost.setProjectid(fenhuoProjectinfo.getProjectid());
            fenhuoZabbixhost.setProjectname(fenhuoProjectinfo.getProjectname());
            fenhuoZabbixhost.setIsdeleted(0);
            fenhuoZabbixhostService.save(fenhuoZabbixhost);
        }
        return R.ok();

    }


    @Login
    @RequestMapping("update")
    public R update(@RequestBody FenhuoProjectinfoRequest fenhuoProjectinfoReq) {

        fenhuoProjectinfoService.updateProjectInfo(fenhuoProjectinfoReq.getProjectinfo());
        fenhuoZabbixhostService.updateById(fenhuoProjectinfoReq.getZabbixhost());

        return R.ok();
    }




    @RequestMapping("/zbxinfo/{projectid}")
    public R zabbixHostsList(@PathVariable("projectid") String projectid){
        QueryWrapper<FenhuoZabbixhostEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("projectid",Long.valueOf(projectid));
        FenhuoZabbixhostEntity zbxhost = fenhuoZabbixhostService.getOne(wrapper);
        String username = zbxhost.getZbusername();
        String password = zbxhost.getZbuserpwd();


        JSONObject result = appZabbixApiUtils.zabbixLogin(username,password);
        if (result.getString("result").equals("")){
            return R.error(500,"zabbix服务器登录失败，请检查账号");
        }

        String auth = result.getString("result");
        Integer id = result.getInteger("id");
        JSONObject data = appZabbixApiUtils.zabbixGetHosts(auth,id);

        return R.ok().put("auth",auth).put("id",id).put("info",data);
    }


    @RequestMapping("/zbx/host/items")
    public R zabbixHostItems(@RequestParam("hostid") String hostid,@RequestParam("auth") String auth,@RequestParam("id") Integer id){
        JSONObject result = appZabbixApiUtils.zabbixGetHostItems(hostid,auth,id);
        return R.ok().put("items",result);
    }

    @RequestMapping("/zbx/host/iteminfo")
    public R zabbixHostItemInfo(@RequestParam("itemid") String itemid,
                                @RequestParam("auth") String auth,
                                @RequestParam("id") Integer id,
                                @RequestParam("from") String from,
                                @RequestParam("till") String till){
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


        JSONObject result = appZabbixApiUtils.zabbixGetItemHistory(itemid, auth, id, f, t);

        return R.ok().put("history",result);
    }




    @Login
    @RequestMapping("/verify/{projectid}")
    public R verify(@PathVariable("projectid") String projectid){

        FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);
        projectinfoEntity.setIsactive(1);

        projectinfoEntity.setAuditstatus(104);

        fenhuoProjectinfoService.updateProjectInfo(projectinfoEntity);

        return R.ok();
    }


    @Login
    @RequestMapping("reject/{projectid}")
    public R reject(@PathVariable("projectid") String projectid){
        FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);
        projectinfoEntity.setIsdelete(1);

        Collection collection = new ArrayList<String>();
        collection.add(projectid);
        fenhuoProjectinfoService.removeByIdsBySetIsDeleted(collection);

        return R.ok();

    }




}


