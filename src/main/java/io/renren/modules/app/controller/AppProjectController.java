package io.renren.modules.app.controller;


import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.obj.FenhuoProjectinfoRequest;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping("/info/{projectid}")
    public R info(@PathVariable("projectid") String projectid) {
        FenhuoProjectinfoEntity fenhuoProjectinfo = fenhuoProjectinfoService.getById(projectid);

        Map<String, Object> map = new HashMap<>();
        map.put("projectid", Long.valueOf(projectid));
        List<FenhuoZabbixhostEntity> zbxhostList = (List<FenhuoZabbixhostEntity>) fenhuoZabbixhostService.listByMap(map);

        String partyaid = fenhuoProjectinfo.getPartyaid();
        String servicemid = fenhuoProjectinfo.getServicemid();

        List<FenhuoUsersEntity> projectUsers = new ArrayList<>();
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


        boolean proInfoIsOk = fenhuoProjectinfoService.saveProjectInfo(fenhuoProjectinfo);
        if(proInfoIsOk){

            fenhuoZabbixhost.setProjectid(fenhuoProjectinfo.getProjectid());
            fenhuoZabbixhost.setProjectname(fenhuoProjectinfo.getProjectname());
            fenhuoZabbixhost.setIsdeleted(0);

            fenhuoZabbixhostService.save(fenhuoZabbixhost);
        }
        return R.ok();

    }


    @Login
    @RequestMapping("/verify/{projectid}")
    public R verify(@PathVariable("projectid") String projectid){

        FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);
        projectinfoEntity.setIsactive(1);

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


