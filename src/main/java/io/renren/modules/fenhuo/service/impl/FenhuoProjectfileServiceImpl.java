package io.renren.modules.fenhuo.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoProjectfileDao;
import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectfileService;


@Service("fenhuoProjectfileService")
public class FenhuoProjectfileServiceImpl extends ServiceImpl<FenhuoProjectfileDao, FenhuoProjectfileEntity> implements FenhuoProjectfileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String type = (String)params.get("type");

        QueryWrapper<FenhuoProjectfileEntity>  queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>();

        if(StringUtils.isNotBlank(type)){
            queryWrapper.and(wrapper->wrapper.ge("type",Long.parseLong(type)));

        }


        IPage<FenhuoProjectfileEntity> page = this.page(
                new Query<FenhuoProjectfileEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryFaultlistPage(Map<String, Object> params) {

        String type = (String)params.get("type");
        String faultid = (String)params.get("faultid");

        QueryWrapper<FenhuoProjectfileEntity>  queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>();

        if(StringUtils.isNotBlank(type)){
            queryWrapper.and(wrapper->wrapper.eq("type",Long.parseLong(type)));
        }
        if(StringUtils.isNotBlank(faultid)){
            queryWrapper.and(wrapper->wrapper.eq("projectid", Integer.valueOf(faultid)));
        }

        IPage<FenhuoProjectfileEntity> page = this.page(
                new Query<FenhuoProjectfileEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}