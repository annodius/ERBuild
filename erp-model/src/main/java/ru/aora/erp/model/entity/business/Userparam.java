package ru.aora.erp.model.entity.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Userparam {

    private String id;

    private String userId;
    private Integer userThemeColor;
    private Integer userThemePattern;
    private BigDecimal userThemeZoom;
    private LocalDateTime deactivationDate;
    private Integer activeStatus;

    public String getId() {
        return id;
    }

    public Userparam setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Userparam setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUserThemeColor() {
        return userThemeColor;
    }

    public Userparam setUserThemeColor(Integer userThemeColor) {
        this.userThemeColor = userThemeColor;
        return this;
    }

    public Integer getUserThemePattern() {
        return userThemePattern;
    }

    public Userparam setUserThemePattern(Integer userThemePattern) {
        this.userThemePattern = userThemePattern;
        return this;
    }

    public BigDecimal getUserThemeZoom() {
        return userThemeZoom;
    }

    public Userparam setUserThemeZoom(BigDecimal userThemeZoom) {
        this.userThemeZoom = userThemeZoom;
        return this;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public Userparam setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
        return this;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public Userparam setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    @Override
    public String toString() {
        return "Userparam{" +
                "id='" + id + '\'' +
                "userId='" + userId + '\'' +
                ", userThemeColor='" + userThemeColor + '\'' +
                ", userThemePattern='" + userThemePattern +'\'' +
                ", userThemeZoom='" + userThemeZoom + '\'' +
                ", deactivationDate=" + deactivationDate +
                ", deactivated=" + activeStatus +
                '}';
    }
}
