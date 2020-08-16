package com.github.driftserver.elasticsearch;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class DriftElasticSearchJacksonModule extends SimpleModule {

    public DriftElasticSearchJacksonModule() {
        super();
        registerSubtypes(
                ElasticSearchSettings.class,

                ElasticSearchSnapshot.class,
                IndexSnapshot.class,

                ElasticSearchDelta.class
        );
    }

}