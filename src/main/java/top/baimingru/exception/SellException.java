package top.baimingru.exception;

import lombok.Getter;
import top.baimingru.enums.ResultEnum;

/**
 * @author bmr
 * @time 2019-01-14 9:46
 */
@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
