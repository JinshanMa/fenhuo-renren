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

        // 技术文件类型
        String type = (String)params.get("type");

        // 文件创建开始时间
        String startDate = (String)params.get("startDate");

        // 文件创建结束时间
        String endDate = (String)params.get("endDate");

        String techcatalogId = (String)params.get("techcatalogid");

        String filtypename = (String)params.get("filtypename");

        String creatorname = (String)params.get("creatorname");


        QueryWrapper<FenhuoProjectfileEntity>  queryWrapper = new QueryWrapper<FenhuoProjectfileEntity>();

        if(StringUtils.isNotBlank(type)){
            queryWrapper.and(wrapper->wrapper.ge("type",Long.parseLong(type)));
        }
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            queryWrapper.and(wrapper->wrapper.ge("date_format(createdatetime,'%Y-%m-%d')",startDate)
                    .le("date_format(createdatetime,'%Y-%m-%d')", endDate));
        }

        if(StringUtils.isNotBlank(techcatalogId)){
            queryWrapper.and(wrapper->wrapper.eq("techcatalogid", techcatalogId));
        }
        if(StringUtils.isNotBlank(filtypename)){
            queryWrapper.and(wrapper->wrapper.like("filetype", filtypename));
        }
        if(StringUtils.isNotBlank(creatorname)){
            queryWrapper.and(wrapper->wrapper.like("creator", creatorname));
        }

        queryWrapper.orderByDesc("fileid");

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