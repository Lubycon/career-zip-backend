package com.careerzip.testobject.job;

import com.careerzip.domain.job.entity.Job;

public class JobFactory {

    public static Job createJob() {
        return new Job("Job Name");
    }
}
