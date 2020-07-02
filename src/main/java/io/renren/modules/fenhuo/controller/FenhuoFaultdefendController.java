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

import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultdefendService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 故障维护单
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:41:48
 */
@RestController
@RequestMapping("fenhuo/fenhuofaultdefend")
public class FenhuoFaultdefendController {
    @Autowired
    private FenhuoFaultdefendService fenhuoFaultdefendService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuofaultdefend:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoFaultdefendService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{defendid}")
    @RequiresPermissions("fenhuo:fenhuofaultdefend:info")
    public R info(@PathVariable("defendid") Integer defendid){
		FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(defendid);

        return R.ok().put("fenhuoFaultdefend", fenhuoFaultdefend);
    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("fenhuo:fenhuofaultdefend:save")
//    public R save(@RequestBody FenhuoFaultdefendEntity fenhuoFaultdefend){
//		fenhuoFaultdefendService.save(fenhuoFaultdefend);
//
//        return R.ok();
//    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuofaultdefend:update")
    public R update(@RequestBody FenhuoFaultdefendEntity fenhuoFaultdefend){
		fenhuoFaultdefendService.updateById(fenhuoFaultdefend);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuofaultdefend:delete")
    public R delete(@RequestBody Integer[] defendids){
		fenhuoFaultdefendService.removeByIds(Arrays.asList(defendids));

        return R.ok();
    }

}
