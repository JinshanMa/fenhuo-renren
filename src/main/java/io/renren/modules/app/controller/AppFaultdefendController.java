package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultdefendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("app/faultdefend")
public class AppFaultdefendController {

    @Autowired
    private FenhuoFaultdefendService fenhuoFaultdefendService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = fenhuoFaultdefendService.queryPage(params);
        return R.ok().put("page", page);

    }


    /**
     * 信息
     */
    @RequestMapping("/info/{defendid}")
    public R info(@PathVariable("defendid") Integer defendid){
        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(defendid);

        return R.ok().put("fenhuoFaultdefend", fenhuoFaultdefend);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoFaultdefendEntity fenhuoFaultdefend){

		fenhuoFaultdefendService.save(fenhuoFaultdefend);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FenhuoFaultdefendEntity fenhuoFaultdefend){
        fenhuoFaultdefendService.updateById(fenhuoFaultdefend);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] defendids){
        fenhuoFaultdefendService.removeByIds(Arrays.asList(defendids));

        return R.ok();
    }

}
