package io.renren.modules.fenhuo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoPushlogDao;
import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;
import io.renren.modules.fenhuo.service.FenhuoPushlogService;


@Service("fenhuoPushlogService")
public class FenhuoPushlogServiceImpl extends ServiceImpl<FenhuoPushlogDao, FenhuoPushlogEntity> implements FenhuoPushlogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoPushlogEntity> page = this.page(
                new Query<FenhuoPushlogEntity>().getPage(params),
                new QueryWrapper<FenhuoPushlogEntity>()
        );

        return new PageUtils(page);
    }

}