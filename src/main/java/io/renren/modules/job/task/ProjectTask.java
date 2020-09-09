package io.renren.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;
import io.renren.modules.job.utils.ScheduleUtils;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("projectTask")
public class ProjectTask implements ITask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    ScheduleJobService scheduleJobService;

    @Override
    public void run(String params) {

        String projectid = params;
        FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);

        if (projectinfoEntity != null){

//            projectinfoEntity.setAuditstatus(107);
//            fenhuoProjectinfoService.saveOrUpdate(projectinfoEntity);
//
//            ScheduleJobEntity scheduleJobEntity = scheduleJobService.getOne(new QueryWrapper<ScheduleJobEntity>().eq("params",projectid));
//            try{
//                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//                ScheduleUtils.deleteScheduleJob(scheduler,scheduleJobEntity.getJobId());
//            }catch (Exception e){
//                e.printStackTrace();
//            }

            //ScheduleUtils.deleteScheduleJob();
        }

        logger.debug("ProjectTask定时任务正在执行，参数为：{}", params);

    }

}
