package nuc.jyg.hikariy.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 19:51 2019-04-25.
 * @description
 */
@Data
@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 企业名
     */
    private String enterpriseName;

    /**
     * 外文名称
     */
    private String foreignName;

    /**
     * 公司简介
     */
    private String introduction;

    /**
     * 公司网站
     */
    private String site;

    /**
     * 总部地点
     */
    private String position;

    /**
     * 成立时间
     */
    private String established;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 公司类型
     */
    private String type;

    /**
     * 年营业额
     */
    private String annualTurnover;

    /**
     * ipo价格
     */
    private BigDecimal ipoPrice;

    /**
     * 员工数
     */
    private String employeeNumber;

    /**
     * 创始人
     */
    private String founder;

    /**
     * 首席执行官
     */
    private String chiefExecutive;

    /**
     * 公司使命
     */
    private String companyMission;

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    private Date updateTime;
}
