package io.renren.modules.app.controller;


import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import io.renren.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


@RestController
@RequestMapping("app/code")
public class AppYunPianController {

    @RequestMapping("/get")
    public R getCode(HttpServletRequest request, @RequestParam Map<String, Object> params){

        String mobile = String.valueOf(params.get("mobile"));

        if (mobile.equals("") || mobile.length()==0){
            return R.error(500,"电话号码不能为空");
        }
        //六位随机码
        String code = randomCode();

        YunpianClient clnt = new YunpianClient("2869591b63db6b66495e416eb13a105f").init();

        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, mobile);
        param.put(YunpianClient.TEXT, "【江民信息】您的验证码是" + code  + "。如非本人操作，请忽略本短信");
        //param.put(YunpianClient.TPL_ID,"3111580");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);

        clnt.close();
        if (r.getCode() == 0){

            final HttpSession httpSession = request.getSession();
            httpSession.setAttribute(mobile,code);

            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("删除session中的验证码" + code);
                    httpSession.removeAttribute(mobile);
                }
            },1 * 60 * 1000);

            return R.ok();
        }else{
            return R.error(500,r.getMsg() + "," + r.getDetail());
        }

    }

    private String randomCode(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++)
        {
            result+=random.nextInt(10);
        }
        return result;
    }


}
