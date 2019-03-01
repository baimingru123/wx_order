package top.baimingru.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.baimingru.VO.ResultVO;
import top.baimingru.config.ProjectUrlConfig;
import top.baimingru.exception.SellException;
import top.baimingru.exception.SellerAuthorizeException;
import top.baimingru.utils.ResultVOUtil;

/**
 * @author bmr
 * @time 2019-01-21 17:17
 */
@ControllerAdvice
public class SellExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        return new ModelAndView("redirect:".
                concat(projectUrlConfig.getWechatOpenAuthorize()).
                concat("/wechat/qrAuthorize").
                concat("?returnUrl=").
                concat(projectUrlConfig.getSystemUrl()).
                concat("/seller/user/login"));
    }

    //捕获
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
}
