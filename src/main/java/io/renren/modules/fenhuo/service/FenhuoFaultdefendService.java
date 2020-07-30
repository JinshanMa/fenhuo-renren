package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;

import java.util.List;
import java.util.Map;

/**
 * 故障维护单
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-29 23:41:48
 */
public interface FenhuoFaultdefendService extends IService<FenhuoFaultdefendEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void removeByIdsBySetIsDeleted(List<Integer> asList);
}

