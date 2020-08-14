package io.renren.modules.app.controller;

import io.renren.common.utils.R;
import io.renren.modules.app.form.ForgetForm;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/app")
public class AppForgetPwdController {

    @Autowired
    FenhuoUsersService fenhuoUsersService;


    @PostMapping("forget")
    public R forget(HttpServletRequest httpRequest, @RequestBody ForgetForm form){

        FenhuoUsersEntity fenhuoUsersEntity = fenhuoUsersService.queryByMobile(form.getMobile());
        if (fenhuoUsersEntity == null){
            return R.error(500,"用户不存在");
        }
        if (fenhuoUsersEntity.getStatus() == "0"){
            return R.error(500,"用户审核未通过");
        }

        final HttpSession httpSession = httpRequest.getSession();
        Object codeObj = httpSession.getAttribute(form.getMobile());
        if (codeObj == null){
            return R.error(500,"验证码过期");
        }else {
            String code = codeObj.toString();
            if (code.equals("") || code.length() == 0 || !code.equals(form.getValid())) {
                return R.error(500, "验证码无效");
            }
        }

        String salt = RandomStringUtils.randomAlphanumeric(20);
        fenhuoUsersEntity.setSalt(salt);
        fenhuoUsersEntity.setPassword(new Sha256Hash(form.getPassword(), salt).toHex());

        fenhuoUsersService.saveOrUpdate(fenhuoUsersEntity);

        return R.ok();
    }

}
