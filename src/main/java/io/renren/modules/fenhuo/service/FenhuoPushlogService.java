package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;

import java.util.Map;

/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-06-22 11:06:23
 */
public interface FenhuoPushlogService extends IService<FenhuoPushlogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

