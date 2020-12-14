package com.lx.pojo;

import com.lx.dao.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回数据
 */
@Data
public class RC {

    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "显示码")
    private Integer code;
    @ApiModelProperty(value = "显示信息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data=new HashMap<>();

    /**
     * 构造方法私有化
     */
    private RC(){}


    /**
     * 成功静态方法
     * @return
     */
    public static RC success(){
        RC rc = new RC();
        rc.setSuccess(true);
        rc.setCode(ResultCode.SUCCESS);
        rc.setMsg("成功");
        return rc;
    }

    /**
     * 失败静态方法
     * @return
     */
    public static RC err(){
        RC rc = new RC();
        rc.setSuccess(false);
        rc.setCode(ResultCode.DEFAULT);
        rc.setMsg("失败");
        return rc;
    }

    public RC success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public RC code(Integer code){
        this.setCode(code);
        return this;
    }

    public RC msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public RC data(String key,Object value){
       this.data.put(key, value);
       return this;
    }

    public RC data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
