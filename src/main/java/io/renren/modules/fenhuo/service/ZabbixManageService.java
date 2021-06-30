package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.ZabbixManageEntity;

import java.util.Map;

/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2021-06-24 13:54:00
 */
public interface ZabbixManageService extends IService<ZabbixManageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

