package io.renren.modules.fenhuo.service.impl;

import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultdefendService;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.IJGPushService;
import io.renren.modules.fenhuo.utils.JGPushUtil;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoFaultDao;
import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultService;


@Service("fenhuoFaultService")
public class FenhuoFaultServiceImpl extends ServiceImpl<FenhuoFaultDao, FenhuoFaultEntity> implements FenhuoFaultService {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private FenhuoFaultdefendService fenhuoFaultdefendService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private IJGPushService jGPushService;

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

        save(faultEntity);


        String faultdesc = faultEntity.getFaultdesc();
        // 获得项目的维护人ids
        QueryWrapper<FenhuoProjectinfoEntity> proinfoWrapper = new QueryWrapper<FenhuoProjectinfoEntity>()
                .eq("projectid", faultEntity.getProjectid());
        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getOne(proinfoWrapper);
        String mids = projectinfo.getServicemid();
        String names = projectinfo.getServicemname();
        String projectname = projectinfo.getProjectname();
        String[] maintainids = mids.split(",");
        String[] maintainNames = names.split(",");

        for(int i = 0;i < maintainids.length ;i++){
            FenhuoFaultdefendEntity fenhuoFaultdefend = new FenhuoFaultdefendEntity();

            fenhuoFaultdefend.setFaultid(faultEntity.getFaultid());

            // 维护人id 和 姓名
            fenhuoFaultdefend.setDefenderid(maintainids[i]);
            fenhuoFaultdefend.setDefendername(maintainNames[i]);
            fenhuoFaultdefend.setLocationtime(new Date());

            fenhuoFaultdefend.setPlan(faultEntity.getPlan());

            // 申报人姓名
            fenhuoFaultdefend.setCreaterid(faultEntity.getDeclarer());
            fenhuoFaultdefend.setCreatername(faultEntity.getDeclarername());

            fenhuoFaultdefend.setCreatetime(new Date());
            fenhuoFaultdefend.setProjectname(projectname);
            fenhuoFaultdefend.setFaultdesc(faultdesc);

            fenhuoFaultdefendService.save(fenhuoFaultdefend);
        }
        //jGPushService.notifyServicers(String.valueOf(projectinfo.getProjectid()), projectname, faultname, null, null, null);

        return true;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoFaultEntity> page = this.page(
                new Query<FenhuoFaultEntity>().getPage(params),
                new QueryWrapper<FenhuoFaultEntity>()
        );

        return new PageUtils(page);
    }

    private String getSysConfig(String queryKey){
        QueryWrapper<SysConfigEntity> serviceMsgConfigWrapper = new QueryWrapper<SysConfigEntity>()
                .eq(StringUtils.isNotBlank(queryKey),"param_key", queryKey);
        return sysConfigService.getOne(serviceMsgConfigWrapper).getParamValue();
    }
}