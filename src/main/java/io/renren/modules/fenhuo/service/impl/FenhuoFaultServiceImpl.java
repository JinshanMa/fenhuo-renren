package io.renren.modules.fenhuo.service.impl;

import io.renren.config.UploadFileConfig;
import io.renren.modules.fenhuo.entity.*;
import io.renren.modules.fenhuo.service.*;
import io.renren.modules.fenhuo.utils.OpUtils;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
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

import io.renren.modules.fenhuo.dao.FenhuoFaultDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("fenhuoFaultService")
public class FenhuoFaultServiceImpl extends ServiceImpl<FenhuoFaultDao, FenhuoFaultEntity> implements FenhuoFaultService {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private FenhuoFaultdefendService fenhuoFaultdefendService;

    @Autowired
    private FenhuoFaultService fenhuoFaultService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private IJGPushService jGPushService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @Autowired
    private FenhuoProjectfileService fenhuoProjectfileService;

    //web
    public void saveFault(FenhuoFaultEntity faultEntity){
        faultEntity.setCreatetime(new Date());

        // 新添加的故障申报默认为  申报成功
        faultEntity.setFaultstatus(500);
        String faultstatustxt = getSysConfig(String.valueOf(500));
        faultEntity.setFaultstatustxt(faultstatustxt);

        save(faultEntity);


        String projectid = faultEntity.getProjectid();
        // 获得项目的维护人ids
        QueryWrapper<FenhuoProjectinfoEntity> proinfoWrapper = new QueryWrapper<FenhuoProjectinfoEntity>()
                .eq("projectid", projectid);

        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getOne(proinfoWrapper);
        String mids = projectinfo.getServicemid();
        String names = projectinfo.getServicemname();

        FenhuoFaultdefendEntity fenhuoFaultdefendEntity = new FenhuoFaultdefendEntity();
        fenhuoFaultdefendEntity.setFaultid(faultEntity.getFaultid());
        fenhuoFaultdefendEntity.setProjectid(Long.parseLong(projectid));
        fenhuoFaultdefendEntity.setProjectname(projectinfo.getProjectname());
        fenhuoFaultdefendEntity.setHostname(faultEntity.getHostname());
        fenhuoFaultdefendEntity.setHostid(faultEntity.getHostid());
        fenhuoFaultdefendEntity.setDefenderid(mids);
        fenhuoFaultdefendEntity.setDefendername(names);
        fenhuoFaultdefendEntity.setLocationtime(new Date());
        fenhuoFaultdefendEntity.setPlan("");

        // 申报人姓名
        fenhuoFaultdefendEntity.setCreaterid(faultEntity.getDeclarer());
        fenhuoFaultdefendEntity.setCreatername(faultEntity.getDeclarername());
        fenhuoFaultdefendEntity.setCreatetime(new Date());

        // 初始化维修时间和创建时间一样，前端判断如果和创建时间一样，则显示“维护未开始”
        fenhuoFaultdefendEntity.setDefendstarttime(new Date());
        fenhuoFaultdefendEntity.setDefendendtime(new Date());


        fenhuoFaultdefendEntity.setFaultdesc(faultEntity.getFaultdesc());
        fenhuoFaultdefendEntity.setDefendresult(0);
        String declarer = faultEntity.getDeclarer();
        FenhuoUsersEntity usersEntity = fenhuoUsersService.queryByUserId(declarer);
        //申报人是维护人员的话状态就为1
        if (usersEntity != null){
            if (usersEntity.getRoleid().equals("3")){
                fenhuoFaultdefendEntity.setDefendresult(1);
                fenhuoFaultdefendService.save(fenhuoFaultdefendEntity);
                Map<String,String> extras = new HashMap<>();
                extras.put("content","正在维护：" + faultEntity.getFaultdesc());
                extras.put("projectId",projectid);
                extras.put("projectName",projectinfo.getProjectname());
                extras.put("msgType","extra-msgType");
                jGPushService.notifyHeader(String.valueOf(projectinfo.getProjectid()), projectinfo.getProjectname(), faultEntity.getFaulttypename(), extras, null, null);
                return;
            }
        }
        fenhuoFaultdefendService.save(fenhuoFaultdefendEntity);

        Map<String,String> extras = new HashMap<>();
        extras.put("content",faultEntity.getFaultdesc());
        extras.put("projectId",projectid);
        extras.put("projectName",projectinfo.getProjectname());
        extras.put("msgType","extra-msgType");
        jGPushService.notifyServicers(String.valueOf(projectinfo.getProjectid()), projectinfo.getProjectname(), faultEntity.getFaulttypename(), extras, null, null);
    }

    //app
    @Override
    public boolean savefenhuofault(FenhuoFaultEntity faultEntity) {

        faultEntity.setCreatetime(new Date());

        // 设置故障类型名称
        String faultname = getSysConfig(String.valueOf(faultEntity.getFaulttype()));
        faultEntity.setFaulttypename(faultname);

        // 新添加的故障申报默认为  申报成功
        faultEntity.setFaultstatus(500);
        String faultstatustxt = getSysConfig(String.valueOf(500));
        faultEntity.setFaultstatustxt(faultstatustxt);

        Date faultStarttime = faultEntity.getStarttime();
        if (faultStarttime == null){
            faultEntity.setStarttime(new Date());
        }else{
            faultEntity.setStarttime(faultStarttime);
        }

        save(faultEntity);


        String faultdesc = faultEntity.getFaultdesc();

        String projectid = faultEntity.getProjectid();
        // 获得项目的维护人ids
        QueryWrapper<FenhuoProjectinfoEntity> proinfoWrapper = new QueryWrapper<FenhuoProjectinfoEntity>()
                .eq("projectid", projectid);

        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getOne(proinfoWrapper);
//        String mids = projectinfo.getServicemid();
//        String names = projectinfo.getServicemname();
        String projectname = projectinfo.getProjectname();


        /////////////////////////////////////////////////////

        FenhuoFaultdefendEntity fenhuoFaultdefend = new FenhuoFaultdefendEntity();

        fenhuoFaultdefend.setProjectid(Long.valueOf(projectid));
        fenhuoFaultdefend.setFaultid(faultEntity.getFaultid());
        fenhuoFaultdefend.setHostname(faultEntity.getHostname());
        fenhuoFaultdefend.setHostid(faultEntity.getHostid());

        // 维护人id 和 姓名

        fenhuoFaultdefend.setDefenderid(projectinfo.getServicemid());
        fenhuoFaultdefend.setDefendername(projectinfo.getServicemname());
        fenhuoFaultdefend.setLocationtime(new Date());

        fenhuoFaultdefend.setPlan(faultEntity.getPlan());

        // 申报人姓名
        fenhuoFaultdefend.setCreaterid(faultEntity.getDeclarer());
        fenhuoFaultdefend.setCreatername(faultEntity.getDeclarername());

        Date iniDate = new Date();

        fenhuoFaultdefend.setCreatetime(iniDate);
        fenhuoFaultdefend.setDefendstarttime(faultStarttime);
        fenhuoFaultdefend.setDefendendtime(faultStarttime);



        fenhuoFaultdefend.setProjectname(projectname);
        fenhuoFaultdefend.setFaultdesc(faultdesc);

        // 未开始维护
        fenhuoFaultdefend.setDefendresult(0);

        String declarer = faultEntity.getDeclarer();
        FenhuoUsersEntity usersEntity = fenhuoUsersService.queryByUserId(declarer);
        //申报人是维护人员的话状态就为1
        if (usersEntity != null){
            if (usersEntity.getRoleid().equals("3")){
                fenhuoFaultdefend.setDefendresult(1);
                fenhuoFaultdefendService.save(fenhuoFaultdefend);
                Map<String,String> extras = new HashMap<>();
                extras.put("content","正在维护：" + faultEntity.getFaultdesc());
                extras.put("projectId",projectid);
                extras.put("projectName",projectinfo.getProjectname());
                extras.put("msgType","extra-msgType");
                jGPushService.notifyHeader(String.valueOf(projectinfo.getProjectid()), projectinfo.getProjectname(), faultEntity.getFaulttypename(), extras, null, null);
                return true;
            }
        }


        fenhuoFaultdefendService.save(fenhuoFaultdefend);
        /////////////////////////////////////////////////////

        Map<String,String> extras = new HashMap<>();
        extras.put("content",faultdesc);
        extras.put("projectId",projectid);
        extras.put("projectName",projectname);
        extras.put("msgType","extra-msgType");

        jGPushService.notifyServicers(String.valueOf(projectinfo.getProjectid()), projectname, faultname, extras, null, null);

        return true;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        String userid = (String)params.get("fenhuouserid");
        List<String> projectids = new ArrayList<String>();

        QueryWrapper<FenhuoProjectinfoEntity> query = new QueryWrapper<FenhuoProjectinfoEntity>().eq("isdelete", 0)
                .and(wrapper->wrapper.eq("isactive", 1))
                .and(StringUtils.isNotBlank((String)params.get("headid")), wrapper->wrapper.last(" headid REGEXP "+ getREPXEPContent((String)params.get("headid"))))
                .and(StringUtils.isNotBlank((String)params.get("partyaid")), wrapper->wrapper.last("partyaid REGEXP "+ getREPXEPContent((String)params.get("partyaid"))))
                .and(StringUtils.isNotBlank((String)params.get("servicemid")), wrapper->wrapper.last("servicemid REGEXP "+ getREPXEPContent((String)params.get("servicemid"))));
        List<FenhuoProjectinfoEntity>  projectinfos = fenhuoProjectinfoService.list(query);

        for(FenhuoProjectinfoEntity info :projectinfos){
            projectids.add(String.valueOf(info.getProjectid()));
        }
//        QueryWrapper<FenhuoFaultEntity> query2 = new QueryWrapper<FenhuoFaultEntity>().in("projectid", projectids);
//        fenhuoFaultService.list(query2);
//        if(fenhuouser.getRoleid().equals("1")){
//            // 甲方负责人
//            QueryWrapper<FenhuoProjectinfoEntity> query = new QueryWrapper<FenhuoProjectinfoEntity>().eq("partyaid", userid);
//            List<FenhuoProjectinfoEntity>  projectinfos = fenhuoProjectinfoService.list(query);
//
//            for(FenhuoProjectinfoEntity info :projectinfos){
//                projectids.add(String.valueOf(info.getProjectid());
//            }
//        }else if(fenhuouser.getRoleid().equals("2")){
//            // 项目负责人
//            QueryWrapper<FenhuoProjectinfoEntity> query = new QueryWrapper<FenhuoProjectinfoEntity>().eq("headid", userid);
//            List<FenhuoProjectinfoEntity>  projectinfos = fenhuoProjectinfoService.list(query);
//
//            for(FenhuoProjectinfoEntity info :projectinfos){
//                projectids.add(String.valueOf(info.getProjectid());
//            }
//        }else {
//            // 维护人员
//        }
        IPage<FenhuoFaultEntity> page = this.page(
                new Query<FenhuoFaultEntity>().getPage(params),
                new QueryWrapper<FenhuoFaultEntity>().eq("isdelete", 0)
                        .and(wrapper->wrapper.eq("faultstatus", (String)params.get("faultType")))
                        .and(projectids.size() > 0,wrapper -> wrapper.in("projectid", projectids))
        );

        return new PageUtils(page);
    }

    @Override
    public FenhuoFaultdefendEntity confirmeById(String faultid, FenhuoUsersEntity fenhuoUsers) {
//        Long id = Long.valueOf(faultid);

        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(faultid);

        String parentFaultid = fenhuoFaultdefend.getFaultid();


        FenhuoFaultEntity faultEntity =  fenhuoFaultService.getById(parentFaultid);

        faultEntity.setFaultstatus(504);
        faultEntity.setFaultstatustxt(getSysConfig("504"));
        fenhuoFaultService.updateById(faultEntity);
//        FenhuoFaultdefendEntity fenhuoFaultdefend = new FenhuoFaultdefendEntity();
//        fenhuoUsers.getUserid();
//        fenhuoUsers.getRealname();
//        fenhuoFaultdefendService.save()

        String faultdesc = faultEntity.getFaultdesc();

        String projectid = faultEntity.getProjectid();
        // 获得项目的维护人ids
        QueryWrapper<FenhuoProjectinfoEntity> proinfoWrapper = new QueryWrapper<FenhuoProjectinfoEntity>()
                .eq("projectid", projectid);

        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getOne(proinfoWrapper);
        String mids = projectinfo.getServicemid();
        String names = projectinfo.getServicemname();
        String projectname = projectinfo.getProjectname();



//        FenhuoFaultdefendEntity fenhuoFaultdefend = new FenhuoFaultdefendEntity();
//
//        fenhuoFaultdefend.setProjectid(Long.valueOf(projectid));
//        fenhuoFaultdefend.setFaultid(faultEntity.getFaultid());
//
//        // 维护人id 和 姓名
//        fenhuoFaultdefend.setDefenderid(String.valueOf(fenhuoUsers.getUserid()));
//        fenhuoFaultdefend.setDefendername(fenhuoUsers.getRealname());
//        fenhuoFaultdefend.setLocationtime(new Date());
//
//        fenhuoFaultdefend.setPlan(faultEntity.getPlan());
//
//        // 申报人姓名
//        fenhuoFaultdefend.setCreaterid(faultEntity.getDeclarer());
//        fenhuoFaultdefend.setCreatername(faultEntity.getDeclarername());
//
//        Date iniDate = new Date();
//
//        fenhuoFaultdefend.setCreatetime(iniDate);
//        fenhuoFaultdefend.setDefendstarttime(iniDate);
//        fenhuoFaultdefend.setDefendendtime(iniDate);
//
//        fenhuoFaultdefend.setProjectname(projectname);
//        fenhuoFaultdefend.setFaultdesc(faultdesc);
//        fenhuoFaultdefend.setDefendresult(0);

//        fenhuoFaultdefendService.updateById(fenhuoFaultdefend);
        return fenhuoFaultdefend;

    }

    @Override
    public void confirmToFaultdefend(FenhuoFaultdefendEntity faultDefend, Date beginDate, Date endate) {
//        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(faultDefend);
        faultDefend.setDefendstarttime(beginDate);
        faultDefend.setDefendstarttime(endate);
        faultDefend.setDefendresult(1);
        fenhuoFaultdefendService.updateById(faultDefend);
    }

    @Override
    public void postvalidate(String faultid) {
        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(faultid);
        fenhuoFaultdefend.setDefendresult(2);
        fenhuoFaultdefendService.updateById(fenhuoFaultdefend);
    }

    @Override
    public void passvalid(String faultid) {
        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(faultid);
        fenhuoFaultdefend.setDefendresult(3);
        fenhuoFaultdefendService.updateById(fenhuoFaultdefend);
    }

    @Override
    public void validfail(String faultid) {
        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(faultid);
        fenhuoFaultdefend.setDefendresult(1);
        fenhuoFaultdefendService.updateById(fenhuoFaultdefend);
    }

    @Override
    public void noideamaintain(String faultid) {
        FenhuoFaultdefendEntity fenhuoFaultdefend = fenhuoFaultdefendService.getById(faultid);
        fenhuoFaultdefend.setDefendresult(-1);
        fenhuoFaultdefendService.updateById(fenhuoFaultdefend);
    }

    private String getSysConfig(String queryKey){
        QueryWrapper<SysConfigEntity> serviceMsgConfigWrapper = new QueryWrapper<SysConfigEntity>()
                .eq(StringUtils.isNotBlank(queryKey),"param_key", queryKey);
        return sysConfigService.getOne(serviceMsgConfigWrapper).getParamValue();
    }

    private String getREPXEPContent(String userID){
        String tempContent = "(^"+ userID +",)|(,"+userID+",)|(,"+userID+"$)|(^"+userID+"$)";

        return "\""+tempContent+"\"";
    }


    @Override
    public void relatedFileDownload(HttpServletRequest request, HttpServletResponse res) {
//        ApplicationHome applicationHome = new ApplicationHome(getClass());
//        File jarFile = applicationHome.getSource();
//        String path = jarFile.getParentFile().toString();
//        path += "/pattern/excel/";

        String prjid = request.getParameter("faultid");

//        FenhuoFaultEntity projectinfo = getById(Long.valueOf(prjid));

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


}