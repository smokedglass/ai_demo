package com.ten.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Operator")
@Getter
@Setter
@NoArgsConstructor
public class Operator implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 数据库主键
     */
    @Id
    private Long userId;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

}
