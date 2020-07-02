package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoUserSysRoleEntity;

import java.util.Map;

/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-12 21:40:25
 */
public interface FenhuoUserSysRoleService extends IService<FenhuoUserSysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

