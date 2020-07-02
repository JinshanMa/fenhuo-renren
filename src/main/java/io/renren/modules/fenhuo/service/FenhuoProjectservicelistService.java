package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoProjectservicelistEntity;

import java.util.Map;

/**
 * 项目维护人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:09
 */
public interface FenhuoProjectservicelistService extends IService<FenhuoProjectservicelistEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

