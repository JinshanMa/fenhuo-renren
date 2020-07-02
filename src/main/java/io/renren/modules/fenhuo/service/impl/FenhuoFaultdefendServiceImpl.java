package io.renren.modules.fenhuo.service.impl;

import org.springframework.stereotype.Service;
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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoFaultdefendEntity> page = this.page(
                new Query<FenhuoFaultdefendEntity>().getPage(params),
                new QueryWrapper<FenhuoFaultdefendEntity>()
        );

        return new PageUtils(page);
    }

}