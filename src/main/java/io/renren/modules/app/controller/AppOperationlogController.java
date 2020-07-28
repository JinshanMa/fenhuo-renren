package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoOperationlogEntity;
import io.renren.modules.fenhuo.service.FenhuoOperationlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("app/operationlog")
public class AppOperationlogController {

    @Autowired
    private FenhuoOperationlogService fenhuoOperationlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoOperationlogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{logid}")
    public R info(@PathVariable("logid") Long logid){
        FenhuoOperationlogEntity fenhuoOperationlog = fenhuoOperationlogService.getById(logid);

        return R.ok().put("fenhuoOperationlog", fenhuoOperationlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoOperationlogEntity fenhuoOperationlog){
        fenhuoOperationlogService.save(fenhuoOperationlog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FenhuoOperationlogEntity fenhuoOperationlog){
        fenhuoOperationlogService.updateById(fenhuoOperationlog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] logids){
        fenhuoOperationlogService.isDeleteByIds(Arrays.asList(logids));
        return R.ok();
    }

}
