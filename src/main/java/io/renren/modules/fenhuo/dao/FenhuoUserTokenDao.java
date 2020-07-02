package io.renren.modules.fenhuo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.fenhuo.entity.FenhuoUserTokenEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FenhuoUserTokenDao extends BaseMapper<FenhuoUserTokenEntity> {
    FenhuoUserTokenEntity queryByToken(String token);
}
