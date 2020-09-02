package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;
import io.renren.modules.fenhuo.service.FenhuoPushlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("app/fenhuopushlog")
public class AppPushLogController {
    @Autowired
    private FenhuoPushlogService fenhuoPushlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoPushlogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        FenhuoPushlogEntity fenhuoPushlog = fenhuoPushlogService.getById(id);

        return R.ok().put("fenhuoPushlog", fenhuoPushlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoPushlogEntity fenhuoPushlog){
        fenhuoPushlogService.save(fenhuoPushlog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FenhuoPushlogEntity fenhuoPushlog){
        fenhuoPushlogService.updateById(fenhuoPushlog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        fenhuoPushlogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
