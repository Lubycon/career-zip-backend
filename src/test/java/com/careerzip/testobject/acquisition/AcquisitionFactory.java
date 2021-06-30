package com.careerzip.testobject.acquisition;

import com.careerzip.domain.acquisition.dto.Acquisition;

public class AcquisitionFactory {

    public static Acquisition createAcquisition() {
        return Acquisition.builder()
                          .id(1L)
                          .utmSource("utm source")
                          .build();
    }
}
