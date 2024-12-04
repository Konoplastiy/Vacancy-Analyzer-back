package com.konolastiy.vacancyanalyzer.service;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ExperienceLevelConstants.*;

import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.payload.platform.PlatformInfoDto;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformInfoService {

  private final VacancyRepository vacancyRepository;
  private final SourceRepository sourceRepository;

  @Transactional
  public List<PlatformInfoDto> getPlatformCountsVacancies() {
    List<Source> sources = sourceRepository.findAll();
    List<PlatformInfoDto> platformInfoList = new ArrayList<>();

    for (Source source : sources) {
      PlatformInfoDto platformInfoDto = new PlatformInfoDto();
      platformInfoDto.setNamePlatform(source.getName().toString());

      Integer seniorCount = vacancyRepository.countBySourceAndExperienceLevel(source, SENIOR);
      Integer middleCount = vacancyRepository.countBySourceAndExperienceLevel(source, MIDDLE);
      Integer juniorCount = vacancyRepository.countBySourceAndExperienceLevel(source, JUNIOR);
      Integer otherCount =
          vacancyRepository.countBySourceAndExperienceLevel(source, DEFAULT_EXPERIENCE_LEVEL);

      platformInfoDto.setSenior(seniorCount);
      platformInfoDto.setMiddle(middleCount);
      platformInfoDto.setJunior(juniorCount);
      platformInfoDto.setOthers(otherCount);

      platformInfoList.add(platformInfoDto);
    }

    return platformInfoList;
  }
}
