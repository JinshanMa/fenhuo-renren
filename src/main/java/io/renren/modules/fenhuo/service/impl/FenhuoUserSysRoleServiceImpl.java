package io.renren.modules.fenhuo.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoUserSysRoleDao;
import io.renren.modules.fenhuo.entity.FenhuoUserSysRoleEntity;
import io.renren.modules.fenhuo.service.FenhuoUserSysRoleService;


@Service("fenhuoUserSysRoleService")
public class FenhuoUserSysRoleServiceImpl extends ServiceImpl<FenhuoUserSysRoleDao, FenhuoUserSysRoleEntity> implements FenhuoUserSysRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoUserSysRoleEntity> page = this.page(
                new Query<FenhuoUserSysRoleEntity>().getPage(params),
                new QueryWrapper<FenhuoUserSysRoleEntity>()
        );

        return new PageUtils(page);
    }

}