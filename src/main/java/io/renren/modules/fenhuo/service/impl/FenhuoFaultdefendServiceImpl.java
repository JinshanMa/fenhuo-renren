package io.renren.modules.fenhuo.service.impl;

import com.mchange.lang.LongUtils;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoFaultdefendDao;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.service.FenhuoFaultdefendService;


@Service("fenhuoFaultdefendService")
public class FenhuoFaultdefendServiceImpl extends ServiceImpl<FenhuoFaultdefendDao, FenhuoFaultdefendEntity> implements FenhuoFaultdefendService {

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

//        boolean istrue = StringUtils.isNotBlank(params.get("headid"));

        List<String> projectids = new ArrayList<String>();

        QueryWrapper<FenhuoProjectinfoEntity> query = new QueryWrapper<FenhuoProjectinfoEntity>().eq("isdelete", 0)
                .and(StringUtils.isNotBlank((String)params.get("headid")), wrapper->wrapper.last(" headid REGEXP "+ getREPXEPContent((String)params.get("headid"))))
                .and(StringUtils.isNotBlank((String)params.get("partyaid")), wrapper->wrapper.last("partyaid REGEXP "+ getREPXEPContent((String)params.get("partyaid"))))
                .and(StringUtils.isNotBlank((String)params.get("servicemid")), wrapper->wrapper.last("servicemid REGEXP "+ getREPXEPContent((String)params.get("servicemid"))));
        List<FenhuoProjectinfoEntity>  projectinfos = fenhuoProjectinfoService.list(query);

        for(FenhuoProjectinfoEntity info :projectinfos){
            projectids.add(String.valueOf(info.getProjectid()));
        }


        IPage<FenhuoFaultdefendEntity> page = this.page(
                new Query<FenhuoFaultdefendEntity>().getPage(params),
                new QueryWrapper<FenhuoFaultdefendEntity>().eq("isdelete", 0)
                        .and(wrapper->wrapper.in("projectid", projectids))
        );

        return new PageUtils(page);
    }
    private String getREPXEPContent(String userID){
        String tempContent = "(^"+ userID +",)|(,"+userID+",)|(,"+userID+"$)|(^"+userID+"$)";

        return "\""+tempContent+"\"";
    }

}