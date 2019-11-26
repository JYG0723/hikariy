package nuc.jyg.hikariy.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 16:49 2019-04-22.
 * @description 用户类(超管 ， 管理员 ， 用户)
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private String email;

    private Integer role;

    private String question;

    private String answer;

    @Column(name = "create_time", updatable = false, nullable = false)
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    private Date updateTime;
}
