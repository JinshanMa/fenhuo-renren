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

import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;
import io.renren.modules.fenhuo.service.FenhuoPushlogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-06-22 11:06:23
 */
@RestController
@RequestMapping("fenhuo/fenhuopushlog")
public class FenhuoPushlogController {
    @Autowired
    private FenhuoPushlogService fenhuoPushlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuopushlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoPushlogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("fenhuo:fenhuopushlog:info")
    public R info(@PathVariable("id") Long id){
		FenhuoPushlogEntity fenhuoPushlog = fenhuoPushlogService.getById(id);

        return R.ok().put("fenhuoPushlog", fenhuoPushlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuopushlog:save")
    public R save(@RequestBody FenhuoPushlogEntity fenhuoPushlog){
		fenhuoPushlogService.save(fenhuoPushlog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuopushlog:update")
    public R update(@RequestBody FenhuoPushlogEntity fenhuoPushlog){
		fenhuoPushlogService.updateById(fenhuoPushlog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuopushlog:delete")
    public R delete(@RequestBody Long[] ids){
		fenhuoPushlogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
