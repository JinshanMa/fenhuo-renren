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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
        fenhuoProjectfileService.removeByIds(Arrays.asList(fileids));

        return R.ok();
    }


    @RequestMapping("/upload")
    public R uploadRelatedFile(HttpServletRequest request, @RequestParam(value = "id",required = false) String id, @RequestParam("type") long type){

//        Map<String, MultipartFile> fileMap = request.getFileMap();
//        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()){
//            System.out.println(entry.getKey());
//            System.out.println( entry.getValue());
//            System.out.println(entry.getValue().getOriginalFilename());
//        }

        OpUtils opUtils = new OpUtils();
        String projuploadir = opUtils.getPath() + uploadFileConfig.getLocaluploadpath();

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
                projectfile.setProjectid(Integer.valueOf(id));
                projectfile.setFilename(fileName);

                if (type == 1){
                    projectfile.setFilepath("/skill");
                }else if (type == 2){
                    projectfile.setFilepath("/proj_" + id + "/");
                }else if (type == 3){
                    projectfile.setFilepath("/fault_" + id + "/" );
                }
                projectfile.setFiletype(fileName.substring(fileName.lastIndexOf(".") + 1));
                projectfile.setFilesize(entry.getValue().getSize());
                projectfile.setType(type);

                fenhuoProjectfileService.save(projectfile);


                System.out.println("---------projectid: " + id + "-----UploadFileConfig.getLocaluploadpath():" + uploadFileConfig.getLocaluploadpath());


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
                    entry.getValue().transferTo(dest);
                    String fullpath = dest.getAbsolutePath();

                    List<String> urls;
                } catch (IOException e) {
                    e.printStackTrace();
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

    }



    @RequestMapping("/filelist/")
    public R listProjectfile(@RequestParam(value = "id",required = false) String projectid,@RequestParam("type") Long type){

        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>()
                .eq("projectid", Integer.valueOf(projectid));
        queryWrapper.and(wrapper->wrapper.like("type", type));


        List<FenhuoProjectfileEntity> filelist = fenhuoProjectfileService.list(queryWrapper);

        return R.ok().put("relatedfilelist", filelist);

    }

}
