package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.common.mapper.PlatformInfoMapper;
import com.konolastiy.vacancyanalyzer.entity.PlatformInfo;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.payload.platform.PlatformInfoDto;
import com.konolastiy.vacancyanalyzer.repository.PlatformInfoRepository;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformInfoService {

    private final PlatformInfoRepository platformInfoRepository;
    private final VacancyRepository vacancyRepository;
    private final SourceRepository sourceRepository;
    private final PlatformInfoMapper platformInfoMapper;

    @Transactional
    public List<PlatformInfoDto> getPlatformCountsVacancies() {
        List<Source> sources = sourceRepository.findAll();
        List<PlatformInfoDto> platformInfoList = new ArrayList<>();

        for (Source source : sources) {
            PlatformInfo platformInfo = new PlatformInfo();
            platformInfo.setPlatformName(source.getName().toString());

            int seniorCount = vacancyRepository.countBySourceAndExperienceLevel(source, "Senior");
            int middleCount = vacancyRepository.countBySourceAndExperienceLevel(source, "Middle");
            int juniorCount = vacancyRepository.countBySourceAndExperienceLevel(source, "Junior");
            int otherCount = vacancyRepository.countBySourceAndExperienceLevel(source, "Others");

            platformInfo.setSeniorCount(seniorCount);
            platformInfo.setMiddleCount(middleCount);
            platformInfo.setJuniorCount(juniorCount);
            platformInfo.setOtherCount(otherCount);

            platformInfoRepository.save(platformInfo);

            PlatformInfoDto platformInfoDto = platformInfoMapper.toDto(platformInfo);
            platformInfoList.add(platformInfoDto);
        }

        return platformInfoList;
    }
}
