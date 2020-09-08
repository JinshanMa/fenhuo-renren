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
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.fenhuo.utils.ProjectRelatedfileObj;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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

//    @Login
//    @RequestMapping("/active")
//    public R activeProject(@RequestParam Map<String,Object> param){
//
//        String projectid = (String)param.get("projectid");
//        if (StringUtils.isNotBlank(projectid)){
//            ArrayList<String> ids = new ArrayList<String>();
//            ids.add(projectid);
//            fenhuoProjectinfoService.updateProjectInfoByIds(ids);
//            return R.ok();
//        }else{
//            return R.error(500,"参数错误");
//        }
//    }

//    @Login
//    @RequestMapping("/close")
//    public R closeProject(@RequestParam Map<String,Object> param){
//
//        String projectid = (String)param.get("projectid");
//        if (StringUtils.isNotBlank(projectid)){
//            ArrayList<String> ids = new ArrayList<String>();
//            ids.add(projectid);
//            fenhuoProjectinfoService.closeProjectInfoByIds(ids);
//            return R.ok();
//        }else{
//            return R.error(500,"参数错误");
//        }
//    }


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


        boolean proInfoIsOk = fenhuoProjectinfoService.saveProjectInfo(fenhuoProjectinfo);
        if(proInfoIsOk){

            fenhuoZabbixhost.setProjectid(fenhuoProjectinfo.getProjectid());
            fenhuoZabbixhost.setProjectname(fenhuoProjectinfo.getProjectname());
            fenhuoZabbixhost.setIsdeleted(0);

            fenhuoZabbixhostService.save(fenhuoZabbixhost);
        }
        return R.ok();

    }

//
//    @RequestMapping("/zbxinfo/{projectid}")
//    public R zabbixHostsList(@PathVariable("projectid") String projectid){
//        QueryWrapper<FenhuoZabbixhostEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq("projectid",Long.valueOf(projectid));
//        FenhuoZabbixhostEntity zbxhost = fenhuoZabbixhostService.getOne(wrapper);
//        String username = zbxhost.getZbusername();
//        String password = zbxhost.getZbuserpwd();
//
//        boolean anotherOk = zabbixApiUtils.zabbixLogin(username,password);
//        if (!anotherOk){
//            return R.error(500,"zabbix服务器登录失败，请检查账号");
//        }
//
//        JSONObject data = zabbixApiUtils.getDataBySingleParam("host.get", "filter",null);
//
//        return R.ok().put("info",data);
//    }


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




    @PostMapping("/upload/{projectid}")
    public R uploadRelatedFile(@PathVariable("projectid") String projectid,
                               @RequestParam("deleteFiles") String[] delFilenames,
                               @RequestParam("files") MultipartFile[] files){
//        System.out.println("delFilenames.length:" + delFilenames.length);
        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
        String projectFileDir = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + OpUtils.getBacklash();

        // 如果 待删除的文件长度大于零表示有文件需要删除
        if (delFilenames.length > 0){
            List<String> totalpaths = new ArrayList<String>(Arrays.asList(projectinfo.getFileurl().split(OpUtils.getSplitNotation())));
            for (String deletingFilename: delFilenames){
                String absolutefilepath = projectFileDir + deletingFilename;
                File file = new File(absolutefilepath);
                if(file.exists()){
                    file.delete();
                }
                for (String path: totalpaths){
                    if (path.equals(absolutefilepath)){
                        totalpaths.remove(path);
                        break;
                    }
                }
            }
            projectinfo.setFileurl(String.join(OpUtils.getSplitNotation(),totalpaths));
            fenhuoProjectinfoService.updateById(projectinfo);

        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return R.error("filename is empty");
            }


            String fileName = file.getOriginalFilename();

            String destPath = uploadFileConfig.getLocaluploadpath();
            System.out.println("---------projectid: " + projectid + "-----UploadFileConfig.getLocaluploadpath():" + uploadFileConfig.getLocaluploadpath());

//            FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
//            String projectFileDir = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + "/";

            File projectUploadFileDir = new File(projectFileDir);
            if (!projectUploadFileDir.exists()) {
                boolean ok = projectUploadFileDir.mkdir();
                if (!ok) {
                    return R.error().put("msg", "project Upload directory can not create!");
                }
            }

            File dest = new File(projectFileDir + fileName);
            try {
                file.transferTo(dest);
                String fullpath = dest.getAbsolutePath();
                String orinalUrls = projectinfo.getFileurl();
                List<String> urls;
                if (StringUtils.isNotBlank(orinalUrls)) {
                    List<String> arrayList = Arrays.asList(orinalUrls.split(OpUtils.getSplitNotation()));
                    urls = new ArrayList(arrayList);
                    System.out.println("---isNotBlank---:" + urls);
                } else {
                    urls = new ArrayList<String>();
                    System.out.println("---isBlank---");
                }
                System.out.println("before finalfullpath++++++++----:" + String.join(OpUtils.getSplitNotation(), urls));
                urls.add(fullpath);
                String finalFullPath = String.join(OpUtils.getSplitNotation(), urls);
                System.out.println("after finalfullpath++++++++------:" + finalFullPath);
                projectinfo.setFileurl(finalFullPath);
                fenhuoProjectinfoService.updateById(projectinfo);
//            LOGGER.info("上传成功");

            } catch (IOException e) {
                e.printStackTrace();
//            LOGGER.error(e.toString(), e);
            }
        }
        return R.ok();

    }

    @GetMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse res) {
        fenhuoProjectinfoService.relatedFileDownload(request, res);
    }

    @RequestMapping("/projectfilelist/{projectid}")
    public R listProjectfile(@PathVariable("projectid") String projectid){
        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
        String fileurls = projectinfo.getFileurl();
        List<ProjectRelatedfileObj> filenames = new ArrayList<ProjectRelatedfileObj>();
        if(StringUtils.isNotBlank(fileurls)) {
            String[] files = fileurls.split(OpUtils.getSplitNotation());
            List<String> filelist = new ArrayList<String>(Arrays.asList(files));

            for (String file : filelist) {
                int index = file.lastIndexOf(OpUtils.getBacklash());
                ProjectRelatedfileObj fileobj = new ProjectRelatedfileObj();
                fileobj.setUid(String.valueOf(file.hashCode()));
                fileobj.setName(file.substring(index+1));
                filenames.add(fileobj);
            }
        }

        return R.ok().put("relatedfilelist", filenames);

    }



}

//
//    CREATE TABLE `fenhuo_projectfile` (
//        `fileid` bigint NOT NULL AUTO_INCREMENT,
//        `projectid` int NOT NULL COMMENT '项目id',
//        `filename` varchar(50) COMMENT '文件名',
//        `filepath` varchar(200) COMMENT '文件路径',
//        `filetype` varchar(20) COMMENT '文件类型',
//        `filesize` bigint COMMENT '文件大小',
//        PRIMARY KEY (`fileid`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目附件';


