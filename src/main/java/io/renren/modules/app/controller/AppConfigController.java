package io.renren.modules.app.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/config")
public class AppConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SysConfigEntity config = sysConfigService.getById(id);

        return R.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    public R save(@RequestBody SysConfigEntity config){
        ValidatorUtils.validateEntity(config);

        sysConfigService.saveConfig(config);

        return R.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @PostMapping("/update")
    public R update(@RequestBody SysConfigEntity config){
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);

        return R.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        sysConfigService.deleteBatch(ids);

        return R.ok();
    }


    /**
     * 条件查询sysconfig的paramkey
     */
    @Login
    @RequestMapping("/queryrange")
    public R fenhuoList(@RequestParam Map<String, Object> params){
        List<SysConfigEntity> queryList = sysConfigService.getConfigByKeysRange(params);

        return R.ok().put("config", queryList);
    }

}
