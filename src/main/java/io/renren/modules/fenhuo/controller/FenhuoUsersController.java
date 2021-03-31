package io.renren.modules.fenhuo.controller;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 系统用户表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-06 09:50:33
 */
@RestController
@RequestMapping("fenhuo/fenhuousers")
public class FenhuoUsersController extends AbstractController {
    @Autowired
    private FenhuoUsersService fenhuoUsersService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuousers:list")
    public R list(@RequestParam Map<String, Object> params){

//        params.put("isdelete", 1);
        PageUtils page = fenhuoUsersService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/projectlist/{projectid}")
    @RequiresPermissions("fenhuo:fenhuoprojectinfo:list")
    public R projectUserlist(@PathVariable("projectid") String projectid, @RequestParam Map<String, Object> params){

        List<FenhuoUsersEntity> fenhuoUsers = fenhuoUsersService.queryProjectUserinfo(projectid, params);
        return R.ok().put("userlist", fenhuoUsers);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{userid}")
    @RequiresPermissions("fenhuo:fenhuousers:info")
    public R info(@PathVariable("userid") String userid){
		FenhuoUsersEntity fenhuoUsers = fenhuoUsersService.getById(userid);

        return R.ok().put("fenhuoUsers", fenhuoUsers);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuousers:save")
    public R save(@RequestBody FenhuoUsersEntity fenhuoUsers){

		fenhuoUsersService.saveFenhuoUser(fenhuoUsers);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuousers:update")
    public R update(@RequestBody FenhuoUsersEntity fenhuoUsers){
        fenhuoUsersService.updateById(fenhuoUsers);
        FenhuoUsersEntity dbfenhuouser = fenhuoUsersService.getById(fenhuoUsers.getUserid());
        if(StringUtils.isNotBlank(fenhuoUsers.getPassword())){
            String newpassword = fenhuoUsers.getPassword();
            //sha256加密
            String newHexPassword = new Sha256Hash(newpassword, dbfenhuouser.getSalt()).toHex();
            fenhuoUsersService.updatePassword(fenhuoUsers.getUserid(), newHexPassword);
        }


        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuousers:delete")
    public R delete(@RequestBody String[] userids){

//		fenhuoUsersService.removeByIds(Arrays.asList(userids));
        fenhuoUsersService.isDeleteByIds(Arrays.asList(userids));
        return R.ok();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @RequiresPermissions("fenhuo:user:batch:add")
    public R upload(@RequestParam("file") MultipartFile file,@RequestParam Map<String, Object> params) throws Exception {
        String userType = (String)params.get("userType");
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        String url = "upload:test"+suffix;

        fenhuoUsersService.batchAddUserByExcel(file, userType);

        return R.ok().put("url", url);
    }

    @GetMapping("/download")
    @RequiresPermissions("fenhuo:user:batch:add")
    public void downloadFile(HttpServletRequest request,HttpServletResponse res) {

        fenhuoUsersService.patternFileDownload(request, res);

    }

}
