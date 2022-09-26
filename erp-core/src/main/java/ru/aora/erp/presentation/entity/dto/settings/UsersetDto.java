package ru.aora.erp.presentation.entity.dto.settings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.StringJoiner;

public final class UsersetDto {

    private String id;
    private Integer userThemeColor;
    private Integer userThemePattern;
    @NotNull
    @Positive
    private BigDecimal userThemeZoom;

    public String getId() {
        return id;
    }

    public UsersetDto setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getUserThemeColor() {
        return userThemeColor;
    }

    public UsersetDto setUserThemeColor(Integer userThemeColor) {
        this.userThemeColor = userThemeColor;
        return this;
    }

    public Integer getUserThemePattern() {
        return userThemePattern;
    }

    public UsersetDto setUserThemePattern(Integer userThemePattern) {
        this.userThemePattern = userThemePattern;
        return this;
    }

    public BigDecimal getUserThemeZoom() {
        return userThemeZoom;
    }

    public UsersetDto setUserThemeZoom(BigDecimal userThemeZoom) {
        this.userThemeZoom = userThemeZoom;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UsersetDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("userThemeColor='" + userThemeColor + "'")
                .add("userThemePattern=" + userThemePattern)
                .add("userThemeZoom='" + userThemeZoom + "'")
                .toString();
    }
}
