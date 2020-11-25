package io.renren.modules.fenhuo.service.impl;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoPushlogDao;
import io.renren.modules.fenhuo.entity.FenhuoPushlogEntity;
import io.renren.modules.fenhuo.service.FenhuoPushlogService;


@Service("fenhuoPushlogService")
public class FenhuoPushlogServiceImpl extends ServiceImpl<FenhuoPushlogDao, FenhuoPushlogEntity> implements FenhuoPushlogService {

    @Autowired
    FenhuoUsersService usersService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {


        String userid = (String)params.get("userid");

        QueryWrapper<FenhuoPushlogEntity> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(userid) && !userid.equals("0")){
            queryWrapper.and(wrapper->wrapper.eq("userid", userid));
        }
        queryWrapper.orderByDesc("pushtime");

        IPage<FenhuoPushlogEntity> page = this.page(
                new Query<FenhuoPushlogEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}