package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.config.UploadFileConfig;
import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectfileService;
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.fenhuo.utils.ProjectRelatedfileObj;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.io.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app/file")
public class AppFileController {
    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;

    @Autowired
    private UploadFileConfig uploadFileConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoProjectfileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fileid}")
    public R info(@PathVariable("fileid") Long fileid){
        FenhuoProjectfileEntity fenhuoProjectfile = fenhuoProjectfileService.getById(fileid);

        return R.ok().put("fenhuoProjectfile", fenhuoProjectfile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoProjectfileEntity fenhuoProjectfile){
        fenhuoProjectfileService.save(fenhuoProjectfile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
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
    public R uploadRelatedFile(HttpServletRequest request, @RequestParam(value = "id",required = false) String id, @RequestParam("type") long type){


        OpUtils opUtils = new OpUtils();
        String opPath = opUtils.getPath();
        if (!opUtils.getPath().endsWith("/")){
            opPath += "/";
        }
        String projuploadir = opPath + uploadFileConfig.getLocaluploadpath();
        if (projuploadir.contains("file:")){
            projuploadir = projuploadir.replace("file:","");
        }

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");

        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();

            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                // 自己的保存文件逻辑
                String fileName = entry.getKey();

                ///// 把项目信息存入项目文件表中
                String relatedJarPath = opUtils.getPath();

                FenhuoProjectfileEntity projectfile = new FenhuoProjectfileEntity();

                if (StringUtils.isNotBlank(id)){
                    projectfile.setProjectid(Integer.valueOf(id));
                }
                projectfile.setFilename(fileName);

                if (type == 1){
                    projectfile.setFilepath("/skill/");
                }else if (type == 2){
                    projectfile.setFilepath("/proj_" + id + "/");
                }else if (type == 3){
                    projectfile.setFilepath("/fault_" + id + "/" );
                }
                projectfile.setFiletype(fileName.substring(fileName.lastIndexOf(".") + 1));
                projectfile.setFilesize(entry.getValue().getSize());
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
                        entry.getValue().transferTo(dest);
//                    String fullpath = dest.getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        }



//    @PostMapping("/upload")
//    public R uploadRelatedFile(@RequestParam(value = "id",required = false) String id,
//                               @RequestParam("file") MultipartFile[] files,
//                               @RequestParam("type") long type){

        return R.ok();

    }

    @GetMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse res) {
        String fileid = request.getParameter("fileid");

//        String path = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + "/";
        OpUtils opUtils = new OpUtils();
        String projuploadir = opUtils.getPath() + uploadFileConfig.getLocaluploadpath();

        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new  QueryWrapper<FenhuoProjectfileEntity>()
                .eq("fileid", Integer.valueOf(fileid));

        FenhuoProjectfileEntity file = fenhuoProjectfileService.list(queryWrapper).get(0);

        String path = projuploadir + file.getFilepath();
        String fileName = file.getFilename();
        String filePath = path + fileName;
        File excelFile = new File(filePath);
        res.setCharacterEncoding("UTF-8");
        res.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        res.setContentType("application/octet-stream;charset=UTF-8");
        //加上设置大小下载下来的.xlsx文件打开时才不会报“Excel 已完成文件级验证和修复。此工作簿的某些部分可能已被修复或丢弃”
        res.addHeader("Content-Length", String.valueOf(excelFile.length()));
        try {
            res.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName.trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
//                    log.error("【下载模板】{}",e);
                    e.printStackTrace();
                }
            }
        }
    }



    @RequestMapping("/filelist/")
    public R listProjectfile(@RequestParam(value = "id",required = false) String id,@RequestParam("type") Long type){


        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = null;
        if (StringUtils.isNotBlank(id)){
            queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                    .eq("projectid", Integer.valueOf(id));
            queryWrapper.and(wrapper->wrapper.eq("type", type));
        }else {
            queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                    .eq("type", type);
        }


        List<FenhuoProjectfileEntity> filelist = fenhuoProjectfileService.list(queryWrapper);

        return R.ok().put("relatedfilelist", filelist);

    }

}
