package io.renren.modules.fenhuo.service.impl;

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
        IPage<FenhuoProjectfileEntity> page = this.page(
                new Query<FenhuoProjectfileEntity>().getPage(params),
                new QueryWrapper<FenhuoProjectfileEntity>()
        );

        return new PageUtils(page);
    }

}