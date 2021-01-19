package io.renren.modules.fenhuo.controller;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.config.UploadFileConfig;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectfileService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 项目附件
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-08-31 16:19:46
 */
@RestController
@RequestMapping("fenhuo/file")
public class FenhuoProjectfileController extends AbstractController {
    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;


    @Autowired
    private UploadFileConfig uploadFileConfig;

    @Autowired
    private SysConfigService sysConfigService;
    /**
     * 技术文档列表
     */
    @RequestMapping("/skilllist")
    @RequiresPermissions("fenhuo:file:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoProjectfileService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 故障文件上传
     */
    @RequestMapping("/faultlist")
    @RequiresPermissions("fenhuo:faultfile:list")
    public R faultfilelist(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoProjectfileService.queryFaultlistPage(params);

        return R.ok().put("page", page);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{fileid}")
    @RequiresPermissions("fenhuo:file:info")
    public R info(@PathVariable("fileid") Long fileid){
		FenhuoProjectfileEntity fenhuoProjectfile = fenhuoProjectfileService.getById(fileid);

        return R.ok().put("file", fenhuoProjectfile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:file:save")
    public R save(@RequestBody FenhuoProjectfileEntity fenhuoProjectfile){
		fenhuoProjectfileService.save(fenhuoProjectfile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:file:update")
    public R update(@RequestBody FenhuoProjectfileEntity fenhuoProjectfile){
		fenhuoProjectfileService.updateById(fenhuoProjectfile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] fileids){

        OpUtils opUtils = new OpUtils();
        String projuploadir = opUtils.getPath() + uploadFileConfig.getLocaluploadpath();

        for (Long fileid: fileids){
            FenhuoProjectfileEntity projectfileEntity = fenhuoProjectfileService.getById(fileid);
            String deletingfilepath = projuploadir + projectfileEntity.getFilepath() + projectfileEntity.getFilename();
            File file = new File(deletingfilepath);
            if(file.exists()) {
                file.delete();
            }
        }
        fenhuoProjectfileService.removeByIds(Arrays.asList(fileids));

        return R.ok();
    }


    @RequestMapping("/upload")
    public R uploadRelatedFile(@RequestParam("filecatalogid") long filecatalogid,
                               @RequestParam(value = "id",required = false) String id,
                               @RequestParam("type") long type,
                               @RequestParam("files") MultipartFile[] files){
        String uploadusername;
        String filecatalogname = "no catalog";
        if(getUser() instanceof SysUserEntity){
            uploadusername = ((SysUserEntity)getUser()).getUsername();
        }else{
            uploadusername = ((FenhuoUsersEntity)getUser()).getRealname();
        }
        QueryWrapper<SysConfigEntity> queryWrapper = new QueryWrapper<SysConfigEntity>();
        QueryWrapper<SysConfigEntity> querychild = queryWrapper.eq("param_key", filecatalogid);
        List<SysConfigEntity> sysUserEntityList = sysConfigService.list(querychild);
        if (sysUserEntityList.size() > 0){
            SysConfigEntity sysConfigEntity = sysUserEntityList.get(0);
            filecatalogname = sysConfigEntity.getParamValue();
        }

        OpUtils opUtils = new OpUtils();
        String opPath = opUtils.getPath();
        if (!opUtils.getPath().endsWith("/")){
            opPath += "/";
        }
        String projuploadir = opPath + uploadFileConfig.getLocaluploadpath();
        if (projuploadir.contains("file:")){
            projuploadir = projuploadir.replace("file:","");
        }

//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        commonsMultipartResolver.setDefaultEncoding("utf-8");

//        if (commonsMultipartResolver.isMultipart(request)){
//            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
//            Map<String, MultipartFile> map = mulReq.getFileMap();

            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return R.error("filename is empty");
                }
                // 自己的保存文件逻辑
                String fileName = file.getOriginalFilename();

                ///// 把项目信息存入项目文件表中
                String relatedJarPath = opUtils.getPath();

                FenhuoProjectfileEntity projectfile = new FenhuoProjectfileEntity();

                if (StringUtils.isNotBlank(id)){
                    projectfile.setProjectid(Integer.valueOf(id));
                }
                projectfile.setFilename(fileName);
                projectfile.setTechcatalogid(filecatalogid);
                projectfile.setTechcatalogname(filecatalogname);

                if (type == 1){
                    projectfile.setFilepath("/skill/");
                }else if (type == 2){
                    projectfile.setFilepath("/proj_" + id + "/");
                }else if (type == 3){
                    projectfile.setFilepath("/fault_" + id + "/" );
                }
                projectfile.setFiletype(fileName.substring(fileName.lastIndexOf(".") + 1));
                projectfile.setFilesize(file.getSize());
                projectfile.setCreatedatetime(new Date());
                projectfile.setCreator(uploadusername);
                projectfile.setType(type);


                //System.out.println("---------projectid: " + id + "-----UploadFileConfig.getLocaluploadpath():" + uploadFileConfig.getLocaluploadpath());


                String projectFileDir = projuploadir + projectfile.getFilepath();
                System.out.println("========file path is:" + projectFileDir + "========");


                File projectUploadFileDir = new File(projectFileDir);
                if (!projectUploadFileDir.exists()) {
                    boolean ok = projectUploadFileDir.mkdir();
                    if (!ok) {
                        //故障附件是多个上传
                        if (type != 3) {
                            return R.error().put("msg", "project upload directory can not create!");
                        }
                    }
                }


                File dest = new File(projectFileDir + fileName);
                if (dest.exists()){
                    return R.error(500,"相同文件名已存在");
                }else{
                    fenhuoProjectfileService.save(projectfile);
                    try {
                        file.transferTo(dest);
//                    String fullpath = dest.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
//        }



//    @PostMapping("/upload")
//    public R uploadRelatedFile(@RequestParam(value = "id",required = false) String id,
//                               @RequestParam("file") MultipartFile[] files,
//                               @RequestParam("type") long type){

        return R.ok();

    }


}
