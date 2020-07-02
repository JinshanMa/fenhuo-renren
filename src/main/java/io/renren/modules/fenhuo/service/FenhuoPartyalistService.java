package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoPartyalistEntity;

import java.util.Map;

/**
 * 项目甲方负责人表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-26 16:32:02
 */
public interface FenhuoPartyalistService extends IService<FenhuoPartyalistEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

