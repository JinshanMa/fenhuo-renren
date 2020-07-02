package io.renren.modules.fenhuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.fenhuo.entity.FenhuoUserTokenEntity;
import io.renren.modules.sys.entity.SysUserTokenEntity;

public interface FenhuoUserTokenService extends IService<FenhuoUserTokenEntity> {
    /**
     * 生成token
     * @param userId  用户ID
     */
    R createToken(long userId);

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    void logout(long userId);
}
