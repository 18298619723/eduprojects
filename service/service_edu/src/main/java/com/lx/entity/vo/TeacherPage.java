package com.lx.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 把需要分页查询的条件封装成对象
 */
@Data
public class TeacherPage {

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "创建时间")
    private String start;

    @ApiModelProperty(value = "结束时间")
    private String over;
}
