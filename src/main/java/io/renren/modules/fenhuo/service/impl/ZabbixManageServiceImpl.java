package io.renren.modules.fenhuo.service.impl;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.ZabbixManageDao;
import io.renren.modules.fenhuo.entity.ZabbixManageEntity;
import io.renren.modules.fenhuo.service.ZabbixManageService;


@Service("zabbixManageService")
public class ZabbixManageServiceImpl extends ServiceImpl<ZabbixManageDao, ZabbixManageEntity> implements ZabbixManageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String zabbixname = (String)params.get("zabbixname");


        QueryWrapper<ZabbixManageEntity> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(zabbixname)){
            queryWrapper.like("zabbix_name",  zabbixname);
        }

        IPage<ZabbixManageEntity> page = this.page(
                new Query<ZabbixManageEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}