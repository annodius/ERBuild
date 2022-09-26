package ru.aora.erp.presentation.entity.dto.settings;

import ru.aora.erp.model.entity.business.Userset;
import ru.aora.erp.presentation.entity.dto.utils.MapperUtils;

import java.util.List;

public final class UsersetDtoMapper {

    public static UsersetDto toUsersetDto(Userset userset) {
        return new UsersetDto()
                .setId(userset.getId())
                .setUserThemeColor(userset.getUserThemeColor())
                .setUserThemePattern(userset.getUserThemePattern())
                .setUserThemeZoom(userset.getUserThemeZoom());
    }

    public static Userset toUserset(UsersetDto dto) {
        return new Userset()
                .setId(dto.getId())
                .setUserThemeColor(dto.getUserThemeColor())
                .setUserThemePattern(dto.getUserThemePattern())
                .setUserThemeZoom(dto.getUserThemeZoom());
    }

    public static UsersetListDto toListDto(List<Userset> usersets) {
        return UsersetListDto.of(MapperUtils.convert(usersets, UsersetDtoMapper::toUsersetDto));
    }
}
