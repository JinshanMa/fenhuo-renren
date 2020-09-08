package io.renren.modules.fenhuo.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import io.renren.common.utils.DateUtils;
import io.renren.config.UploadFileConfig;
import io.renren.modules.fenhuo.entity.*;
import io.renren.modules.fenhuo.service.FenhuoProjectfileService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.fenhuo.utils.ProjectRelatedfileObj;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.fenhuo.service.FenhuoFaultService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;

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
        return R.ok().put("faultid", fenhuoFault.getFaultid());
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


    @PostMapping("/upload/{faultid}")
    @RequiresPermissions("fenhuo:fault:upload")
    public R uploadFaultRelatedFile(@PathVariable("faultid") String faultid,
                               @RequestParam("deleteFiles") String[] delFilenames,
                               @RequestParam("files") MultipartFile[] files){
        OpUtils opUtils = new OpUtils();
        String projuploadir = opUtils.getPath() + uploadFileConfig.getLocaluploadpath();

//        System.out.println("delFilenames.length:" + delFilenames.length);
//        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));

        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                .eq("projectid", Integer.valueOf(faultid));

        List<FenhuoProjectfileEntity> filelists = fenhuoProjectfileService.list(queryWrapper);
//        String projectFileDir = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + OpUtils.getBacklash();

        // 如果 待删除的文件长度等于零表示有文件需要删除
        if (delFilenames.length > 0){
//            List<String> totalpaths = new ArrayList<String>(Arrays.asList(projectinfo.getFileurl().split(OpUtils.getSplitNotation())));
            for (String deletingFilename: delFilenames){

                for (FenhuoProjectfileEntity projectfile: filelists){
                    if(projectfile.getFilename().equals(deletingFilename)){
                        fenhuoProjectfileService.removeById(projectfile.getFileid());
                        String deletingfilepath = projuploadir + projectfile.getFilepath() + projectfile.getFilename();
                        File file = new File(deletingfilepath);
                        if(file.exists()){
                            file.delete();
                        }
                        break;
                    }
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
            projectfile.setProjectid(Integer.valueOf(faultid));
            projectfile.setFilename(fileName);
//            projectfile.setFilepath(relatedJarPath);
            projectfile.setFilepath("/fault_" + faultid + "/");

//            projectfile.setFileid(Long.valueOf(projectid));
            projectfile.setFiletype(fileName.substring(fileName.lastIndexOf(".") + 1));
            projectfile.setFilesize(file.getSize());
            projectfile.setType(3L);

            fenhuoProjectfileService.save(projectfile);
            //////////////////

            System.out.println("---------faultid: " + faultid + "-----UploadFileConfig.getLocaluploadpath():" + uploadFileConfig.getLocaluploadpath());

//            FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
//            String projectFileDir = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + "/";
            String projectFileDir = projuploadir + projectfile.getFilepath();
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

                List<String> urls;

//            LOGGER.info("上传成功");

            } catch (IOException e) {
                e.printStackTrace();
//            LOGGER.error(e.toString(), e);
            }
        }
        return R.ok();
    }

    @RequestMapping("/faultfilelist/{faultid}")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:projectfilelist")
    public R listfaultfile(@PathVariable("faultid") String faultid){

        List<ProjectRelatedfileObj> filenames = new ArrayList<ProjectRelatedfileObj>();


        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                .eq("projectid", Integer.valueOf(faultid));
        List<FenhuoProjectfileEntity> filelist = fenhuoProjectfileService.list(queryWrapper);
        for(FenhuoProjectfileEntity relatedfile :filelist){
            String filename = relatedfile.getFilename();
            ProjectRelatedfileObj fileobj = new ProjectRelatedfileObj();
            fileobj.setUid(String.valueOf(filename.hashCode()));
            fileobj.setName(filename);
            filenames.add(fileobj);
        }

        return R.ok().put("relatedfilelist", filenames);

    }

    @GetMapping("/download")
    @RequiresPermissions("fenhuo:proj:relatedfile:download")
    public void downloadfaultFile(HttpServletRequest request, HttpServletResponse res) {

        fenhuoFaultService.relatedFileDownload(request, res);

    }
}
