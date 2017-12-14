package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.exception.ParamsException;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.service.*;
import com.shsxt.xm.api.util.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("basItem")
public class BasItemController {
    @Resource
    private IBasItemService basItemService;
    @Resource
    private IBusAccountService busAccountService;
    @Resource
    private IBasUserSecurityService basUserSecurityService;
    @Resource
    private IBusItemLoanService busItemLoanService;
    @Resource
    private ISysPictureService sysPictureService;

    /**
     * 投资列表页
     * @param request
     * @return
     */
    @RequestMapping("list")
    public String toBasItemListPage(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "item/invest_list";
    }

    @RequestMapping("queryBasItemsByParams")
    @ResponseBody
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery){
        return basItemService.queryBasItemsByParams(basItemQuery);
    }

    @RequestMapping("updateBasItemStatusToOpen")
    @ResponseBody
    public ResultInfo updateBasItemStatusToOpen(Integer itemId){
        ResultInfo resultInfo = new ResultInfo();
        try {
            basItemService.updateBasItemStatusToOpen(itemId);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(P2pConstant.OPS_FAILED_MSG);
        }
        return resultInfo;
    }

    @RequestMapping("itemDetailPage")
    public String itemDetailPage(Integer itemId, ModelMap modelMap,HttpServletRequest request){
        BasItemDto basItemDto = basItemService.queryBasItemByItemId(itemId);
        BasUser basUser = (BasUser)request.getSession().getAttribute("userInfo");
        if (null != basUser){
            BusAccount busAccount = busAccountService.queryBasAccountByUserId(basUser.getId());
            modelMap.addAttribute("busAccount",busAccount);
        }
        BasUserSecurity basUserSecurity = basUserSecurityService.queryBasUserSecurityByUserId(basItemDto.getItemUserId());
        BusItemLoan busItemLoan = busItemLoanService.queryBusItemLoanByItemId(itemId);
        List<SysPicture> sysPictures = sysPictureService.querySysPicturesByItemId(itemId);
        modelMap.addAttribute("loanUser",basUserSecurity);
        modelMap.addAttribute("busItemLoan",busItemLoan);
        modelMap.addAttribute("ctx",request.getContextPath());
        modelMap.addAttribute("item",basItemDto);
        modelMap.addAttribute("pics",sysPictures);
        return "item/details";
    }
}
