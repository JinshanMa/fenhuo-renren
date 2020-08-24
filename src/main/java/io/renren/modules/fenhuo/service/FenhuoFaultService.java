package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;

import java.util.Map;

/**
 * 故障申报表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:42:15
 */
public interface FenhuoFaultService extends IService<FenhuoFaultEntity> {

    void saveFault(FenhuoFaultEntity fenhuoFaultEntity);
    boolean savefenhuofault(FenhuoFaultEntity fenhuoFaultEntity);
    PageUtils queryPage(Map<String, Object> params);
}

