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

import io.renren.modules.fenhuo.entity.FenhuoProjectservicelistEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectservicelistService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 项目维护人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:09
 */
@RestController
@RequestMapping("generator/fenhuoprojectservicelist")
public class FenhuoProjectservicelistController {
    @Autowired
    private FenhuoProjectservicelistService fenhuoProjectservicelistService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:fenhuoprojectservicelist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoProjectservicelistService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:fenhuoprojectservicelist:info")
    public R info(@PathVariable("id") Integer id){
		FenhuoProjectservicelistEntity fenhuoProjectservicelist = fenhuoProjectservicelistService.getById(id);

        return R.ok().put("fenhuoProjectservicelist", fenhuoProjectservicelist);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:fenhuoprojectservicelist:save")
    public R save(@RequestBody FenhuoProjectservicelistEntity fenhuoProjectservicelist){
		fenhuoProjectservicelistService.save(fenhuoProjectservicelist);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:fenhuoprojectservicelist:update")
    public R update(@RequestBody FenhuoProjectservicelistEntity fenhuoProjectservicelist){
		fenhuoProjectservicelistService.updateById(fenhuoProjectservicelist);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:fenhuoprojectservicelist:delete")
    public R delete(@RequestBody Integer[] ids){
		fenhuoProjectservicelistService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
