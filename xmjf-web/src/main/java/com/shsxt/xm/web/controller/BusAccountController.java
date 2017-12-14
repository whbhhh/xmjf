package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.exception.ParamsException;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.service.IBusAccountRechargeService;
import com.shsxt.xm.api.service.IBusAccountService;
import com.shsxt.xm.api.util.PageList;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping("account")
public class BusAccountController {
    @Resource
    private IBusAccountService busAccountService;
    @Resource
    private IBusAccountRechargeService busAccountRechargeService;

    /**
     * 转发到充值页面
     *
     * @param request
     * @return
     */
    @RequestMapping("rechargePage")
    public String toAccountRechargePage(HttpServletRequest request) {
        request.getSession().setAttribute("ctx", request.getContextPath());
        return "user/recharge";
    }

    /**
     * 发起支付请求页面
     *
     * @param
     * @param picCode
     * @param bussinessPassword
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("doAccountRechargeToRechargePage")
    public String doAccountRechargeToRechargePage(BigDecimal amount, String picCode, String bussinessPassword,
                                                  HttpServletRequest request, Model model) {
        model.addAttribute("ctx", request.getContextPath());
        String sessionPicCode = (String) request.getSession().getAttribute(P2pConstant.PICTURE_VERIFY_CODE);
        if (StringUtils.isBlank(sessionPicCode)) {
            System.out.println("验证码失效");
            return "user/pay";
        }
        if (!picCode.equals(sessionPicCode)) {
            System.out.println("验证码不匹配");
            return "user/pay";
        }
        BasUser basUser = (BasUser) request.getSession().getAttribute("userInfo");
        PayDto payDto = busAccountService.addRechargeRequestInfo(amount, bussinessPassword, basUser.getId());
        model.addAttribute("pay", payDto);
        return "user/pay";
    }

    @RequestMapping("callback")
    public String callback(@RequestParam(defaultValue = "0", name = "total_fee") BigDecimal totalFee,
                           @RequestParam(defaultValue = "1", name = "out_order_no") String outOrderNo, String sign
            , @RequestParam(defaultValue = "2017", name = "trade_no") String tradeNo,
                           @RequestParam(defaultValue = "success", name = "trade_status") String tradeStatus,
                           HttpSession session, RedirectAttributes redirectAttributes) {
        BasUser basUser = (BasUser)session.getAttribute("userInfo");

        try {
            busAccountService.updateAccountRecharge(basUser.getId(),totalFee,outOrderNo,sign,tradeNo,tradeStatus);
            redirectAttributes.addAttribute("result","success");
        } catch (ParamsException e) {
            e.printStackTrace();
            System.out.println(e.getErrorMsg());
            redirectAttributes.addAttribute("result","failed");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("系统异常!");
            redirectAttributes.addAttribute("result","failed");
        }
        return "redirect:/account/result";// 重定向到controller 指定方法
    }

    @RequestMapping("result")
    public String result(String result,Model model,HttpServletRequest request){
        model.addAttribute("ctx",request.getContextPath());
        model.addAttribute("result",result);
        return "user/result";
    }

    @RequestMapping("rechargeRecodePage")
    public  String rechargeRecodePage(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "user/recharge_record";
    }


    @RequestMapping("queryRechargeRecodesByUserId")
    @ResponseBody
    public PageList queryRechargeRecodesByUserId(HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("userInfo");
        AccountRechargeQuery accountRechargeQuery=new AccountRechargeQuery();
        accountRechargeQuery.setUserId(basUser.getId());
        return busAccountRechargeService.queryRechargeRecodesByParams(accountRechargeQuery);
    }
}
