package com.konolastiy.vacancyanalyzer.common.mapper;

import com.konolastiy.vacancyanalyzer.entity.PlatformInfo;
import com.konolastiy.vacancyanalyzer.payload.platform.PlatformInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlatformInfoMapper {


    PlatformInfoDto toDto(PlatformInfo platformInfo);

    PlatformInfo fromDto(PlatformInfoDto platformInfoDto);

    List<PlatformInfoDto> toDtoList(List<PlatformInfo> platformInfos);
}
