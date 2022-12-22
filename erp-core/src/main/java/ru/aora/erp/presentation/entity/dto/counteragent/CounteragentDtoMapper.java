package ru.aora.erp.presentation.entity.dto.counteragent;

import ru.aora.erp.presentation.entity.dto.utils.MapperUtils;
import ru.aora.erp.model.entity.business.Counteragent;

import java.util.List;

public final class CounteragentDtoMapper {

    public static CounteragentDto toCounteragentDto(Counteragent counteragent) {
        return new CounteragentDto()
                .setAddress(counteragent.getAddress())
                .setCounteragentName(counteragent.getCounteragentName())
                .setDirectorFirstName(counteragent.getDirectorFirstName())
                .setDirectorPatronymic(counteragent.getDirectorPatronymic())
                .setDirectorSurname(counteragent.getDirectorSurname())
                .setGroupName(counteragent.getGroupName())
                .setId(counteragent.getId())
                .setOldId(counteragent.getOldId())
                .setMail(counteragent.getMail())
                .setPhoneNumber(counteragent.getPhoneNumber())
                .setInn(counteragent.getInn())
                .setContractSumValue(counteragent.getContractSumValue())
                .setKSSumValue(counteragent.getKSSumValue());
    }

    public static Counteragent toCounteragent(CounteragentDto dto) {
        return new Counteragent()
                .setAddress(dto.getAddress())
                .setCounteragentName(dto.getCounteragentName())
                .setDirectorFirstName(dto.getDirectorFirstName())
                .setDirectorPatronymic(dto.getDirectorPatronymic())
                .setDirectorSurname(dto.getDirectorSurname())
                .setGroupName(dto.getGroupName())
                .setOldId(dto.getOldId())
                .setId(dto.getId())
                .setMail(dto.getMail())
                .setPhoneNumber(dto.getPhoneNumber())
                .setInn(dto.getInn())
                .setContractSumValue(dto.getContractSumValue())
                .setKSSumValue(dto.getKSSumValue());
    }

    public static CounteragentListDto toListDto(List<Counteragent> counteragents) {
        return CounteragentListDto.of(MapperUtils.convert(counteragents, CounteragentDtoMapper::toCounteragentDto));
    }
}
