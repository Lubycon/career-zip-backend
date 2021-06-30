package com.careerzip.domain.acquisition.repository;

import com.careerzip.domain.acquisition.entity.Acquisition;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.careerzip.testobject.acquisition.AcquisitionFactory.createAcquisition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AcquisitionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AcquisitionRepository acquisitionRepository;

    @Test
    @DisplayName("UtmSource를 기준 조회 메서드 테스트")
    void findByUtmSourceTest() {
        // given
        Acquisition acquisition = acquisitionRepository.save(createAcquisition());

        // when
        Acquisition foundAcquisition = acquisitionRepository.findByUtmSource(acquisition.getUtmSource()).orElse(null);

        // then
        assertThat(foundAcquisition.getUtmSource()).isEqualTo(acquisition.getUtmSource());
    }
}
