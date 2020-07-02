package io.renren.modules.fenhuo.service;

import java.util.Map;

public interface IJGPushService {
    /**
     * 通知甲方
     */
    void notifyPartyAs(String projectId, String title, String content, Map<String,String> extras, String msgType, String msgRemark);
    /**
     * 通知维护人员
     */
    void notifyServicers(String projectId,String title,String content, Map<String,String> extras,String msgType,String msgRemark);
    /**
     * 通知项目负责人
     */
    void notifyHeader(String projectId,String title,String content, Map<String,String> extras,String msgType,String msgRemark);
    /**
     * 通知系统管理员
     */
    void notifyAdmin(String title,String content, Map<String,String> extras,String msgType,String msgRemark);
}
