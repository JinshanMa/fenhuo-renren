package io.renren.modules.fenhuo.dao;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 项目信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:10
 */
@Mapper
public interface FenhuoProjectinfoDao extends BaseMapper<FenhuoProjectinfoEntity> {

    List<FenhuoProjectinfoEntity> selectProjectinfoByHeaderid(String headid);

    List<FenhuoProjectinfoEntity> selectProjectinfoByApartid(String exp);

    List<FenhuoProjectinfoEntity> selectProjectinfoByMaintainid(String exp);
	
}
