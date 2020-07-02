package io.renren.modules.fenhuo.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 项目主机表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-27 11:51:03
 */
public interface FenhuoZabbixhostService extends IService<FenhuoZabbixhostEntity> {



    PageUtils queryPage(Map<String, Object> params);


    boolean removeByIdsBySetIsDeleted(Collection<? extends Serializable> idList);


    boolean removeByIdsBySetIsDeleted(Long id);


    JSONObject authrizedTestAndGetHosts(Map<String, Object> params);
}

