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

import io.renren.modules.fenhuo.entity.FenhuoOperationlogEntity;
import io.renren.modules.fenhuo.service.FenhuoOperationlogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 16:03:28
 */
@RestController
@RequestMapping("fenhuo/fenhuooperationlog")
public class FenhuoOperationlogController {
    @Autowired
    private FenhuoOperationlogService fenhuoOperationlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuooperationlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoOperationlogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{logid}")
    @RequiresPermissions("fenhuo:fenhuooperationlog:info")
    public R info(@PathVariable("logid") Long logid){
		FenhuoOperationlogEntity fenhuoOperationlog = fenhuoOperationlogService.getById(logid);

        return R.ok().put("fenhuoOperationlog", fenhuoOperationlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuooperationlog:save")
    public R save(@RequestBody FenhuoOperationlogEntity fenhuoOperationlog){
		fenhuoOperationlogService.save(fenhuoOperationlog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuooperationlog:update")
    public R update(@RequestBody FenhuoOperationlogEntity fenhuoOperationlog){
		fenhuoOperationlogService.updateById(fenhuoOperationlog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuooperationlog:delete")
    public R delete(@RequestBody Long[] logids){
		fenhuoOperationlogService.isDeleteByIds(Arrays.asList(logids));
        return R.ok();
    }

}
