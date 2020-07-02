package io.renren.modules.fenhuo.obj;

import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoZabbixhostEntity;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

@Data
public class FenhuoProjectinfoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private FenhuoProjectinfoEntity projectinfo;

    private FenhuoZabbixhostEntity zabbixhost;

}