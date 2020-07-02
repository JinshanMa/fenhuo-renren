package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoRoleEntity;

import java.util.Map;

/**
 * 角色表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-04-30 09:15:45
 */
public interface FenhuoRoleService extends IService<FenhuoRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

