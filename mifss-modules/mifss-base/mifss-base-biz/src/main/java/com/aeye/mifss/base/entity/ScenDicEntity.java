package com.aeye.mifss.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 场景字典代码表 实体类
 *
 * @author mifss
 */
@Data
@TableName("scen_dic_a")
public class ScenDicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据字典ID
     */
    @TableId(value = "DIC_ID", type = IdType.ASSIGN_UUID)
    private String dicId;

    /**
     * 字典值代码
     */
    @TableField("DIC_CODE")
    private String dicCode;

    /**
     * 字典值名称
     */
    @TableField("DIC_NAME")
    private String dicName;

    /**
     * 父级字典值代码
     */
    @TableField("PRNT_DIC_CODE")
    private String prntDicCode;

    /**
     * 字典类型代码
     */
    @TableField("DIC_TYPE_CODE")
    private String dicTypeCode;

    /**
     * 字典类型名称
     */
    @TableField("DIC_TYPE_NAME")
    private String dicTypeName;

    /**
     * 顺序号
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 有效标志
     */
    @TableField("VALI_FLAG")
    private String valiFlag;

    /**
     * 数据唯一记录号
     */
    @TableField("RID")
    private String rid;

    /**
     * 数据创建时间
     */
    @TableField("CRTE_TIME")
    private LocalDateTime crteTime;

    /**
     * 数据更新时间
     */
    @TableField("UPDT_TIME")
    private LocalDateTime updtTime;
}
