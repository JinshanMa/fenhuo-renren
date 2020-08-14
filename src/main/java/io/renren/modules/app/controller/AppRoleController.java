package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.service.SysRoleService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppRoleController {
    @Autowired
    SysRoleService sysRoleService;

    @Login
    @RequestMapping("admin/role")
    public R getAdminRoleList(@RequestParam Map<String, Object> params){

        PageUtils page = sysRoleService.queryPage(params);

        List<SysRoleEntity> roles = (List<SysRoleEntity>)page.getList();
        for (SysRoleEntity r:roles) {
            if (r.getRoleId() == 1){
                roles.remove(r);
                break;
            }
        }
        page.setList(roles);

        return R.ok().put("page",page);
    }
}
