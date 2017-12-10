package com.shsxt.xm.api.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BasItem implements Serializable{
    private static final long serialVersionUID = 193200446469337230L;
    /**
     * id
     * 主键
     */
    private Integer id;

    /**
     * item_isalive
     * 项目的存活状态：1-正常发标 0-项目失败
     */
    private Byte itemIsalive;

    /**
     * item_isnew
     * 项目的新手标的标志，0-普通，1-新手标
     */
    private Byte itemIsnew;

    /**
     * item_isrecommend
     * 是否推荐：0-否 1-推荐表2-周年庆3-奥运
     */
    private Integer itemIsrecommend;

    /**
     * item_status
     * 状态 
     */
    private Integer itemStatus;

    /**
     * item_user_id
     * 
     */
    private Integer itemUserId;

    /**
     * item_name
     * 项目名称 
     */
    private String itemName;

    /**
     * item_second_name
     * 项目二级标题
     */
    private String itemSecondName;

    /**
     * item_number
     * 项目编号
     */
    private String itemNumber;

    /**
     * item_desc
     * 项目描述 
     */
    private String itemDesc;

    /**
     * item_rate
     * 项目利率 单位为%，100即100%
     */
    private BigDecimal itemRate;

    /**
     * item_add_rate
     * 
     */
    private BigDecimal itemAddRate;

    /**
     * item_serve_rate
     * 项目服务费率1就是1%，先锋只接收整数
     */
    private BigDecimal itemServeRate;

    /**
     * item_type
     * 项目类型 1.学车宝 2.车商宝 3.车贷宝 4.车易保
     */
    private Integer itemType;

    /**
     * item_cycle
     * 借款周期 
     */
    private Integer itemCycle;

    /**
     * item_cycle_unit
     * 周期单位 1-天、2-月、3-季、4-年
     */
    private Integer itemCycleUnit;

    /**
     * item_account
     * 借款金额 
     */
    private BigDecimal itemAccount;

    /**
     * item_ongoing_account
     * 进行中金额 
     */
    private BigDecimal itemOngoingAccount;

    /**
     * item_scale
     * 进度 单位为%，100即100%
     */
    private BigDecimal itemScale;

    /**
     * item_single_min_investment
     * 该标的单笔最低投标金额 
     */
    private BigDecimal itemSingleMinInvestment;

    /**
     * item_repay_method
     * 还款方式 1-一次性还款 2-等额本息 3-先息后本 4-每日付息
     */
    private Integer itemRepayMethod;

    /**
     * item_single_max_investment
     * 该标的单笔最高投标金额 
     */
    private BigDecimal itemSingleMaxInvestment;

    /**
     * item_max_investment
     * 该标的最大投标金额总额 
     */
    private BigDecimal itemMaxInvestment;

    /**
     * invest_times
     * 投标次数
     */
    private Integer investTimes;

    /**
     * addip
     * 新增IP，支持IPV6 
     */
    private String addip;

    /**
     * addtime
     * 新增时间 
     */
    private Date addtime;

    /**
     * release_time
     * 发布时间
     */
    private Date releaseTime;

    /**
     * end_time
     * 结束时间
     */
    private Date endTime;

    /**
     * update_time
     * 更新时间
     */
    private Date updateTime;

    /**
     * password
     * 定向密码
     */
    private String password;

    /**
     * guarantee_id
     * 担保id
     */
    private Integer guaranteeId;

    /**
     * contract
     * 合同
     */
    private String contract;

    /**
     * receipt_method
     * 收款方式(0. 借款人收款 1.分润担保公司 2.受托支付)
     */
    private Integer receiptMethod;

    /**
     * prepayment
     * 是否提前还款0-否1-是
     */
    private Integer prepayment;

    /**
     * move_vip
     * 是否移动专享0-否1-是
     */
    private Integer moveVip;

    /**
     * item_loan_use
     * 借款用途
     */
    private String itemLoanUse;

    /**
     * item_is_auto
     * 是否支持自动投标：0-否，1-是
     */
    private Integer itemIsAuto;

    /**
     * item_content
     * 项目内容 富文本
     */
    private String itemContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getItemIsalive() {
        return itemIsalive;
    }

    public void setItemIsalive(Byte itemIsalive) {
        this.itemIsalive = itemIsalive;
    }

    public Byte getItemIsnew() {
        return itemIsnew;
    }

    public void setItemIsnew(Byte itemIsnew) {
        this.itemIsnew = itemIsnew;
    }

    public Integer getItemIsrecommend() {
        return itemIsrecommend;
    }

    public void setItemIsrecommend(Integer itemIsrecommend) {
        this.itemIsrecommend = itemIsrecommend;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Integer getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(Integer itemUserId) {
        this.itemUserId = itemUserId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSecondName() {
        return itemSecondName;
    }

    public void setItemSecondName(String itemSecondName) {
        this.itemSecondName = itemSecondName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getItemRate() {
        return itemRate;
    }

    public void setItemRate(BigDecimal itemRate) {
        this.itemRate = itemRate;
    }

    public BigDecimal getItemAddRate() {
        return itemAddRate;
    }

    public void setItemAddRate(BigDecimal itemAddRate) {
        this.itemAddRate = itemAddRate;
    }

    public BigDecimal getItemServeRate() {
        return itemServeRate;
    }

    public void setItemServeRate(BigDecimal itemServeRate) {
        this.itemServeRate = itemServeRate;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getItemCycle() {
        return itemCycle;
    }

    public void setItemCycle(Integer itemCycle) {
        this.itemCycle = itemCycle;
    }

    public Integer getItemCycleUnit() {
        return itemCycleUnit;
    }

    public void setItemCycleUnit(Integer itemCycleUnit) {
        this.itemCycleUnit = itemCycleUnit;
    }

    public BigDecimal getItemAccount() {
        return itemAccount;
    }

    public void setItemAccount(BigDecimal itemAccount) {
        this.itemAccount = itemAccount;
    }

    public BigDecimal getItemOngoingAccount() {
        return itemOngoingAccount;
    }

    public void setItemOngoingAccount(BigDecimal itemOngoingAccount) {
        this.itemOngoingAccount = itemOngoingAccount;
    }

    public BigDecimal getItemScale() {
        return itemScale;
    }

    public void setItemScale(BigDecimal itemScale) {
        this.itemScale = itemScale;
    }

    public BigDecimal getItemSingleMinInvestment() {
        return itemSingleMinInvestment;
    }

    public void setItemSingleMinInvestment(BigDecimal itemSingleMinInvestment) {
        this.itemSingleMinInvestment = itemSingleMinInvestment;
    }

    public Integer getItemRepayMethod() {
        return itemRepayMethod;
    }

    public void setItemRepayMethod(Integer itemRepayMethod) {
        this.itemRepayMethod = itemRepayMethod;
    }

    public BigDecimal getItemSingleMaxInvestment() {
        return itemSingleMaxInvestment;
    }

    public void setItemSingleMaxInvestment(BigDecimal itemSingleMaxInvestment) {
        this.itemSingleMaxInvestment = itemSingleMaxInvestment;
    }

    public BigDecimal getItemMaxInvestment() {
        return itemMaxInvestment;
    }

    public void setItemMaxInvestment(BigDecimal itemMaxInvestment) {
        this.itemMaxInvestment = itemMaxInvestment;
    }

    public Integer getInvestTimes() {
        return investTimes;
    }

    public void setInvestTimes(Integer investTimes) {
        this.investTimes = investTimes;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(Integer guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Integer getReceiptMethod() {
        return receiptMethod;
    }

    public void setReceiptMethod(Integer receiptMethod) {
        this.receiptMethod = receiptMethod;
    }

    public Integer getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(Integer prepayment) {
        this.prepayment = prepayment;
    }

    public Integer getMoveVip() {
        return moveVip;
    }

    public void setMoveVip(Integer moveVip) {
        this.moveVip = moveVip;
    }

    public String getItemLoanUse() {
        return itemLoanUse;
    }

    public void setItemLoanUse(String itemLoanUse) {
        this.itemLoanUse = itemLoanUse;
    }

    public Integer getItemIsAuto() {
        return itemIsAuto;
    }

    public void setItemIsAuto(Integer itemIsAuto) {
        this.itemIsAuto = itemIsAuto;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    @Override
    public String toString() {
        return "BasItem{" +
                "id=" + id +
                ", itemIsalive=" + itemIsalive +
                ", itemIsnew=" + itemIsnew +
                ", itemIsrecommend=" + itemIsrecommend +
                ", itemStatus=" + itemStatus +
                ", itemUserId=" + itemUserId +
                ", itemName='" + itemName + '\'' +
                ", itemSecondName='" + itemSecondName + '\'' +
                ", itemNumber='" + itemNumber + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemRate=" + itemRate +
                ", itemAddRate=" + itemAddRate +
                ", itemServeRate=" + itemServeRate +
                ", itemType=" + itemType +
                ", itemCycle=" + itemCycle +
                ", itemCycleUnit=" + itemCycleUnit +
                ", itemAccount=" + itemAccount +
                ", itemOngoingAccount=" + itemOngoingAccount +
                ", itemScale=" + itemScale +
                ", itemSingleMinInvestment=" + itemSingleMinInvestment +
                ", itemRepayMethod=" + itemRepayMethod +
                ", itemSingleMaxInvestment=" + itemSingleMaxInvestment +
                ", itemMaxInvestment=" + itemMaxInvestment +
                ", investTimes=" + investTimes +
                ", addip='" + addip + '\'' +
                ", addtime=" + addtime +
                ", releaseTime=" + releaseTime +
                ", endTime=" + endTime +
                ", updateTime=" + updateTime +
                ", password='" + password + '\'' +
                ", guaranteeId=" + guaranteeId +
                ", contract='" + contract + '\'' +
                ", receiptMethod=" + receiptMethod +
                ", prepayment=" + prepayment +
                ", moveVip=" + moveVip +
                ", itemLoanUse='" + itemLoanUse + '\'' +
                ", itemIsAuto=" + itemIsAuto +
                ", itemContent='" + itemContent + '\'' +
                '}';
    }
}