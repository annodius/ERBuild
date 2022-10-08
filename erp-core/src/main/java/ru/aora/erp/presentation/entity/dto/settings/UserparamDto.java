package ru.aora.erp.presentation.entity.dto.settings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.StringJoiner;

public final class UserparamDto {

    private String id;

    private String userId;
    private Integer userThemeColor;
    private Integer userThemePattern;
    @NotNull
    @Positive
    private BigDecimal userThemeZoom;

    public String getId() {
        return id;
    }

    public UserparamDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public UserparamDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUserThemeColor() {
        return userThemeColor;
    }

    public UserparamDto setUserThemeColor(Integer userThemeColor) {
        this.userThemeColor = userThemeColor;
        return this;
    }

    public Integer getUserThemePattern() {
        return userThemePattern;
    }

    public UserparamDto setUserThemePattern(Integer userThemePattern) {
        this.userThemePattern = userThemePattern;
        return this;
    }

    public BigDecimal getUserThemeZoom() {
        return userThemeZoom;
    }

    public UserparamDto setUserThemeZoom(BigDecimal userThemeZoom) {
        this.userThemeZoom = userThemeZoom;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserparamDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("userId='" + userId + "'")
                .add("userThemeColor='" + userThemeColor + "'")
                .add("userThemePattern='" + userThemePattern + "'")
                .add("userThemeZoom='" + userThemeZoom + "'")
                .toString();
    }
}
