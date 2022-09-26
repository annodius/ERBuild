package ru.aora.erp.model.entity.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Userset {

    private String id;
    private Integer userThemeColor;
    private Integer userThemePattern;
    private BigDecimal userThemeZoom;
    private LocalDateTime deactivationDate;
    private Integer activeStatus;

    public String getId() {
        return id;
    }

    public Userset setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getUserThemeColor() {
        return userThemeColor;
    }

    public Userset setUserThemeColor(Integer userThemeColor) {
        this.userThemeColor = userThemeColor;
        return this;
    }

    public Integer getUserThemePattern() {
        return userThemePattern;
    }

    public Userset setUserThemePattern(Integer userThemePattern) {
        this.userThemePattern = userThemePattern;
        return this;
    }

    public BigDecimal getUserThemeZoom() {
        return userThemeZoom;
    }

    public Userset setUserThemeZoom(BigDecimal userThemeZoom) {
        this.userThemeZoom = userThemeZoom;
        return this;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public Userset setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public Userset setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    @Override
    public String toString() {
        return "Userset{" +
                "id='" + id + '\'' +
                ", userThemeColor='" + userThemeColor + '\'' +
                ", userThemePattern=" + userThemePattern +
                ", userThemeZoom='" + userThemeZoom + '\'' +
                ", deactivationDate=" + deactivationDate +
                ", deactivated=" + activeStatus +
                '}';
    }
}
