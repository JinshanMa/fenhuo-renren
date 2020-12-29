package io.renren.modules.job.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.IJGPushService;
import io.renren.modules.fenhuo.utils.JGPushUtil;
import io.renren.modules.job.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("projectTask")
public class ProjectTask implements ITask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    ScheduleJobService scheduleJobService;

    @Autowired
    IJGPushService jgPushService;

    @Override
    public void run(String params) {


        List<FenhuoProjectinfoEntity> list = (List<FenhuoProjectinfoEntity>)fenhuoProjectinfoService.queryActivePage();

        for (FenhuoProjectinfoEntity project:list) {

            Date projectEndTime = project.getServiceendtime();

            Date now = new Date();

            long days = DateUtil.between(projectEndTime,now, DateUnit.DAY);
            int alertTime = project.getAlerttime();

            //到期前15天和前7天各推送一次
            if (days > 7 && days <= 15){
                if (alertTime == 0){
                    project.setAlerttime(1);
                    fenhuoProjectinfoService.saveOrUpdate(project);

                }else{
                    continue;
                }
            }else if (days > 0 && days <= 7){
                if (alertTime == 1){
                    project.setAlerttime(2);
                    fenhuoProjectinfoService.saveOrUpdate(project);
                    String title = "项目：" + project.getProjectname() + "还有7天即将到期";
                    String content = "尊敬的项目负责人！" + "你的项目《" + project.getProjectname() + "》将在" + project.getServiceendtime() + "结束服务";
                    jgPushService.notifyHeader(String.valueOf(project.getProjectid()),title,content,null,"","");
                }
            }


        }


        logger.debug("ProjectTask定时任务正在执行，参数为：{}", params);

    }

}
