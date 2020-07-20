package com.acme;

import com.fasterxml.jackson.databind.Module;
import com.github.driftserver.core.infra.jackson.DriftCoreJacksonModule;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.serialization.JsonModelSerializer;
import com.github.driftserver.core.metamodel.serialization.YamlModelSerializer;
import com.github.driftserver.elasticsearch.DriftElasticSearchJacksonModule;
import com.github.driftserver.filesystem.DriftFileSystemJacksonModule;
import com.github.driftserver.jdbc.infra.DriftJDBCJacksonModule;
import org.springframework.beans.factory.FactoryBean;

public class AcmeModelStoreFactory implements FactoryBean<ModelStore> {

    @Override
    public ModelStore getObject() {

        Module coreJacksonModule = new DriftCoreJacksonModule();
        Module jdbcJacksonModule = new DriftJDBCJacksonModule();
        Module fileSystemJacksonModule = new DriftFileSystemJacksonModule();
        Module esModule = new DriftElasticSearchJacksonModule();

        JsonModelSerializer jsonModelSerializer = new JsonModelSerializer();
        jsonModelSerializer.registerModule(coreJacksonModule);
        jsonModelSerializer.registerModule(jdbcJacksonModule);
        jsonModelSerializer.registerModule(fileSystemJacksonModule);
        jsonModelSerializer.registerModule(esModule);

        YamlModelSerializer yamlModelSerializer = new YamlModelSerializer();
        yamlModelSerializer.registerModule(coreJacksonModule);
        yamlModelSerializer.registerModule(jdbcJacksonModule);
        yamlModelSerializer.registerModule(fileSystemJacksonModule);
        yamlModelSerializer.registerModule(esModule);

        return null;

    }

    @Override
    public Class<?> getObjectType() {
        return ModelStore.class;
    }


}
