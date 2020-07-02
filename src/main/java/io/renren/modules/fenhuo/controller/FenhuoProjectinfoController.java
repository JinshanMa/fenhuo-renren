package io.renren.modules.fenhuo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.obj.FenhuoProjectinfoRequest;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 项目信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:10
 */
@RestController
@RequestMapping("fenhuo/fenhuoprojectinfo")
public class FenhuoProjectinfoController extends AbstractController {
    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private ZabbixApiUtils zabbixApiUtils;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:list")
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
        PageUtils page = fenhuoProjectinfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{projectid}")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:info")
    public R info(@PathVariable("projectid") String projectid){
		FenhuoProjectinfoEntity fenhuoProjectinfo = fenhuoProjectinfoService.getById(projectid);

        Map<String, Object> map = new HashMap<>();
        map.put("projectid", Long.valueOf(projectid));
		List<FenhuoZabbixhostEntity>  zbxhostList = (List<FenhuoZabbixhostEntity>)fenhuoZabbixhostService.listByMap(map);

        return R.ok().put("projectinfo", fenhuoProjectinfo).put("zabbixhost", zbxhostList);
    }

    @RequestMapping("/zbx/{projectid}/{itemname}")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:info")
    public R zabbixInfo(@PathVariable("projectid") String projectid, @PathVariable("itemname") String itemname){

//        Map<String, Object> map = new HashMap<>();
        QueryWrapper<FenhuoZabbixhostEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("projectid",Long.valueOf(projectid));

        FenhuoZabbixhostEntity zbxhost = fenhuoZabbixhostService.getOne(wrapper);

        String hostids = zbxhost.getHostids();
        String hostnames = zbxhost.getZabbixhostnames();
        if (!StringUtils.isNotBlank(hostids) || !StringUtils.isNotBlank(hostnames)){
            return R.error().put("msg", "zabbix hostids or hostnames params is null error!");
        }
//        String hostnames = zbxhost.getZabbixhostnames();

        String username = zbxhost.getZbusername();
        String password = zbxhost.getZbuserpwd();

        JSONObject filter = new JSONObject();
        String[] hostidsArray = hostids.split(",");
        String[] hostnamesArray = hostnames.split(",");

        if(zabbixApiUtils.zabbixLogin(username,password)){
            JSONArray result = zabbixApiUtils.zbxApiUsage(itemname, hostidsArray, hostnamesArray);
            return R.ok().put("zbxdata", result);
        } else {
            JSONObject errordata = new JSONObject();
//            errordata.put("msg", "zabbix username or password error!");
            return R.error().put("msg", "zabbix username or password error!");
//            return R.error().put("prozbx", jsonData);
        }
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:save")
    public R save(@RequestBody FenhuoProjectinfoRequest fenhuoProjectinfoReq){

//        System.out.println(fenhuoProjectinfoReq);

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
//        return R.ok("projectid", fenhuoProjectinfo.getProjectid());
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:update")
    public R update(@RequestBody FenhuoProjectinfoRequest fenhuoProjectinfoReq){
        FenhuoProjectinfoEntity fenhuoProjectinfo = fenhuoProjectinfoReq.getProjectinfo();
        FenhuoZabbixhostEntity fenhuoZabbixhost = fenhuoProjectinfoReq.getZabbixhost();


        boolean proInfoIsOk = fenhuoProjectinfoService.updateProjectInfo(fenhuoProjectinfo);
        if(proInfoIsOk){

            fenhuoZabbixhost.setProjectid(fenhuoProjectinfo.getProjectid());
            fenhuoZabbixhost.setProjectname(fenhuoProjectinfo.getProjectname());
//            fenhuoZabbixhost.setIsdeleted(0);

            fenhuoZabbixhostService.updateById(fenhuoZabbixhost);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:delete")
    public R delete(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.removeByIdsBySetIsDeleted(Arrays.asList(projectids));
        return R.ok();
    }

    /**
     * 激活
     */
    @RequestMapping("/active")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:active")
    public R active(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.updateProjectInfoByIds(Arrays.asList(projectids));
        return R.ok();
    }

    /**
     * 关闭
     */
    @RequestMapping("/close")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:active")
    public R close(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.closeProjectInfoByIds(Arrays.asList(projectids));
        return R.ok();
    }

    /**
     * 关闭
     */
    @RequestMapping("/faildaudit")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:faildaudit")
    public R faildaudit(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.failedProjectInfoByIds(Arrays.asList(projectids));
        return R.ok();
    }



    @PostMapping("/upload")
    @RequiresPermissions("fenhuo:user:batch:add")
    public R uploadRelatedFile(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()) {
            return R.error("filename is empty");
        }

        String fileName = file.getOriginalFilename();
        System.out.println("fileName----:" + fileName);
        String filePath = "/temp/";
        File dest = new File(filePath + fileName);

        try {
            file.transferTo(dest);
//            LOGGER.info("上传成功");
            return R.ok();
        } catch (IOException e) {
            e.printStackTrace();
//            LOGGER.error(e.toString(), e);
        }

        return R.ok();
    }

}
