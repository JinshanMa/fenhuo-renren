package io.renren.modules.fenhuo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoRoleDao;
import io.renren.modules.fenhuo.entity.FenhuoRoleEntity;
import io.renren.modules.fenhuo.service.FenhuoRoleService;


@Service("fenhuoRoleService")
public class FenhuoRoleServiceImpl extends ServiceImpl<FenhuoRoleDao, FenhuoRoleEntity> implements FenhuoRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoRoleEntity> page = this.page(
                new Query<FenhuoRoleEntity>().getPage(params),
                new QueryWrapper<FenhuoRoleEntity>()
        );

        return new PageUtils(page);
    }

}