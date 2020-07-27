package io.renren.modules.fenhuo.service.impl;

import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysCaptchaService;
import io.renren.modules.sys.service.SysConfigService;
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

import io.renren.modules.fenhuo.dao.FenhuoProjectinfoDao;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;


@Service("fenhuoProjectinfoService")
public class FenhuoProjectinfoServiceImpl extends ServiceImpl<FenhuoProjectinfoDao, FenhuoProjectinfoEntity> implements FenhuoProjectinfoService {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoProjectinfoEntity> page = this.page(
                new Query<FenhuoProjectinfoEntity>().getPage(params),
                new QueryWrapper<FenhuoProjectinfoEntity>().eq("isdelete", 0)
                .and(StringUtils.isNotBlank((String)params.get("headid")), wrapper->wrapper.like("headid", (String)params.get("headid")))
                .and(StringUtils.isNotBlank((String)params.get("partyaid")), wrapper->wrapper.like("partyaid",(String)params.get("partyaid")))
                .and(StringUtils.isNotBlank((String)params.get("servicemid")), wrapper->wrapper.like("servicemid",(String)params.get("servicemid")))
        );
        return new PageUtils(page);
    }

    @Override
    public boolean saveProjectInfo(FenhuoProjectinfoEntity projectinfo) {
        if (projectinfo != null){
//            processRequestObj(projectinfo);
            projectinfo.setIsactive(0);
            save(processRequestObj(projectinfo));
            return true;
        }

        return false;
    }

    @Override
    public boolean removeByIdsBySetIsDeleted(Collection<? extends Serializable> idList) {

        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
            fenhuoProjectinfo.setIsdelete(1);
            updateById(fenhuoProjectinfo);
            fenhuoZabbixhostService.removeByIdsBySetIsDeleted(fenhuoProjectinfo.getProjectid());
        }
        return true;
    }

    @Override
    public boolean updateProjectInfo(FenhuoProjectinfoEntity projectinfo) {
        if (projectinfo != null){
//            processRequestObj(projectinfo);
            updateById(processRequestObj(projectinfo));
            return true;
        }

        return false;
    }

    @Override
    public boolean updateProjectInfoByIds(Collection<? extends Serializable> idList) {

        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
            fenhuoProjectinfo.setIsactive(1);
            updateById(fenhuoProjectinfo);
        }

        return true;
    }

    @Override
    public boolean closeProjectInfoByIds(Collection<? extends Serializable> idList) {
        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
            fenhuoProjectinfo.setIsactive(0);
            updateById(fenhuoProjectinfo);
        }
        return true;
    }

    @Override
    public boolean failedProjectInfoByIds(Collection<? extends Serializable> idList) {
        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
            fenhuoProjectinfo.setAuditstatus(103);
            fenhuoProjectinfo.setAuditname("未审核通过");
            updateById(fenhuoProjectinfo);
        }
        return true;
    }

    public List<FenhuoProjectinfoEntity> getProjectinfoByHeadid(String exp){
        return baseMapper.selectProjectinfoByHeaderid(exp);
    }

    @Override
    public List<FenhuoProjectinfoEntity> getProjectinfoByApartid(String exp) {
        return baseMapper.selectProjectinfoByApartid(exp);
    }

    @Override
    public List<FenhuoProjectinfoEntity> getProjectinfoByMaintainid(String exp) {
        return baseMapper.selectProjectinfoByMaintainid(exp);
    }

    private FenhuoProjectinfoEntity processRequestObj(FenhuoProjectinfoEntity projectinfo){
        String serviceId = String.valueOf(projectinfo.getServiceid());
        String taskid = String.valueOf(projectinfo.getTaskid());
        String auditStatus = String.valueOf(projectinfo.getAuditstatus());

        String headid = String.valueOf(projectinfo.getHeadid());
        String partyaid = String.valueOf(projectinfo.getPartyaid());
        String servicemid = String.valueOf(projectinfo.getServicemid());


//        String serviceName = getSysConfig(serviceId).getParamValue();
//        String taskName = getSysConfig(taskid).getParamValue();
//        String audiName = getSysConfig(auditStatus).getParamValue();

//        String headNames = convertToStringName(getUsersMsg(headid));
//        String partyAName = convertToStringName(getUsersMsg(partyaid));
//        String serviceMName = convertToStringName(getUsersMsg(servicemid));
//
//
//        projectinfo.setServiceditemetail(serviceName);
//        projectinfo.setTaskname(taskName);
//        projectinfo.setHeadname(headNames);
//        projectinfo.setPartyaname(partyAName);
//        projectinfo.setServicemname(serviceMName);
//        projectinfo.setAuditname(audiName);

        projectinfo.setProjectcreatetime(new Date());
        projectinfo.setIsdelete(0);

        return projectinfo;
    }

    private SysConfigEntity getSysConfig(String queryKey){
        QueryWrapper<SysConfigEntity> serviceMsgConfigWrapper = new QueryWrapper<SysConfigEntity>()
                .eq(StringUtils.isNotBlank(queryKey),"param_key", queryKey);
        return sysConfigService.getOne(serviceMsgConfigWrapper);
    }
    private List<FenhuoUsersEntity> getUsersMsg(String userids){
        String[] useridArray = userids.split(",");
        QueryWrapper<FenhuoUsersEntity> fenhuoUserWrapper = new QueryWrapper<FenhuoUsersEntity>()
                .in(useridArray.length > 0,"userid", useridArray);
        return fenhuoUsersService.list(fenhuoUserWrapper);
    }
    private String convertToStringName(List<FenhuoUsersEntity> users){
        StringBuilder sb = new StringBuilder();
        for(FenhuoUsersEntity usersEntity : users){
            sb.append(usersEntity.getRealname());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}