package io.renren.modules.fenhuo.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 故障申报表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:42:15
 */
@RestController
@RequestMapping("fenhuo/fenhuofault")
public class FenhuoFaultController extends AbstractController {
    @Autowired
    private FenhuoFaultService fenhuoFaultService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fenhuo:fenhuofault:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fenhuoFaultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultid}")
    @RequiresPermissions("fenhuo:fenhuofault:info")
    public R info(@PathVariable("faultid") String faultid){
		FenhuoFaultEntity fenhuoFault = fenhuoFaultService.getById(faultid);

        return R.ok().put("fenhuoFault", fenhuoFault);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fenhuo:fenhuofault:save")
    public R save(@RequestBody FenhuoFaultEntity fenhuoFault){
        // 申报人id
        Long userid = getUserId();
        FenhuoUsersEntity userMsg = fenhuoUsersService.getById(userid);
        fenhuoFault.setDeclarer(String.valueOf(userid));

        // 设置申报人姓名
        fenhuoFault.setDeclarername(userMsg.getRealname());
		fenhuoFaultService.savefenhuofault(fenhuoFault);



        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fenhuo:fenhuofault:update")
    public R update(@RequestBody FenhuoFaultEntity fenhuoFault){
		fenhuoFaultService.updateById(fenhuoFault);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fenhuo:fenhuofault:delete")
    public R delete(@RequestBody String[] faultids){
		fenhuoFaultService.removeByIds(Arrays.asList(faultids));

        return R.ok();
    }

}
