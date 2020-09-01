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

import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectfileService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 项目附件
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-08-31 16:19:46
 */
@RestController
@RequestMapping("fenhuo/fenhuoprojectfile")
public class FenhuoProjectfileController {
    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuoprojectfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoProjectfileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fileid}")
    @RequiresPermissions("fenhuo:fenhuoprojectfile:info")
    public R info(@PathVariable("fileid") Long fileid){
		FenhuoProjectfileEntity fenhuoProjectfile = fenhuoProjectfileService.getById(fileid);

        return R.ok().put("fenhuoProjectfile", fenhuoProjectfile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuoprojectfile:save")
    public R save(@RequestBody FenhuoProjectfileEntity fenhuoProjectfile){
		fenhuoProjectfileService.save(fenhuoProjectfile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuoprojectfile:update")
    public R update(@RequestBody FenhuoProjectfileEntity fenhuoProjectfile){
		fenhuoProjectfileService.updateById(fenhuoProjectfile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuoprojectfile:delete")
    public R delete(@RequestBody Long[] fileids){
		fenhuoProjectfileService.removeByIds(Arrays.asList(fileids));

        return R.ok();
    }

}
