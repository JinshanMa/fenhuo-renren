package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultService;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("app/fault")
public class AppFaultController extends AbstractController {

    @Autowired
    private FenhuoFaultService fenhuoFaultService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoFaultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @Login
    @RequestMapping("/info/{faultid}")
    public R info(@PathVariable("faultid") String faultid){
        FenhuoFaultEntity fenhuoFault = fenhuoFaultService.getById(faultid);

        return R.ok().put("fenhuoFault", fenhuoFault);
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(@RequestBody FenhuoFaultEntity fenhuoFault){

        fenhuoFaultService.savefenhuofault(fenhuoFault);

        return R.ok().put("faultid", fenhuoFault.getFaultid());
    }


    /**
     * 修改
     */
    @Login
    @RequestMapping("/update")
    public R update(@RequestBody FenhuoFaultEntity fenhuoFault){
        fenhuoFaultService.updateById(fenhuoFault);

        return R.ok();
    }

    /**
     * 删除
     */
    @Login
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] faultids){
        fenhuoFaultService.removeByIds(Arrays.asList(faultids));

        return R.ok();
    }


}
