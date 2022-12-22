package ru.aora.erp.model.entity.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Contract {

    private String id;
    private String oldId;
    private String counteragentId;
    private int contractType;
    private LocalDate contractDate;
    private String contractNumber;
    private String contractSubject;
    private BigDecimal contractSum;
    private BigDecimal contractValue;
    private BigDecimal ksValue;
    private LocalDateTime deactivationDate;
    private Integer activeStatus;

    public String getId() {
        return id;
    }

    public Contract setId(String id) {
        this.id = id;
        return this;
    }
    public String getOldId() {
        return oldId;
    }

    public Contract setOldId(String oldId) {
        this.oldId = oldId;
        return this;
    }

    public String getCounteragentId() {
        return counteragentId;
    }

    public Contract setCounteragentId(String counteragentId) {
        this.counteragentId = counteragentId;
        return this;
    }

    public int getContractType() {
        return contractType;
    }

    public Contract setContractType(int contractType) {
        this.contractType = contractType;
        return this;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public Contract setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
        return this;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public Contract setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public String getContractSubject() {
        return contractSubject;
    }

    public Contract setContractSubject(String contractSubject) {
        this.contractSubject = contractSubject;
        return this;
    }

    public BigDecimal getContractSum() {
        return contractSum;
    }

    public Contract setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
        return this;
    }
    public BigDecimal getContractValue() {
        return contractValue;
    }

    public Contract setContractValue(BigDecimal contractValue) {
        this.contractValue = contractValue;
        return this;
    }
    public BigDecimal getKSValue() {
        return ksValue;
    }

    public Contract setKSValue(BigDecimal ksValue) {
        this.ksValue = ksValue;
        return this;
    }
    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public Contract setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public Contract setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", oldId='" + oldId + '\'' +
                ", counteragentId='" + counteragentId + '\'' +
                ", contractType=" + contractType +
                ", contractDate=" + contractDate +
                ", contractNumber='" + contractNumber + '\'' +
                ", contractSubject='" + contractSubject + '\'' +
                ", contractSum'" + contractSum + '\'' +
                ", contractValue'" + contractValue + '\'' +
                ", ksValue'" + ksValue + '\'' +
                ", deactivationDate=" + deactivationDate +
                ", deactivated=" + activeStatus +
                '}';
    }
}
