package io.renren.modules.app.controller;

import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("app/user")
public class AppUserController {


    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 列表
     */
    @RequestMapping("/role/list")
    public R roleList(@RequestParam Map<String, Object> params){

        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

//        params.put("isdelete", 1);
        PageUtils page = fenhuoUsersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userid}")
    public R info(@PathVariable("userid") String userid){
        FenhuoUsersEntity fenhuoUsers = fenhuoUsersService.getById(userid);

        return R.ok().put("fenhuoUsers", fenhuoUsers);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoUsersEntity fenhuoUsers){

        fenhuoUsersService.saveFenhuoUser(fenhuoUsers);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FenhuoUsersEntity fenhuoUsers){
        fenhuoUsersService.updateById(fenhuoUsers);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] userids){

//		fenhuoUsersService.removeByIds(Arrays.asList(userids));
        fenhuoUsersService.isDeleteByIds(Arrays.asList(userids));
        return R.ok();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params) throws Exception {
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
    public void downloadFile(HttpServletRequest request, HttpServletResponse res) {

        fenhuoUsersService.patternFileDownload(request, res);

    }


}
