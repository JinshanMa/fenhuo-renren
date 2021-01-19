package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultdefendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

        PageUtils page = fenhuoFaultdefendService.queryWithStatu(params);
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

        return R.ok();//.put("id",fenhuoFaultdefend.getFaultid())
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FenhuoFaultdefendEntity fenhuoFaultdefend){
        if (fenhuoFaultdefend.getDefendresult().equals("2")){
            fenhuoFaultdefend.setDefendsubmitverifytime(new Date());
        }
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

    @RequestMapping("/updatetime")
    public R updatetime(@RequestParam(value = "faultid") long faultid, @RequestParam(value = "time") String time, @RequestParam(value = "type") int type){
        FenhuoFaultdefendEntity entity = fenhuoFaultdefendService.getById(faultid);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(time);
            if (type == 1){
                entity.setDefendvisittime(date);
            }else if (type == 2){
                entity.setDefendsetouttime(date);
            }else if (type == 3){
                entity.setDefendstarttime(date);
            }else if (type == 4){
                entity.setDefendendtime(date);
            }
            fenhuoFaultdefendService.saveOrUpdate(entity);
        }catch (Exception e){
            e.printStackTrace();
        }

        return R.ok();
    }


}
