package com.ustc.project4.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;


//自动填充可参考
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Date addtime;

    private Integer comment;
    private Integer type;
    private Integer status;
    private Integer sex;

    private String qq;
    private String email;
    private String phone;
    private String activationCode;
    private String avatar;

    private Double gpa;
    private Integer sat;
    private Double ielts;
    private Double toefl;
}
