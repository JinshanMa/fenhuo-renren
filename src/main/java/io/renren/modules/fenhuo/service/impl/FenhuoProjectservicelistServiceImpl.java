package io.renren.modules.fenhuo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoProjectservicelistDao;
import io.renren.modules.fenhuo.entity.FenhuoProjectservicelistEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectservicelistService;


@Service("fenhuoProjectservicelistService")
public class FenhuoProjectservicelistServiceImpl extends ServiceImpl<FenhuoProjectservicelistDao, FenhuoProjectservicelistEntity> implements FenhuoProjectservicelistService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoProjectservicelistEntity> page = this.page(
                new Query<FenhuoProjectservicelistEntity>().getPage(params),
                new QueryWrapper<FenhuoProjectservicelistEntity>()
        );

        return new PageUtils(page);
    }

}