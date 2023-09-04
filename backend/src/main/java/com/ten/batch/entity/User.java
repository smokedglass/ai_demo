package com.ten.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 名称：User
 * 功能描述：
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 13:44
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 数据库主键
     */
    @Id
    private String id;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
