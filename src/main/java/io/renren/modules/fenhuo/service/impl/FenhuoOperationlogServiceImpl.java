package io.renren.modules.fenhuo.service.impl;

//import com.sun.javafx.collections.MappingChange;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.obj.FenhuoProjectinfoRequest;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.IJGPushService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoOperationlogDao;
import io.renren.modules.fenhuo.entity.FenhuoOperationlogEntity;
import io.renren.modules.fenhuo.service.FenhuoOperationlogService;


@Service("fenhuoOperationlogService")
public class FenhuoOperationlogServiceImpl extends ServiceImpl<FenhuoOperationlogDao, FenhuoOperationlogEntity> implements FenhuoOperationlogService {

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private IJGPushService jGPushService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String startDate = (String)params.get("startDate");

        String endDate = (String)params.get("endDate");

        String opusername = (String)params.get("opersoname");

        String opname = (String)params.get("opname");

        String projname = (String)params.get("projname");

        String projectid = (String)params.get("projectid");

        String projtypeid = (String)params.get("projtypeid");

        QueryWrapper<FenhuoOperationlogEntity>  queryWrapper = new QueryWrapper<FenhuoOperationlogEntity>();
        QueryWrapper<FenhuoOperationlogEntity> queryChild = (QueryWrapper<FenhuoOperationlogEntity>)queryWrapper.eq("isdelete", 0);

        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
//            queryChild.apply("date_format(create_time,'%Y-%m-%d') >= {0}", startDate)
//                    .apply("date_format(create_time,'%Y-%m-%d') <= {0}", endDate);
            queryChild.and(wrapper->wrapper.ge("date_format(opdatetime,'%Y-%m-%d')",startDate)
                    .le("date_format(opdatetime,'%Y-%m-%d')", endDate));
        }
        if(StringUtils.isNotBlank(opusername)){
            queryChild.and(wrapper->wrapper.like("opusername", opusername));
        }
        if(StringUtils.isNotBlank(opname)){
            queryChild.and(wrapper->wrapper.like("opname", opname));
        }
        if(StringUtils.isNotBlank(projname)){
            queryChild.and(wrapper->wrapper.like("projectname", projname));
        }
        if(StringUtils.isNotBlank(projectid)){
            queryChild.and(wrapper->wrapper.eq("projectid", projectid));
        }
        if(StringUtils.isNotBlank(projtypeid)){
            Set<Long> typeidList = new HashSet<Long>();
            QueryWrapper<FenhuoProjectinfoEntity>  fenhuoqueryWrapper = new QueryWrapper<FenhuoProjectinfoEntity>();
            fenhuoqueryWrapper.and(a->a.eq("projectypeid", projtypeid));
            List<FenhuoProjectinfoEntity> fenhuoSpecificList = fenhuoProjectinfoService.list(fenhuoqueryWrapper);
            for( FenhuoProjectinfoEntity entity : fenhuoSpecificList){
                typeidList.add(entity.getProjectid());
            }
            if (typeidList.size() > 0) {
                queryChild.and(wrapper -> wrapper.in("projectid", typeidList));
            }
        }
        queryChild.orderByDesc("logid");


        IPage<FenhuoOperationlogEntity> page = this.page(
                new Query<FenhuoOperationlogEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }


    @Override
    public void saveAppOpByParamThroughAspect(Long userid, Object[] paramsObj, String opname) {


        if (opname.equals("save")){//101
            FenhuoOperationlogEntity oplog = aspectCommonProcess(userid);
            FenhuoProjectinfoRequest requestObj = (FenhuoProjectinfoRequest)paramsObj[0];
            FenhuoProjectinfoEntity projectinfo = requestObj.getProjectinfo();
            String projectname = projectinfo.getProjectname();
            Long projectid = projectinfo.getProjectid();
            oplog.setProjectid(projectid);
            oplog.setProjectname(projectname);
            oplog.setIsdelete(0);
            oplog.setOpname("申请项目");
            save(oplog);
            Map<String,String> extras = new HashMap<>();
            extras.put("content","新建项目,待审核");
            extras.put("projectId",String.valueOf(projectid));
            extras.put("projectName",projectname);
            extras.put("msgType","extra-msgType");
            jGPushService.notifyAdmin(projectname, "新建项目,待审核", extras, null, null);
        }else if(opname.equals("rejectApplyProject") || opname.equals("applyActiveProject")
                || opname.equals("activeProject") || opname.equals("applyCloseProject")
                || opname.equals("closeProject")){
            String projectid = ((Map<String,String>) paramsObj[0]).get("projectid");
            if (StringUtils.isNotBlank(projectid)){
                FenhuoOperationlogEntity oplog = aspectCommonProcess(userid);
                FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(projectid);
                String projectname = projectinfo.getProjectname();
                oplog.setProjectid(Long.parseLong(projectid));
                oplog.setProjectname(projectname);
                oplog.setIsdelete(0);


                Map<String,String> extras = new HashMap<>();
                extras.put("projectId",String.valueOf(projectid));
                extras.put("projectName",projectname);
                extras.put("msgType","extra-msgType");

                if(opname.equals("rejectApplyProject")){//102 管理员操作

                    oplog.setOpname("拒绝项目申请");
                    extras.put("content","拒绝项目申请");
                    jGPushService.notifyHeader(projectid,"项目申请被拒绝","项目申请被拒绝",extras,"","");

                }else if (opname.equals("applyActiveProject")){//103  负责人操作
                    oplog.setOpname("申请激活项目");
                    extras.put("content","申请激活项目");
                    jGPushService.notifyAdmin(projectname, "申请激活项目", extras, null, null);

                }else if (opname.equals("activeProject")){//104 管理员操作
                    oplog.setOpname("激活项目");
                    extras.put("content","激活项目");

                    jGPushService.notifyHeader(projectid,"项目已激活","管理员已激活项目：" + projectname,extras,"","");

                }else if(opname.equals("applyCloseProject")){//105  负责人操作
                    oplog.setOpname("申请关闭项目");
                    extras.put("content","申请关闭项目");

                    jGPushService.notifyAdmin(projectname, "申请关闭项目", extras, null, null);

                }else if(opname.equals("closeProject")){//106  管理员操作
                    oplog.setOpname("关闭项目");
                    extras.put("content","关闭项目");

                    jGPushService.notifyHeader(projectid,"项目已关闭","管理员已关闭项目：" + projectname,extras,"","");
                }
                save(oplog);

            }
        }

    }



    @Override
    public void saveOpByParamThroughAspect(Long userid, Object[] paramsObj, String opname) {


        if(opname.equals("save") || opname.equals("update")) {
            FenhuoOperationlogEntity oplog = aspectCommonProcess(userid);
            FenhuoProjectinfoRequest requestObj = (FenhuoProjectinfoRequest)paramsObj[0];
            FenhuoProjectinfoEntity projectinfo = requestObj.getProjectinfo();
            String projectname = projectinfo.getProjectname();
            Long projectid = projectinfo.getProjectid();
            oplog.setProjectid(projectid);
            oplog.setProjectname(projectname);
            oplog.setIsdelete(0);
            if (opname.equals("save")) {
                oplog.setOpname("新建项目");
                Map<String,String> extras = new HashMap<>();
                extras.put("content","新建项目,待审核");
                extras.put("projectId",String.valueOf(projectid));
                extras.put("projectName",projectname);
                extras.put("msgType","extra-msgType");
                jGPushService.notifyAdmin(projectname, "新建项目,待审核", extras, null, null);
            }
            else
                oplog.setOpname("更新项目");
            save(oplog);
        }
        else if (opname.equals("delete") || opname.equals("active")
                || opname.equals("close") || opname.equals("faildaudit")) {

            String[] projectids = (String[])paramsObj[0];

            Iterator iters = Arrays.asList(projectids).iterator();
            while (iters.hasNext()) {
                FenhuoOperationlogEntity oplog = aspectCommonProcess(userid);
                Long detetingId = Long.parseLong((String) iters.next());
                FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(detetingId);
                if(opname.equals("delete"))
                    oplog.setOpname("删除项目");
                else if(opname.equals("active"))
                    oplog.setOpname("激活项目");
                else if(opname.equals("close"))
                    oplog.setOpname("关闭项目");
                else
                    oplog.setOpname("未审核通过");
                oplog.setIsdelete(0);
                oplog.setProjectid(projectinfo.getProjectid());
                oplog.setProjectname(projectinfo.getProjectname());
                save(oplog);
            }
        }else if(opname.equals("applyforaction")){


            String action = (String)paramsObj[0];
            String[] projectids = (String[])paramsObj[1];

//            List<String> list = Arrays.asList(projectids);
            for(String projid:projectids){
                FenhuoOperationlogEntity oplog = aspectCommonProcess(userid);
                if(action.equals("active")){
                    oplog.setOpname("申请激活项目");
                }else if(action.equals("close")){
                    oplog.setOpname("申请关闭项目");
                }
                oplog.setIsdelete(0);
                oplog.setProjectid(Long.valueOf(projid));
//                oplog.setProjectname();
                save(oplog);
            }


        }
    }

    @Override
    public boolean isDeleteByIds(Collection<? extends Serializable> idList) {
        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = (Long)iters.next();
            FenhuoOperationlogEntity fenhuoOplog = getById(detetingId);
            fenhuoOplog.setIsdelete(1);
            updateById(fenhuoOplog);
        }
        return true;
    }

    private FenhuoOperationlogEntity aspectCommonProcess(Long userid){
        FenhuoUsersEntity fenhuoUser = fenhuoUsersService.getById(userid);

        SysUserEntity sysUser = null;
        FenhuoOperationlogEntity oplog = new FenhuoOperationlogEntity();

        oplog.setOpdatetime(new Date());
        if(fenhuoUser == null){
            sysUser = sysUserService.getById(userid);
            String realname = sysUser.getUsername();
            oplog.setOpersonid(userid);
            oplog.setOpersoname(realname);
        } else {
            String realname = fenhuoUser.getRealname();
            oplog.setOpersonid(userid);
            oplog.setOpersoname(realname);
        }

        return oplog;
    }

}