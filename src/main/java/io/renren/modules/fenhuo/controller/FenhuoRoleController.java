package io.renren.modules.fenhuo.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.fenhuo.entity.FenhuoRoleEntity;
import io.renren.modules.fenhuo.service.FenhuoRoleService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 角色表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-04-30 09:15:45
 */
@RestController
@RequestMapping("fenhuo/fenhuorole")
public class FenhuoRoleController {
    @Autowired
    private FenhuoRoleService fenhuoRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuorole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleid}")
    @RequiresPermissions("fenhuo:fenhuorole:info")
    public R info(@PathVariable("roleid") String roleid){
		FenhuoRoleEntity fenhuoRole = fenhuoRoleService.getById(roleid);

        return R.ok().put("fenhuoRole", fenhuoRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuorole:save")
    public R save(@RequestBody FenhuoRoleEntity fenhuoRole){
		fenhuoRoleService.save(fenhuoRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuorole:update")
    public R update(@RequestBody FenhuoRoleEntity fenhuoRole){
		fenhuoRoleService.updateById(fenhuoRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuorole:delete")
    public R delete(@RequestBody String[] roleids){
		fenhuoRoleService.removeByIds(Arrays.asList(roleids));

        return R.ok();
    }

}
