package nuc.jyg.hikariy.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 14:20 2019-04-26.
 * @description 股票
 */
@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 企业id
     */
    private Integer enterpriseId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 股票编号
     */
    private String stockNumber;

    /**
     * 拼音编码
     */
    private String shorthandNumber;

    /**
     * 当前价格
     */
    private BigDecimal currentPrice;

    /**
     * 开盘价
     */
    private BigDecimal openingPrice;

    /**
     * 前收盘价
     */
    private BigDecimal beforeClosingPrice;

    /**
     * 收盘价
     */
    private BigDecimal closingPrice;

    /**
     * 最高点
     */
    private BigDecimal highestPoint;

    /**
     * 最低点
     */
    private BigDecimal lowestPoint;

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time", nullable = false, updatable = false)
    @UpdateTimestamp
    private Date updateTime;
}
