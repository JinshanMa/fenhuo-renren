package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.utils.JGPushUtil;
import io.renren.modules.sys.service.SysRoleService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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

        QueryWrapper<FenhuoUsersEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", fenhuoUsers.getMobile());
        FenhuoUsersEntity hasUser = fenhuoUsersService.getOne(queryWrapper);
        if (hasUser != null){
            return R.error(500,"号码已存在");
        }

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

//    @PostMapping("/modifypwd")
    @RequestMapping("/modifypwd")
    public R modifyPwd(@RequestParam("userid") String userid,@RequestParam("oldpwd") String oldpwd,@RequestParam("newpwd") String newpwd){

        FenhuoUsersEntity fenhuoUser = fenhuoUsersService.getById(userid);

        if(!fenhuoUser.getPassword().equals(new Sha256Hash(oldpwd, fenhuoUser.getSalt()).toHex())) {
            return R.error("密码错误");
        }

        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);

        fenhuoUser.setPassword(new Sha256Hash(newpwd, salt).toHex());
        fenhuoUser.setSalt(salt);
        fenhuoUsersService.saveOrUpdate(fenhuoUser);

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


    @RequestMapping("/verify/{userid}")
    public R verifyUser(@PathVariable("userid") String userid){

        FenhuoUsersEntity fenhuoUsersEntity = fenhuoUsersService.getById(userid);
        fenhuoUsersEntity.setStatus("1");

        fenhuoUsersService.saveOrUpdate(fenhuoUsersEntity);

        return R.ok();
    }

    @RequestMapping("/reject/{userid}")
    public R rejectUser(@PathVariable("userid") String userid){

        FenhuoUsersEntity fenhuoUsersEntity = fenhuoUsersService.getById(userid);
        fenhuoUsersEntity.setStatus("1");

        fenhuoUsersService.removeById(userid);

        YunpianClient clnt = new YunpianClient("2869591b63db6b66495e416eb13a105f").init();

        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, fenhuoUsersEntity.getMobile());
        param.put(YunpianClient.TEXT, "【江民信息】您注册的云桥账号：" + fenhuoUsersEntity.getMobile()  + "审核不通过。");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);

        clnt.close();
        if (r.getCode() == 0){
            return R.ok();
        }else{
            return R.error(500,r.getMsg() + "," + r.getDetail());
        }
    }


}
