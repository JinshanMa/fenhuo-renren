package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoOperationlogEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 16:03:28
 */
public interface FenhuoOperationlogService extends IService<FenhuoOperationlogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAppOpByParamThroughAspect(Long userid, Object[] paramsObj, String opname);

    void saveOpByParamThroughAspect(Long userid, Object[] params, String opname);

    boolean isDeleteByIds(Collection<? extends Serializable> idList);

}

