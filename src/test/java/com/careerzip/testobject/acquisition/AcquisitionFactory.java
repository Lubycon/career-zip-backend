package com.careerzip.testobject.acquisition;

import com.careerzip.domain.acquisition.entity.Acquisition;

public class AcquisitionFactory {

    public static Acquisition createAcquisition() {
        return Acquisition.builder()
                          .id(1L)
                          .utmSource("utm source")
                          .build();
    }
}
