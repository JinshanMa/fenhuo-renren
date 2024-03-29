package io.renren.modules.fenhuo.service.impl;

import io.renren.common.exception.RRException;
import io.renren.common.validator.Assert;
import io.renren.config.FilePatternConfig;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUserSysRoleEntity;
import io.renren.modules.fenhuo.service.FenhuoProjectinfoService;
import io.renren.modules.fenhuo.service.FenhuoUserSysRoleService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.fenhuo.dao.FenhuoUsersDao;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("fenhuoUsersService")
public class FenhuoUsersServiceImpl extends ServiceImpl<FenhuoUsersDao, FenhuoUsersEntity> implements FenhuoUsersService {

    @Autowired
    private FenhuoUserSysRoleService fenhuoUserSysRoleService;

    @Autowired
    private FenhuoProjectinfoService fenhuoProjectinfoService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private FilePatternConfig filePatternConfig;

    @Override
    public FenhuoUsersEntity queryByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<FenhuoUsersEntity>().eq("mobile", mobile));
    }

    public FenhuoUsersEntity queryByUserId(String userId) {
        return baseMapper.selectOne(new QueryWrapper<FenhuoUsersEntity>().eq("userid", userId));
    }

    @Override
    public FenhuoUsersEntity login(LoginForm form) {
        FenhuoUsersEntity user = queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");

        //密码错误
        String pwd = new Sha256Hash(form.getPassword(), user.getSalt()).toHex();
        if (!user.getPassword().equals(pwd)){
            throw new RRException("手机号或密码错误");
        }

        user.setPushid(form.getPushid());


        baseMapper.updateById(user);

//        if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
//            throw new RRException("手机号或密码错误");
//        }

        return user;
    }

    @Override
    public List<FenhuoUsersEntity> queryProjectUserinfo(String projectid, Map<String, Object> params) {

        FenhuoProjectinfoEntity projectinfo = fenhuoProjectinfoService.getById(Long.valueOf(projectid));
        String queryRoleid = (String)params.get("roleid");
        if(StringUtils.isNotBlank(queryRoleid)){
            if(queryRoleid.equals("1")){
                // 甲方负责人
                String amngers =  projectinfo.getPartyaid();
                String[] amnagerArray = amngers.split(",");
                QueryWrapper<FenhuoUsersEntity> queryWrapper
                        = new QueryWrapper<FenhuoUsersEntity>().in("userid", amnagerArray);
                List<FenhuoUsersEntity> partyaLists = fenhuoUsersService.list(queryWrapper);
                return partyaLists;
            } else if(queryRoleid.equals("2")){
                // 项目负责人
                String mngers =  projectinfo.getHeadid();
                String[] mnagerArray = mngers.split(",");
                QueryWrapper<FenhuoUsersEntity> queryWrapper
                        = new QueryWrapper<FenhuoUsersEntity>().in("userid", mnagerArray);
                List<FenhuoUsersEntity> mngersLists = fenhuoUsersService.list(queryWrapper);
                return mngersLists;
            } else if(queryRoleid.equals("3")){
                // 项目维护人员
                String servicemids =  projectinfo.getServicemid();
                String[] servicemidsArray = servicemids.split(",");
                QueryWrapper<FenhuoUsersEntity> queryWrapper
                        = new QueryWrapper<FenhuoUsersEntity>().in("userid", servicemidsArray);
                List<FenhuoUsersEntity> servicemidLists = fenhuoUsersService.list(queryWrapper);
                return servicemidLists;
            }
        }
        return null;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String startDate = (String)params.get("startDate");

        String endDate = (String)params.get("endDate");

        String username = (String)params.get("username");

        String realname = (String)params.get("realname");

        String roleid = (String)params.get("roleid");
        
        String statu = (String)params.get("statu");

        String keyword = (String)params.get("keyword");

        String address = (String)params.get("address");

        QueryWrapper<FenhuoUsersEntity>  queryWrapper = new QueryWrapper<FenhuoUsersEntity>();
        QueryWrapper<FenhuoUsersEntity> queryChild = (QueryWrapper<FenhuoUsersEntity>)queryWrapper.eq("isdelete", 0);

        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
//            queryChild.apply("date_format(create_time,'%Y-%m-%d') >= {0}", startDate)
//                    .apply("date_format(create_time,'%Y-%m-%d') <= {0}", endDate);
            queryChild.and(wrapper->wrapper.ge("date_format(create_time,'%Y-%m-%d')",startDate)
                    .le("date_format(create_time,'%Y-%m-%d')", endDate));
        }
        if(StringUtils.isNotBlank(username)){
            queryChild.and(wrapper->wrapper.like("loginname", username));
        }
        if(StringUtils.isNotBlank(realname)){
            queryChild.and(wrapper->wrapper.like("realname", realname));
        }
        if(StringUtils.isNotBlank(roleid)){
            queryChild.and(wrapper->wrapper.like("roleid", roleid));
        }
        if (StringUtils.isNotBlank(statu)){
            queryChild.and(wrapper->wrapper.eq("status",statu));
        }

        if (StringUtils.isNotBlank(keyword)){
            queryChild.and(wrapper->wrapper.like("mobile",keyword));
            queryChild.or(wrapper->wrapper.like("realname",keyword));
        }

        if(StringUtils.isNotBlank(address)){
            queryChild.and(wrapper->wrapper.like("address", address));
        }

        IPage<FenhuoUsersEntity> page = this.page(
                new Query<FenhuoUsersEntity>().getPage(params),
                queryWrapper
//                new QueryWrapper<FenhuoUsersEntity>()
//                        .eq("isdelete", 0)
//                        .apply("date_format(create_time,'%Y-%m-%d') >= {0}", "2019-02-14")
        );

        return new PageUtils(page);
    }

    @Override
    public void saveFenhuoUser(FenhuoUsersEntity fenhuoUser) {
        fenhuoUser.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);

        fenhuoUser.setPassword(new Sha256Hash(fenhuoUser.getPassword(), salt).toHex());
        fenhuoUser.setSalt(salt);
        if (fenhuoUser.getStatus() == null){
            fenhuoUser.setStatus("1");
        }else{
            if (fenhuoUser.getStatus().length() == 0 || fenhuoUser.getStatus().equals("")  || fenhuoUser.getStatus().equals("1")){
                fenhuoUser.setStatus("1");
            }else{
                fenhuoUser.setStatus("0");
            }
        }


        this.save(fenhuoUser);
        String roleId = fenhuoUser.getRoleid();
        if(roleId.equals("2")){
            // 项目负责人
            FenhuoUserSysRoleEntity fenhuoUserSysRole = new FenhuoUserSysRoleEntity();
            fenhuoUserSysRole.setUserId(fenhuoUser.getUserid());
            fenhuoUserSysRole.setRoleId(Long.valueOf("7"));
            fenhuoUserSysRoleService.save(fenhuoUserSysRole);
        }else if(roleId.equals("1")){
            // 甲方负责人
            FenhuoUserSysRoleEntity fenhuoUserSysRole = new FenhuoUserSysRoleEntity();
            fenhuoUserSysRole.setUserId(fenhuoUser.getUserid());
            fenhuoUserSysRole.setRoleId(Long.valueOf("8"));
            fenhuoUserSysRoleService.save(fenhuoUserSysRole);
        }else if(roleId.equals("3")){
            // 甲方负责人
            FenhuoUserSysRoleEntity fenhuoUserSysRole = new FenhuoUserSysRoleEntity();
            fenhuoUserSysRole.setUserId(fenhuoUser.getUserid());
            fenhuoUserSysRole.setRoleId(Long.valueOf("9"));
            fenhuoUserSysRoleService.save(fenhuoUserSysRole);
        }

    }

    // 系统调用烽火账户进行处理
    @Override
    public FenhuoUsersEntity sysOverQueryByUserName(String fenghuoUserName) {
        return baseMapper.sysOverQueryByUserName(fenghuoUserName);
    }

    @Override
    public boolean isDeleteByIds(Collection<? extends Serializable> idList) {
        Iterator iters = idList.iterator();
        while (iters.hasNext()) {
            Long detetingId = Long.parseLong((String) iters.next());
            FenhuoUsersEntity fenhuoUser = getById(detetingId);
            fenhuoUsersService.removeById(detetingId);
//            fenhuoUser.setIsdelete(1);
//            updateById(fenhuoUser);
        }
        return true;
    }

    @Override
    public boolean batchAddUserByExcel(MultipartFile file, String userType) throws Exception{

        //1.得到上传的表
        Workbook workbook2 = WorkbookFactory.create(file.getInputStream());
        //2.获取test工作表 注意test就是excel下面的sheet名称
        Sheet sheet2 = workbook2.getSheet("Sheet1");
        //3.获取表的总行数
        int num = sheet2.getLastRowNum();
        System.out.println("lastRownNum:"+num);
//        //4.获取表总列数
//        int col = sheet2.getRow(0).getLastCellNum();
        int col = 0;

        //5.遍历excel每一行
        for (int j = 1; j <= num; j++) {
//            Row row = sheet2.getRow(j);
//            col = sheet2.getRow(j).getLastCellNum();
//            System.out.println("row "+j+" ,col num:"+col);
//            for(int i = 0;i <  col;i++) {
//                Cell cell = row.getCell(i);
//                cell.setCellType(CellType.STRING);
//                System.out.println("row:"+ j +", col value:"+cell.getStringCellValue());
//            }
//            continue;
            Row row1 = sheet2.getRow(j);
            // 如果单元格中有数字或者其他格式的数据，则调用setCellType()转换为string类型
            Cell excelId = row1.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            excelId.setCellType(CellType.STRING);
            //获取表中第i行，第2列的单元格
            Cell userName = row1.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            userName.setCellType(CellType.STRING);
            //获取excel表的第i行，第3列的单元格
            Cell password = row1.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            password.setCellType(CellType.STRING);

            Cell roleName = row1.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            roleName.setCellType(CellType.STRING);

            Cell realName = row1.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            realName.setCellType(CellType.STRING);
            Cell sex = row1.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            sex.setCellType(CellType.STRING);

            Cell orgnazationName = row1.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            orgnazationName.setCellType(CellType.STRING);

            Cell companyName = row1.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            companyName.setCellType(CellType.STRING);

            Cell contact = row1.getCell(8,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            contact.setCellType(CellType.STRING);

            Cell contactTel = row1.getCell(9,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            contactTel.setCellType(CellType.STRING);

            Cell university = row1.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            university.setCellType(CellType.STRING);

            Cell skill = row1.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            skill.setCellType(CellType.STRING);

            Cell provice = row1.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            provice.setCellType(CellType.STRING);

            Cell city = row1.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            city.setCellType(CellType.STRING);

            Cell area = row1.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            area.setCellType(CellType.STRING);

//            System.out.println(cell1.getStringCellValue()+","+cell2.getStringCellValue()+","+cell3.getStringCellValue()+","+cell15.getStringCellValue());

            FenhuoUsersEntity user = new FenhuoUsersEntity();
            user.setRoleid(userType);
            user.setMobile(contactTel.getStringCellValue());
            user.setLoginname(userName.getStringCellValue());

            //sha256加密
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            user.setPassword(new Sha256Hash(password.getStringCellValue(), salt).toHex());

            user.setOrgname(orgnazationName.getStringCellValue());
            String sexStr = (sex.getStringCellValue() == "男" ? "man":"women");
            user.setSex(sexStr);

            user.setRealname(realName.getStringCellValue());
            switch (userType) {
                case "1":
                    user.setRolename("甲方负责人");
                    break;
                case "2":
                    user.setRolename("项目负责人");
                    break;
                case "3":
                    user.setRolename("维护工程师");
                    break;
                case "5":
                    user.setRolename("注册工程师");
                    break;
                case "6":
                    user.setRolename("注册用户");
                    break;

            }

            user.setCreateTime(new Date());
            user.setIsdelete(0);
            user.setCompanyname(companyName.getStringCellValue());
            user.setContacts(contact.getStringCellValue());
            user.setContactstel(contactTel.getStringCellValue());
            user.setUniversity(university.getStringCellValue());
            user.setSkill(skill.getStringCellValue());
            user.setProvice(provice.getStringCellValue());
            user.setCity(city.getStringCellValue());
            user.setArea(area.getStringCellValue());
            user.setStatus("1");
            save(user);
        }

        return false;
    }

    @Override
    public void patternFileDownload(HttpServletRequest request, HttpServletResponse res) {
        ApplicationHome applicationHome = new ApplicationHome(getClass());
        File jarFile = applicationHome.getSource();
        String path = jarFile.getParentFile().toString();
//        path += "/pattern/excel/";
        path += "/"+filePatternConfig.getUserbatchupdate()+"/";

        String fileName = request.getParameter("fileName");
        String filePath = path + fileName;
        File excelFile = new File(filePath);
        res.setCharacterEncoding("UTF-8");
        res.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        res.setContentType("application/octet-stream;charset=UTF-8");
        //加上设置大小下载下来的.xlsx文件打开时才不会报“Excel 已完成文件级验证和修复。此工作簿的某些部分可能已被修复或丢弃”
        res.addHeader("Content-Length", String.valueOf(excelFile.length()));
        try {
            res.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName.trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
//                    log.error("【下载模板】{}",e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {

        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        FenhuoUsersEntity userEntity = new FenhuoUsersEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<FenhuoUsersEntity>().eq("userid", userId).eq("password", password));
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) {
        FenhuoUsersEntity userEntity = new FenhuoUsersEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<FenhuoUsersEntity>().eq("userid", userId));
    }

}