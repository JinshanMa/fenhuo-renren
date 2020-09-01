package io.renren.modules.fenhuo.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.fenhuo.utils.ZabbixApiUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoZabbixhostDao;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import io.renren.modules.fenhuo.service.FenhuoZabbixhostService;


@Service("fenhuoZabbixhostService")
public class FenhuoZabbixhostServiceImpl extends ServiceImpl<FenhuoZabbixhostDao, FenhuoZabbixhostEntity> implements FenhuoZabbixhostService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FenhuoZabbixhostEntity> page = this.page(
                new Query<FenhuoZabbixhostEntity>().getPage(params),
                new QueryWrapper<FenhuoZabbixhostEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public boolean removeByIdsBySetIsDeleted(Collection<? extends Serializable> idList) {
        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            removeByIdsBySetIsDeleted(detetingId);
        }
        return true;
    }

    @Override
    public boolean removeByIdsBySetIsDeleted(Long id) {
        QueryWrapper<FenhuoZabbixhostEntity> zabbixhostQueryWrapper
                = new QueryWrapper<FenhuoZabbixhostEntity>()
                .eq("projectid", id);
        List<FenhuoZabbixhostEntity> zbxHosts = list(zabbixhostQueryWrapper);
        for(FenhuoZabbixhostEntity zbxhost : zbxHosts){
            zbxhost.setIsdeleted(1);
            updateById(zbxhost);
        }
        return true;
    }

    @Override
    public JSONObject authrizedTestAndGetHosts(Map<String, Object> params) {
        String username = (String)params.get("zbxUsername");
        String password = (String)params.get("zbxPassword");

        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            ZabbixApiUtils zabbixApiUtils = new ZabbixApiUtils();
            if(zabbixApiUtils.zabbixLogin(username,password)){
                JSONObject jsonData =  zabbixApiUtils.getDataBySingleParam("host.get", "filter",null);
                return jsonData;
            }else{
                return null;
            }
        }else {
            return null;
        }
    }

}