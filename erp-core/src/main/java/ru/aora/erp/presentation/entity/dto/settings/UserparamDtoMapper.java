package ru.aora.erp.presentation.entity.dto.settings;

import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.model.entity.business.Userparam;
import ru.aora.erp.presentation.entity.dto.ks.KsDto;
import ru.aora.erp.presentation.entity.dto.ks.KsDtoMapper;
import ru.aora.erp.presentation.entity.dto.utils.MapperUtils;

import java.util.List;

public final class UserparamDtoMapper {

    public static UserparamDto toUserparamDto(Userparam userparam) {
        return new UserparamDto()
                .setId(userparam.getId())
                .setUserId(userparam.getUserId())
                .setUserThemeColor(userparam.getUserThemeColor())
                .setUserThemePattern(userparam.getUserThemePattern())
                .setUserThemeZoom(userparam.getUserThemeZoom());
    }

    public static Userparam toUserparam(UserparamDto userparamDto) {
        return new Userparam()
                .setId(userparamDto.getId())
                .setUserId(userparamDto.getUserId())
                .setUserThemeColor(userparamDto.getUserThemeColor())
                .setUserThemePattern(userparamDto.getUserThemePattern())
                .setUserThemeZoom(userparamDto.getUserThemeZoom());
    }

    public static List<UserparamDto> toUserparamDtoList(List<Userparam> userparams) {
        return MapperUtils.convert(userparams, UserparamDtoMapper::toUserparamDto);
    }


}
