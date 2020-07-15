package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 *
 * @author majinshan
 * @email sunlightcs@gmail.com
 * @date 2020-05-06 09:50:33
 */
public interface FenhuoUsersService extends IService<FenhuoUsersEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveFenhuoUser(FenhuoUsersEntity fenhuoUser);

    FenhuoUsersEntity sysOverQueryByUserName(String fenghuoUserName);


    boolean isDeleteByIds(Collection<? extends Serializable> idList);

    boolean batchAddUserByExcel(MultipartFile file, String userType) throws Exception;

    void patternFileDownload(HttpServletRequest request, HttpServletResponse res);


    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    boolean updatePassword(Long userId, String password, String newPassword);

    FenhuoUsersEntity queryByMobile(String mobile);

    /**
     * 用户登录
     * @param form    登录表单
     * @return        返回用户ID
     */
    FenhuoUsersEntity login(LoginForm form);
}

