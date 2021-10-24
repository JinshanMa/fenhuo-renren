package io.renren.modules.fenhuo.service.impl;

import io.renren.config.UploadFileConfig;
import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.*;
import io.renren.modules.fenhuo.utils.JGPushUtil;
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysConfigService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoProjectinfoDao;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("fenhuoProjectinfoService")
public class FenhuoProjectinfoServiceImpl extends ServiceImpl<FenhuoProjectinfoDao, FenhuoProjectinfoEntity> implements FenhuoProjectinfoService {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FenhuoZabbixhostService fenhuoZabbixhostService;

    @Autowired
    private FenhuoFaultService fenhuoFaultService;

    @Autowired
    private UploadFileConfig uploadFileConfig;


    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;

    public PageUtils queryPageWithParam(Map<String, Object> params) {

        String keyword = (String)params.get("keyword");
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");
        String type = (String)params.get("type");//类型id
        String statu = (String)params.get("statu");//项目状态

        QueryWrapper<FenhuoProjectinfoEntity> queryWrapper = new QueryWrapper<>();
        QueryWrapper<FenhuoProjectinfoEntity> queryChild = (QueryWrapper<FenhuoProjectinfoEntity>)queryWrapper.eq("isdelete", 0);
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            queryChild.and(wrapper->wrapper.ge("date_format(projectcreatetime,'%Y-%m-%d')",startDate)
                    .le("date_format(projectcreatetime,'%Y-%m-%d')", endDate));
        }
        if (StringUtils.isNotBlank(keyword)){
            queryChild.and(wrapper->wrapper.like("projectname", keyword));
        }

        if (StringUtils.isNotBlank(type)){
            queryChild.and(wrapper->wrapper.like("projectypeid", Integer.parseInt(type)));
        }

        if (StringUtils.isNotBlank(statu)){
            queryChild.and(wrapper->wrapper.like("auditstatus", Integer.parseInt(statu)));
        }

        IPage<FenhuoProjectinfoEntity> page = this.page(
                new Query<FenhuoProjectinfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String keyword = (String)params.get("projectname");
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");
        String headid = (String)params.get("headid");
        String partyaid = (String)params.get("partyaid");
        String servicemid = (String)params.get("servicemid");
        String type = (String)params.get("projectype");//类型id
        String statu = (String)params.get("statu");//项目状态

        QueryWrapper<FenhuoProjectinfoEntity> queryWrapper = new QueryWrapper<>();
        QueryWrapper<FenhuoProjectinfoEntity> queryChild = (QueryWrapper<FenhuoProjectinfoEntity>)queryWrapper.eq("isdelete", 0);

        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            queryChild.and(wrapper->wrapper.ge("date_format(projectcreatetime,'%Y-%m-%d')",startDate)
                    .le("date_format(projectcreatetime,'%Y-%m-%d')", endDate));
        }
        if (StringUtils.isNotBlank(keyword)){
            queryChild.and(wrapper->wrapper.like("projectname", keyword));
        }

        if (StringUtils.isNotBlank(headid)){
            queryChild.and(wrapper->wrapper.last(" headid REGEXP "+ getREPXEPContent((String)params.get("headid"))));
        }

        if (StringUtils.isNotBlank(partyaid)){
            queryChild.and(wrapper->wrapper.last("partyaid REGEXP "+ getREPXEPContent((String)params.get("partyaid"))));
        }

        if (StringUtils.isNotBlank(servicemid)){
            queryChild.and(wrapper->wrapper.last("servicemid REGEXP "+ getREPXEPContent((String)params.get("servicemid"))));
        }

        if (StringUtils.isNotBlank(type)){
            queryChild.and(wrapper->wrapper.like("projectypeid", Integer.parseInt(type)));
        }

        if (StringUtils.isNotBlank(statu)){
            queryChild.and(wrapper->wrapper.like("auditstatus", Integer.parseInt(statu)));
        }

        IPage<FenhuoProjectinfoEntity> page = this.page(
                new Query<FenhuoProjectinfoEntity>().getPage(params),
                queryWrapper
        );

        //        IPage<FenhuoProjectinfoEntity> page = this.page(
//                new Query<FenhuoProjectinfoEntity>().getPage(params),
//                new QueryWrapper<FenhuoProjectinfoEntity>().eq("isdelete", 0)
//                //.eq(StringUtils.isNotBlank((String)params.get("auditstatus")))
//                .and(StringUtils.isNotBlank((String)params.get("headid")), wrapper->wrapper.last(" headid REGEXP "+ getREPXEPContent((String)params.get("headid"))))
//                        .and(StringUtils.isNotBlank((String)params.get("partyaid")), wrapper->wrapper.last("partyaid REGEXP "+ getREPXEPContent((String)params.get("partyaid"))))
//                        .and(StringUtils.isNotBlank((String)params.get("servicemid")), wrapper->wrapper.last("servicemid REGEXP "+ getREPXEPContent((String)params.get("servicemid"))))
//
//        );


        return new PageUtils(page);
    }


    @Override
    public List<FenhuoProjectinfoEntity> queryActivePage() {


        QueryWrapper<FenhuoProjectinfoEntity> queryWrapper = new QueryWrapper<>();
        QueryWrapper<FenhuoProjectinfoEntity> queryChild = (QueryWrapper<FenhuoProjectinfoEntity>)queryWrapper.eq("isdelete", 0);
        queryChild.and(wrapper->wrapper.eq("auditstatus", 101).or().eq("auditstatus",102));

        List<FenhuoProjectinfoEntity> projectinfoEntityList = this.baseMapper.selectList(queryWrapper);


        return projectinfoEntityList;
    }

    @Override
    public PageUtils querySelectedPage(Map<String, Object> params) {
        IPage<FenhuoProjectinfoEntity> page = this.page(
                new Query<FenhuoProjectinfoEntity>().getPage(params),
                new QueryWrapper<FenhuoProjectinfoEntity>().eq("isdelete", 0)
//                        .and(wrapper->{
//                            if(StringUtils.isNotBlank((String)params.get("headid"))
//                                    || StringUtils.isNotBlank((String)params.get("partyaid"))
//                                || StringUtils.isNotBlank((String)params.get("servicemid"))) {
//                                return wrapper.eq("isactive", 1);
//                            }else{
//                                return wrapper.eq("1", "1");
//                            }
//                        })
                        .and(wrapper->wrapper.eq("isactive", 1))
                        .and(StringUtils.isNotBlank((String)params.get("headid")), wrapper->wrapper.last(" headid REGEXP "+ getREPXEPContent((String)params.get("headid"))))
                        .and(StringUtils.isNotBlank((String)params.get("partyaid")), wrapper->wrapper.last("partyaid REGEXP "+ getREPXEPContent((String)params.get("partyaid"))))
                        .and(StringUtils.isNotBlank((String)params.get("servicemid")), wrapper->wrapper.last("servicemid REGEXP "+ getREPXEPContent((String)params.get("servicemid"))))

        );
        return new PageUtils(page);
    }

    private String getREPXEPContent(String userID){
        String tempContent = "(^"+ userID +",)|(,"+userID+",)|(,"+userID+"$)|(^"+userID+"$)";

        return "\""+tempContent+"\"";
    }

    @Override
    public boolean saveProjectInfo(FenhuoProjectinfoEntity projectinfo) {
        if (projectinfo != null){
//            processRequestObj(projectinfo);
            if (projectinfo.getHeadid() != null && projectinfo.getHeadname() != null) {
                if (projectinfo.getAuditstatus() == 101) {
                    String[] headids = projectinfo.getHeadid().split(",");
                    String[] headnames = projectinfo.getHeadname().split(",");
                    FenhuoUsersEntity userHead = fenhuoUsersService.queryByUserId(headids[0]);
                    //JGPushUtil.pushMsgByRegID()

                    //通知所有系统管理员
                    List<SysUserEntity> sysUsers = sysUserService.list();
                    for (SysUserEntity sysUserEntity : sysUsers) {
                        if (sysUserEntity.getPushid() != null) {
                            if (headnames.length > 0) {
                                String title = headnames[0] + "申请创建项目";
                                String content = "项目管理员:" + headnames[0] + ",申请创建项目：" + projectinfo.getProjectname();
                                JGPushUtil.pushMsgByRegID(sysUserEntity.getPushid(), title, content, null);
                            }

                        }
                    }

                }

            }

            projectinfo.setIsactive(0);
            save(processRequestObj(projectinfo));
            return true;
        }

        return false;
    }

    @Override
    public boolean removeByIdsBySetIsDeleted(Collection<? extends Serializable> idList) {

//        Iterator iters = idList.iterator();
//        while (iters.hasNext()) {
//            Long detetingId = Long.parseLong((String) iters.next());
//            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
//            //fenhuoProjectinfo.setIsdelete(1);
//            //updateById(fenhuoProjectinfo);
//        }
        String[] projectids = idList.toArray(new String[]{});
        fenhuoFaultService.removeBySetisdeletedByProjectid(projectids);
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
    public boolean updateProjectInfoByIds(Collection<? extends Serializable> idList, int status) {

        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
            if (status == 104) {
                fenhuoProjectinfo.setIsactive(1);
                fenhuoProjectinfo.setRecentactivetime(new Date());
            } else if (status == 106) {
                fenhuoProjectinfo.setIsactive(0);
                fenhuoProjectinfo.setRecentclosetime(new Date());
            }
            fenhuoProjectinfo.setAuditstatus(status);

            String audiName = getSysConfig(String.valueOf(status)).getParamValue();

            fenhuoProjectinfo.setAuditname(audiName);

            updateById(fenhuoProjectinfo);
        }

        return true;
    }

//    @Override
//    public boolean closeProjectInfoByIds(Collection<? extends Serializable> idList) {
//        Iterator iters = idList.iterator();
//        while (iters.hasNext()) {
//            Long detetingId = Long.parseLong((String) iters.next());
//            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
//            fenhuoProjectinfo.setIsactive(0);
//            updateById(fenhuoProjectinfo);
//        }
//        return true;
//    }

//    @Override
//    public boolean failedProjectInfoByIds(Collection<? extends Serializable> idList) {
//        Iterator iters = idList.iterator();
//        while (iters.hasNext()) {
//            Long detetingId = Long.parseLong((String) iters.next());
//            FenhuoProjectinfoEntity fenhuoProjectinfo = getById(detetingId);
//            fenhuoProjectinfo.setAuditstatus(103);
//            fenhuoProjectinfo.setAuditname("未审核通过");
//            updateById(fenhuoProjectinfo);
//        }
//        return true;
//    }

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
        String projectypeid = String.valueOf(projectinfo.getProjectypeid());
//        String zabbixmngid = String.valueOf(projectinfo.getZabbixmngid());
        String taskid = String.valueOf(projectinfo.getTaskid());
        String auditStatus = String.valueOf(projectinfo.getAuditstatus());

        String headid = String.valueOf(projectinfo.getHeadid());
        String partyaid = String.valueOf(projectinfo.getPartyaid());
        String servicemid = String.valueOf(projectinfo.getServicemid());

        String projectypeName = "";
        if (!projectypeid.equals("0")){
            projectypeName = getSysConfig(projectypeid).getParamValue();
        }

        if (taskid!=null && taskid!="" && taskid!="null" && taskid!="0"){
            String taskName = getSysConfig(taskid).getParamValue();
            projectinfo.setTaskname(taskName);
        }

        String audiName = getSysConfig(auditStatus).getParamValue();

        String headNames = convertToStringName(getUsersMsg(headid));
        String partyAName = convertToStringName(getUsersMsg(partyaid));
        String serviceMName = convertToStringName(getUsersMsg(servicemid));

        projectinfo.setProjectype(projectypeName);
        projectinfo.setServiceditemetail(projectinfo.getServiceditemetail());

        projectinfo.setHeadname(headNames);
        projectinfo.setPartyaname(partyAName);
        projectinfo.setServicemname(serviceMName);
        projectinfo.setAuditname(audiName);
//        projectinfo.setZabbixmngid();
        projectinfo.setProjectcreatetime(new Date());
        projectinfo.setIsdelete(0);

        return projectinfo;
    }

    private SysConfigEntity getSysConfig(String queryKey){
        QueryWrapper<SysConfigEntity> serviceMsgConfigWrapper = new QueryWrapper<SysConfigEntity>()
                .eq(StringUtils.isNotBlank(queryKey),"param_key", queryKey);
        return sysConfigService.getOne(serviceMsgConfigWrapper);
    }
    private List<? extends Serializable> getUsersMsg(String userids){
        String[] useridArray = userids.split(",");

        QueryWrapper<FenhuoUsersEntity> fenhuoUserWrapper = new QueryWrapper<FenhuoUsersEntity>()
                .in(useridArray.length > 0,"userid", useridArray);
        List<FenhuoUsersEntity> fenhuoList = fenhuoUsersService.list(fenhuoUserWrapper);

        List<String> reverseUserid = new ArrayList<>();
        for(String userid : useridArray){
            Long longUserid = Long.valueOf(userid);
            if (longUserid < 0){
                longUserid = -longUserid;
            }
            reverseUserid.add(String.valueOf(longUserid));
        }
        String[] reverseArrayUserid = reverseUserid.toArray(new String[]{});
        if(fenhuoList.size() <= 0){
            QueryWrapper<SysUserEntity> sysUserWrapper = new QueryWrapper<SysUserEntity>()
                    .in(reverseArrayUserid.length > 0,"user_id", reverseArrayUserid);
            return  sysUserService.list(sysUserWrapper);
        }
        return fenhuoList;
    }
    private String convertToStringName(List<? extends Serializable> users){
        StringBuilder sb = new StringBuilder();

        if (users.size() > 0 && users.get(0) instanceof FenhuoUsersEntity){
            List<FenhuoUsersEntity> fenhuoUsers = (List<FenhuoUsersEntity>)users;
            for(FenhuoUsersEntity usersEntity : fenhuoUsers){
                sb.append(usersEntity.getRealname());
                sb.append(",");
            }
        }else if(users.size() > 0){
            List<SysUserEntity> sysUsers = (List<SysUserEntity>)users;
            for(SysUserEntity usersEntity : sysUsers){
                sb.append(usersEntity.getUsername());
                sb.append(",");
            }
        }
        if(users.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    @Override
    public void relatedFileDownload(HttpServletRequest request, HttpServletResponse res) {
//        ApplicationHome applicationHome = new ApplicationHome(getClass());
//        File jarFile = applicationHome.getSource();
//        String path = jarFile.getParentFile().toString();
//        path += "/pattern/excel/";

        String prjid = request.getParameter("projectid");

        FenhuoProjectinfoEntity projectinfo = getById(Long.valueOf(prjid));

//        String path = uploadFileConfig.getLocaluploadpath() + projectinfo.getProjectname() + "/";
        OpUtils opUtils = new OpUtils();
        String projuploadir = opUtils.getPath() + uploadFileConfig.getLocaluploadpath();

        QueryWrapper<FenhuoProjectfileEntity> queryWrapper = new  QueryWrapper<FenhuoProjectfileEntity>()
                .eq("projectid", Integer.valueOf(prjid));

        FenhuoProjectfileEntity file = fenhuoProjectfileService.list(queryWrapper).get(0);

        String path = projuploadir + file.getFilepath();
        String fileName = request.getParameter("fileName");
        String filePath = path + fileName;
        File excelFile = new File(filePath);
        res.setCharacterEncoding("UTF-8");
        res.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        res.setContentType("application/octet-stream;charset=UTF-8");
        //加上设置大小下载下来的.xlsx文件打开时才不会报“Excel 已完成文件级验证和修复。此工作簿的某些部分可能已被修复或丢弃”
        res.addHeader("Content-Length", String.valueOf(excelFile.length()));
        try {
            res.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName.trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
//                    log.error("【下载模板】{}",e);
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        OpUtils opUtils = new OpUtils();
        String projuploadir = opUtils.getPath() + new UploadFileConfig().getLocaluploadpath();
        System.out.println(projuploadir);

    }
}

