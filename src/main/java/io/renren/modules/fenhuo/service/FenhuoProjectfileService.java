package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoProjectfileEntity;

import java.util.Map;

/**
 * 项目附件
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-08-31 16:19:46
 */
public interface FenhuoProjectfileService extends IService<FenhuoProjectfileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

