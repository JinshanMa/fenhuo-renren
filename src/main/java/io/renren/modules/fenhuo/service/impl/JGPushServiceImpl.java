package io.renren.modules.fenhuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.netty.util.internal.StringUtil;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoPushlogService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.IJGPushService;
import io.renren.modules.fenhuo.utils.JGPushUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("jGPushService")
public class JGPushServiceImpl implements IJGPushService {
    private static Logger logger = LoggerFactory.getLogger(JGPushServiceImpl.class);
    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;
    @Autowired

    private FenhuoPushlogService fenhuoPushlogService;


    @Override
    public void notifyPartyAs(String projectId, String title, String content, Map<String,String> extras, String msgType, String msgRemark) {

        FenhuoProjectinfoEntity fenhuoProjectinfoEntity = getProjectInfo(projectId, title, content);

        String partyids = fenhuoProjectinfoEntity.getPartyaid();

        pushAndSavePushmsg(partyids, projectId, title, content, extras);

//        String[] partidsArray = partyids.split(",");
//
//        if (partidsArray.length>0) {
//            QueryWrapper<FenhuoUsersEntity> userFilter = new QueryWrapper<>();
//            userFilter.in("userid",partidsArray);
//            List<FenhuoUsersEntity> users = fenhuoUsersService.list(userFilter);
//            if (users == null || users.size()==0){
//                logger.info("未找到相应用户，推送失败");
//                return;
//            }
//            for (FenhuoUsersEntity user : users) {
//                if (!StringUtils.isNotBlank(user.getPushid())) {
//                    logger.info("给甲方推送通知，推送ID：" + user.getPushid());
//                    boolean b = JGPushUtil.pushMsgByRegID(user.getPushid(), title, content, extras);
//                    if (b) {
//                        FenhuoPushlogEntity pushlog = new FenhuoPushlogEntity();
//                        pushlog.setProjectid(Long.valueOf(projectId));
//                        pushlog.setPushid(user.getPushid());
//                        pushlog.setPushtitle(title);
//                        pushlog.setPushtxt(content);
//                        pushlog.setPushtime(new Date());
//                        boolean insert = fenhuoPushlogService.save(pushlog);
//                        logger.info("给甲方推送通知，添加推送日志结果：" + insert);
//                    }
//                } else {
//                    logger.info("用户："+user.getUserid()+"推送ID为空，无法推送！" );
//                }
//            }
//        }else {
//            logger.info("未找到相应甲方，添加推送日志结果："+false);
//        }
    }

    @Override
    public void notifyServicers(String projectId, String title, String content, Map<String,String> extras,String msgType,String msgRemark) {

        FenhuoProjectinfoEntity fenhuoProjectinfoEntity = getProjectInfo(projectId, title, content);

        String servicesrids = fenhuoProjectinfoEntity.getServicemid();

        boolean isok = pushAndSavePushmsg(servicesrids, projectId, title, content, extras);
    }

    @Override
    public void notifyHeader(String projectId, String title, String content, Map<String,String> extras,String msgType,String msgRemark) {
        FenhuoProjectinfoEntity fenhuoProjectinfoEntity = getProjectInfo(projectId, title, content);

        String headerids = fenhuoProjectinfoEntity.getHeadid();

        pushAndSavePushmsg(headerids, projectId, title, content, extras);
    }

    @Override
    public void notifyAdmin(String title, String content, Map<String, String> extras, String msgType,String msgRemark) {

    }

    private FenhuoProjectinfoEntity getProjectInfo(String projectId, String title, String content){
        logger.info("给维护人员推送通知，入参："+projectId+"--"+title+"--"+ content);
        QueryWrapper<FenhuoProjectinfoEntity> filter = new QueryWrapper<>();
        filter.eq("projectid",Long.valueOf(projectId));
        return fenhuoProjectinfoService.getOne(filter);
    }
    private boolean pushAndSavePushmsg(String servicesrids,String projectId, String title, String content, Map<String,String> extras){
        String[] servicersIdsArray = servicesrids.split(",");
        if (servicersIdsArray.length>0){
            QueryWrapper<FenhuoUsersEntity> userFilter = new QueryWrapper<>();
            userFilter.in("userid",servicersIdsArray);
            List<FenhuoUsersEntity> users = fenhuoUsersService.list(userFilter);
            if (users == null || users.size()==0){
                logger.info("未找到相应用户，推送失败");
                return false;
            }
            for (FenhuoUsersEntity user : users){
                if (StringUtils.isNotBlank(user.getPushid())) {
                    logger.info("给维护人员推送通知，推送ID："+user.getPushid());
                    boolean b = JGPushUtil.pushMsgByRegID(user.getPushid(), title, content, extras);
                    if (b){
                        FenhuoPushlogEntity pushlog = new FenhuoPushlogEntity();
                        pushlog.setProjectid(Long.valueOf(projectId));
                        pushlog.setPushid(user.getPushid());
                        pushlog.setPushtitle(title);
                        pushlog.setPushtxt(content);
                        pushlog.setPushtime(new Date());
                        boolean insert = fenhuoPushlogService.save(pushlog);
                        logger.info("给维护人员推送通知，添加推送日志结果："+insert);
                        return true;
                    }
                } else {
                    logger.info("用户："+user.getUserid()+"推送ID为空，无法推送！" );
                    return false;
                }
            }
        }else {
            logger.info("未找到相应维护人员，添加推送日志结果："+false);
            return  false;
        }
        return false;
    }
}
