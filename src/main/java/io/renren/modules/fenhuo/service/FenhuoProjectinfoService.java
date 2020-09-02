package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 项目信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-29 16:12:10
 */
public interface FenhuoProjectinfoService extends IService<FenhuoProjectinfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils querySelectedPage(Map<String, Object> params);

    boolean saveProjectInfo(FenhuoProjectinfoEntity projectinfo);

    boolean removeByIdsBySetIsDeleted(Collection<? extends Serializable> idList);

    boolean updateProjectInfo(FenhuoProjectinfoEntity projectinfo);

    boolean updateProjectInfoByIds(Collection<? extends Serializable> idList, int status);

//    boolean closeProjectInfoByIds(Collection<? extends Serializable> idList);

//    boolean failedProjectInfoByIds(Collection<? extends Serializable> idList);

    List<FenhuoProjectinfoEntity> getProjectinfoByHeadid(String exp);

    List<FenhuoProjectinfoEntity> getProjectinfoByApartid(String exp);

    List<FenhuoProjectinfoEntity> getProjectinfoByMaintainid(String exp);

    void relatedFileDownload(HttpServletRequest request, HttpServletResponse res);
}

