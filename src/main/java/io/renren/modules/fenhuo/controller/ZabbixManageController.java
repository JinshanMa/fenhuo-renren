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

import io.renren.modules.fenhuo.entity.ZabbixManageEntity;
import io.renren.modules.fenhuo.service.ZabbixManageService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2021-06-24 13:54:00
 */
@RestController
@RequestMapping("fenhuo/zabbixmanage")
public class ZabbixManageController {
    @Autowired
    private ZabbixManageService zabbixManageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:zabbixmanage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zabbixManageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{zabbixId}")
    @RequiresPermissions("fenhuo:zabbixmanage:info")
    public R info(@PathVariable("zabbixId") Integer zabbixId){
		ZabbixManageEntity zabbixManage = zabbixManageService.getById(zabbixId);

        return R.ok().put("zabbixManage", zabbixManage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:zabbixmanage:save")
    public R save(@RequestBody ZabbixManageEntity zabbixManage){
		zabbixManageService.save(zabbixManage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:zabbixmanage:update")
    public R update(@RequestBody ZabbixManageEntity zabbixManage){
		zabbixManageService.updateById(zabbixManage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:zabbixmanage:delete")
    public R delete(@RequestBody Integer[] zabbixIds){
		zabbixManageService.removeByIds(Arrays.asList(zabbixIds));

        return R.ok();
    }

}
