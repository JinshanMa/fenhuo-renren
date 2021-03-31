package io.renren.modules.fenhuo.controller;


import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.config.UploadFileConfig;
import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.obj.FenhuoProjectinfoRequest;
import io.renren.modules.fenhuo.service.FenhuoProjectfileService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.fenhuo.utils.ProjectRelatedfileObj;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private ZabbixApiUtils zabbixApiUtils;

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;
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
     * 列表
     */
    @RequestMapping("/seletedlist")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:list")
    public R seletedList(@RequestParam Map<String, Object> params){

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
        PageUtils page = fenhuoProjectinfoService.querySelectedPage(params);

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

//        if(zabbixApiUtils.zabbixLogin(username,password)){
//            JSONArray result = zabbixApiUtils.zbxApiUsage(itemname, hostidsArray, hostnamesArray);
//            return R.ok().put("zbxdata", result);
//        } else {
//            JSONObject errordata = new JSONObject();
////            errordata.put("msg", "zabbix username or password error!");
//            return R.error().put("msg", "zabbix username or password error!");
////            return R.error().put("prozbx", jsonData);
//        }
        return R.ok();
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
		return R.ok().put("projectid", fenhuoProjectinfo.getProjectid());
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
        return R.ok().put("projectid", fenhuoProjectinfo.getProjectid());
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



    @RequestMapping("/applyfor/{action}")
    @RequiresPermissions("fenhuo:proj:applyfor")
    public R applyforaction(@PathVariable("action") String action, @RequestBody String[] projectids){
        if(action.equals("active")){
    //		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
            fenhuoProjectinfoService.updateProjectInfoByIds(Arrays.asList(projectids), 103);
        } else if(action.equals("close")){
            fenhuoProjectinfoService.updateProjectInfoByIds(Arrays.asList(projectids), 105);
        }
        return R.ok();
    }

    /**
     * 激活 审核通过
     */
    @RequestMapping("/active")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:active")
    public R active(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.updateProjectInfoByIds(Arrays.asList(projectids), 104);
        return R.ok();
    }

    /**
     * 审核未通过
     */
    @RequestMapping("/faildaudit")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:faildaudit")
    public R audifail(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.updateProjectInfoByIds(Arrays.asList(projectids), 102);
        return R.ok();
    }


    /**
     * 关闭
     */
    @RequestMapping("/close")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:active")
    public R close(@RequestBody String[] projectids){
//		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
        fenhuoProjectinfoService.updateProjectInfoByIds(Arrays.asList(projectids), 106);
        return R.ok();
    }

//    /**
//     * 关闭
//     */
//    @RequestMapping("/faildaudit")
//    @RequiresPermissions("fenhuo:fenhuoprojectinfo:faildaudit")
//    public R faildaudit(@RequestBody String[] projectids){
////		fenhuoProjectinfoService.removeByIds(Arrays.asList(projectids));
//        fenhuoProjectinfoService.failedProjectInfoByIds(Arrays.asList(projectids));
//        return R.ok();
//    }



    @PostMapping("/upload/{projectid}")
    @RequiresPermissions("fenhuo:proj:upload")
    public R uploadRelatedFile(@PathVariable("projectid") String projectid,
                               @RequestParam("deleteFiles") String[] delFileIds,
                               @RequestParam("files") MultipartFile[] files){
        OpUtils opUtils = new OpUtils();

        String opPath = opUtils.getPath();
        if (!opUtils.getPath().endsWith("/")){
            opPath += "/";
        }

        String projuploadir = opPath + uploadFileConfig.getLocaluploadpath();
        if (projuploadir.contains("file:")){
            projuploadir = projuploadir.replace("file:","");
        }
//        System.out.println("delFilenames.length:" + delFilenames.length);
//        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));

        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                .eq("projectid", Integer.valueOf(projectid));

        List<FenhuoProjectfileEntity> filelists = fenhuoProjectfileService.list(queryWrapper);
//        String projectFileDir = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + OpUtils.getBacklash();

        // 如果 待删除的文件长度大于零表示有文件需要删除
        if (delFileIds.length > 0){
//            List<String> totalpaths = new ArrayList<String>(Arrays.asList(projectinfo.getFileurl().split(OpUtils.getSplitNotation())));
            for (String deletingFileId: delFileIds){
                FenhuoProjectfileEntity relatedFile = fenhuoProjectfileService.getById(deletingFileId);
                if (relatedFile != null){
                String deletingFilepath = projuploadir + relatedFile.getFilepath() + relatedFile.getFilename();
                File file = new File(deletingFilepath);
                if(file.exists()){
                    file.delete();
                }
                fenhuoProjectfileService.removeById(deletingFileId);
//                for (FenhuoProjectfileEntity projectfile: filelists){
//                    if(projectfile.getFilename().equals(deletingFilename)){
//                        fenhuoProjectfileService.removeById(projectfile.getFileid());
//                        String deletingfilepath = projuploadir + projectfile.getFilepath() + projectfile.getFilename();
//                        File file = new File(deletingfilepath);
//                        if(file.exists()){
//                            file.delete();
//                        }
//                        break;
//                    }
//                }
                }
            }
        }
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return R.error("filename is empty");
            }


            String fileName = file.getOriginalFilename();

            ///// 把项目信息存入项目文件表中
            String relatedJarPath = opUtils.getPath();

            FenhuoProjectfileEntity projectfile = new FenhuoProjectfileEntity();
            projectfile.setProjectid(Integer.valueOf(projectid));
            projectfile.setFilename(fileName);
//            projectfile.setFilepath(relatedJarPath);
            projectfile.setFilepath("/proj_" + projectid + "/");

//            projectfile.setFileid(Long.valueOf(projectid));
            projectfile.setFiletype(fileName.substring(fileName.lastIndexOf(".") + 1));
            projectfile.setFilesize(file.getSize());
            projectfile.setType(2L);

            fenhuoProjectfileService.save(projectfile);
            //////////////////

            System.out.println("---------projectid: " + projectid + "-----UploadFileConfig.getLocaluploadpath():" + uploadFileConfig.getLocaluploadpath());

//            FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
//            String projectFileDir = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + "/";
            String projectFileDir = projuploadir + projectfile.getFilepath();
            File projectUploadFileDir = new File(projectFileDir);
            if (!projectUploadFileDir.exists()) {
                System.out.println("projectuploadfile:" + projectFileDir);
                boolean ok = projectUploadFileDir.mkdir();
                if (!ok) {
                    return R.error().put("msg", "project Upload directory can not create!");
                }
            }

            File dest = new File(projectFileDir + fileName);
            try {
                file.transferTo(dest);
                String fullpath = dest.getAbsolutePath();

                List<String> urls;

//            LOGGER.info("上传成功");

            } catch (IOException e) {
                e.printStackTrace();
//            LOGGER.error(e.toString(), e);
            }
        }
        return R.ok();

    }

    @GetMapping("/download")
    @RequiresPermissions("fenhuo:proj:relatedfile:download")
    public void downloadFile(HttpServletRequest request,HttpServletResponse res) {

        fenhuoProjectinfoService.relatedFileDownload(request, res);

    }

    @RequestMapping("/projectfilelist/{projectid}")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:projectfilelist")
    public R listProjectfile(@PathVariable("projectid") String projectid){
//        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
//        String fileurls = projectinfo.getFileurl();
        List<ProjectRelatedfileObj> filenames = new ArrayList<ProjectRelatedfileObj>();
//        if(StringUtils.isNotBlank(fileurls)) {
//            String[] files = fileurls.split(OpUtils.getSplitNotation());
//            List<String> filelist = new ArrayList<String>(Arrays.asList(files));
//
//            for (String file : filelist) {
//                int index = file.lastIndexOf(OpUtils.getBacklash());
//                ProjectRelatedfileObj fileobj = new ProjectRelatedfileObj();
//                fileobj.setUid(String.valueOf(file.hashCode()));
//                fileobj.setName(file.substring(index+1));
//                filenames.add(fileobj);
//            }
//        }

        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                .eq("projectid", Integer.valueOf(projectid)).and(r->r.eq("type", 2));
        List<FenhuoProjectfileEntity> filelist = fenhuoProjectfileService.list(queryWrapper);
        for(FenhuoProjectfileEntity relatedfile :filelist){
            String filename = relatedfile.getFilename();
            ProjectRelatedfileObj fileobj = new ProjectRelatedfileObj();
            fileobj.setFileid(relatedfile.getFileid());
            fileobj.setUid(String.valueOf(filename.hashCode()));
            fileobj.setName(filename);
            filenames.add(fileobj);
        }

        return R.ok().put("relatedfilelist", filenames);

    }

}
