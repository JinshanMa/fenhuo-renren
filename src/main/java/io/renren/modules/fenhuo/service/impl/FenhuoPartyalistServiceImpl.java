package io.renren.modules.fenhuo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoPartyalistDao;
import io.renren.modules.fenhuo.entity.FenhuoPartyalistEntity;
import io.renren.modules.fenhuo.service.FenhuoPartyalistService;


@Service("fenhuoPartyalistService")
public class FenhuoPartyalistServiceImpl extends ServiceImpl<FenhuoPartyalistDao, FenhuoPartyalistEntity> implements FenhuoPartyalistService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoPartyalistEntity> page = this.page(
                new Query<FenhuoPartyalistEntity>().getPage(params),
                new QueryWrapper<FenhuoPartyalistEntity>()
        );

        return new PageUtils(page);
    }

}