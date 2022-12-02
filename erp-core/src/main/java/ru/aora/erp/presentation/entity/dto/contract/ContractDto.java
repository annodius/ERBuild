package ru.aora.erp.presentation.entity.dto.contract;

import ru.aora.erp.presentation.entity.dto.ks.KsDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

public final class ContractDto {

    private String id;

    private String oldId;
    private String counteragentId;
    private int contractType;
    private LocalDate contractDate;
    private String contractNumber;
    private String contractSubject;
    @NotNull
    @Positive
    private BigDecimal contractSum;

    public String getId() {
        return id;
    }

    public ContractDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getOldId() {
        return oldId;
    }

    public ContractDto setOldId(String oldId) {
        this.oldId = oldId;
        return this;
    }

    public String getCounteragentId() {
        return counteragentId;
    }

    public ContractDto setCounteragentId(String counteragentId) {
        this.counteragentId = counteragentId;
        return this;
    }

    public int getContractType() {
        return contractType;
    }

    public ContractDto setContractType(int contractType) {
        this.contractType = contractType;
        return this;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public ContractDto setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
        return this;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public ContractDto setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public String getContractSubject() {
        return contractSubject;
    }

    public ContractDto setContractSubject(String contractSubject) {
        this.contractSubject = contractSubject;
        return this;
    }
    public BigDecimal getContractSum() {
        return contractSum;
    }

    public ContractDto setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
        return this;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", ContractDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("oldId=" + oldId)
                .add("counteragentId=" + counteragentId)
                .add("contractType='" + contractType + "'")
                .add("contractDate='" + contractDate + "'")
                .add("contractNumber='" + contractNumber + "'")
                .add("contractSubject='" + contractSubject + "'")
                .add("contractSum='" + contractSum + "'")
                .toString();
    }
}
