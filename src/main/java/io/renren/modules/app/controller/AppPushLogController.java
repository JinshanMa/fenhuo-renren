package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoPushlogService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.fenhuo.service.IJGPushService;
import oracle.jdbc.proxy.annotation.Methods;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app/fenhuopushlog")
public class AppPushLogController {
    @Autowired
    private FenhuoPushlogService fenhuoPushlogService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private IJGPushService jGPushService;

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


    @RequestMapping(value = "/zabbixAlert",method = RequestMethod.POST)
    public R zabbixAlert( @RequestParam("sendto") String sendto, @RequestParam("subject") String subject, @RequestParam("message") String message){

        Map<String, Object> map = new HashMap<>();
        map.put("zbusername",sendto);
        PageUtils page = fenhuoZabbixhostService.queryPage(map);
        if (page.getList().size() > 0){
            List<FenhuoZabbixhostEntity> zabbixhostEntities = (List<FenhuoZabbixhostEntity>)page.getList();
            FenhuoZabbixhostEntity zabbixhostEntity = zabbixhostEntities.get(0);

            Long projectid = zabbixhostEntity.getProjectid();

            FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);
            String heads = projectinfoEntity.getHeadid();
            String parts = projectinfoEntity.getPartyaid();
            String services = projectinfoEntity.getServicemid();

            Map<String,String> extras = new HashMap<>();
            extras.put("content",message);
            extras.put("projectId",String.valueOf(projectid));
            extras.put("msgType","extra-msgType");

            if (heads != null && !heads.isEmpty()){
                jGPushService.notifyHeader(String.valueOf(projectid),subject,message,extras,null,null);
            }

            if (parts != null && !parts.isEmpty()){
                jGPushService.notifyPartyAs(String.valueOf(projectid),subject,message,extras,null,null);
            }


            if (services != null && !services.isEmpty()){
                jGPushService.notifyServicers(String.valueOf(projectid),subject,message,extras,null,null);
            }
        }


        System.out.println("sendto" + sendto + " subject:" + subject + " message:" + message);

        return R.ok();
    }
}
