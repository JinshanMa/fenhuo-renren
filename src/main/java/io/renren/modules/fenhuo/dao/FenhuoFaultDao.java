package io.renren.modules.fenhuo.dao;

import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 故障申报表
 * 
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:42:15
 */
@Mapper
public interface FenhuoFaultDao extends BaseMapper<FenhuoFaultEntity> {
	
}
