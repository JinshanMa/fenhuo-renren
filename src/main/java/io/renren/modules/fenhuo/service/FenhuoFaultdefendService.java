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

    PageUtils queryWithStatu(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);

    /*
    * 获取所有待确认维护单
    * */
    List<FenhuoFaultdefendEntity> queryPageWithStatu2();

    void removeByIdsBySetIsDeleted(List<Integer> asList);
}

