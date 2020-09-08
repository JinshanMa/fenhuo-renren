package io.renren.modules.job.task;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("projectTask")
public class ProjectTask implements ITask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FenhuoProjectinfoService fenhuoProjectinfoService;

    @Override
    public void run(String params) {

        String projectid = params;
        FenhuoProjectinfoEntity projectinfoEntity = fenhuoProjectinfoService.getById(projectid);

        if (projectinfoEntity != null){
            projectinfoEntity.setAuditstatus(107);

            fenhuoProjectinfoService.saveOrUpdate(projectinfoEntity);
        }

        logger.debug("ProjectTask定时任务正在执行，参数为：{}", params);

    }

}
