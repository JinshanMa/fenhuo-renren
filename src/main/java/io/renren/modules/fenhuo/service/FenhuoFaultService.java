package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoFaultEntity;
import io.renren.modules.fenhuo.entity.FenhuoFaultdefendEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    FenhuoFaultdefendEntity confirmeById(String faultid, FenhuoUsersEntity fenhuousers);

    void confirmToFaultdefend(FenhuoFaultdefendEntity faultDefend, Date beginDate, Date endate);

    void postvalidate(String faultid);

    void passvalid(String faultid);

    void validfail(String faultid);

    void noideamaintain(String faultid);

    void relatedFileDownload(HttpServletRequest request, HttpServletResponse res);

    void removeBySetisdeleted(String[] faultids);
}

