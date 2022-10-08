package ru.aora.erp.presentation.entity.dto.settings;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public final class UserparamListDto {
    private List<UserparamDto> userparams;

    private UserparamListDto(List<UserparamDto> userparams) {
        this.userparams = userparams;
    }

    public static UserparamListDto of(List<UserparamDto> userparams) {
        return new UserparamListDto(userparams);
    }

    public static UserparamListDto of(UserparamDto... userparams) {
        return new UserparamListDto(Arrays.asList(userparams));
    }

    public List<UserparamDto> getUserparamDto() {
        return userparams;
    }

    public void setUserparamDto(List<UserparamDto> userparams) {
        this.userparams = userparams;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserparamListDto.class.getSimpleName() + "[", "]")
                .add("userparams=" + userparams)
                .toString();
    }
}
