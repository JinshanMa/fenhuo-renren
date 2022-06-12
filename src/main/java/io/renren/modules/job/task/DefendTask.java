package io.renren.modules.job.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultdefendService;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.IJGPushService;
import io.renren.modules.fenhuo.utils.JGPushUtil;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.utils.ScheduleUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("defendTask")
public class DefendTask implements ITask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FenhuoFaultdefendService faultdefendService;

    @Autowired
    FenhuoProjectinfoService projectinfoService;

    @Autowired
    FenhuoUsersService usersService;

    @Autowired
    private IJGPushService jGPushService;

    @Override
    public void run(String params) {
        logger.debug("执行DefendTask");
        System.out.println("DefendTask执行");


        List<FenhuoFaultdefendEntity> faultdefendEntityList = (List<FenhuoFaultdefendEntity>)faultdefendService.queryPageWithStatu2();
        for (FenhuoFaultdefendEntity entity:faultdefendEntityList) {

            Date submitDate = entity.getDefendsubmitverifytime();
            if (submitDate == null){
                continue;
            }
            Date now = new Date();
            long betweenHour = DateUtil.between(now,submitDate, DateUnit.HOUR);
            if (betweenHour >= 48){
                entity.setDefendresult(3);
                faultdefendService.saveOrUpdate(entity);
                FenhuoProjectinfoEntity project = projectinfoService.getById(entity.getProjectid());

                String title = "维护单48小时自动确认完成";

                String content = "项目《" + entity.getProjectname() + "》" + "的维护单：" + entity.getFaultdesc()
                        + " 已维护完成且提交客户审核已48小时，系统自动确认维护完成。";

                jGPushService.notifyPartyAs(String.valueOf(entity.getProjectid()),title,content,null,"","");
            }

        }


    }

    public static void main(String[] args) {
//        try {
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            ScheduleJobEntity jobEntity = new ScheduleJobEntity();
//            jobEntity.setCronExpression("0 0 0 * * ?");
//            jobEntity.setParams("");
//            jobEntity.setBeanName("defendTask");
//            jobEntity.setRemark("项目到期提示");
//            jobEntity.setCreateTime(new Date());
//            jobEntity.setStatus(1);
//
//            ScheduleUtils.createScheduleJob(scheduler,jobEntity);
//        }catch (Exception e){
//            e.printStackTrace();
//        }






    }
}


