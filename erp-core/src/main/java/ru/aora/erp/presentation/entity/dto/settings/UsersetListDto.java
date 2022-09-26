package ru.aora.erp.presentation.entity.dto.settings;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public final class UsersetListDto {
    private List<UsersetDto> usersets;

    private UsersetListDto(List<UsersetDto> usersets) {
        this.usersets = usersets;
    }

    public static UsersetListDto of(List<UsersetDto> usersets) {
        return new UsersetListDto(usersets);
    }

    public static UsersetListDto of(UsersetDto... usersets) {
        return new UsersetListDto(Arrays.asList(usersets));
    }

    public List<UsersetDto> getUsersetDto() {
        return usersets;
    }

    public void setUsersetDto(List<UsersetDto> usersets) {
        this.usersets = usersets;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UsersetListDto.class.getSimpleName() + "[", "]")
                .add("usersets=" + usersets)
                .toString();
    }
}
