package com.acme;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.StandardCoreModule;
import com.github.driftserver.core.metamodel.id.IDGenerator;
import com.github.driftserver.core.metamodel.id.IDGeneratorUUIDImpl;
import com.github.driftserver.core.metamodel.metadata.MetaDataStorage;
import com.github.driftserver.elasticsearch.DriftElasticSearchModule;
import com.github.driftserver.filesystem.DriftFileSystemModule;
import com.github.driftserver.jdbc.infra.DriftJdbcModule;
import com.github.driftserver.ui.config.DefaultDriftSpringSecurityConfig;
import de.agilecoders.wicket.core.Bootstrap;
import org.apache.wicket.settings.RequestCycleSettings;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@Import({ DefaultDriftSpringSecurityConfig.class})
public class AcmeDriftConfig {

    @Bean
    public WicketBootWebApplication wicketWebApplication() {
        return new WicketBootStandardWebApplication() {
            @Override
            protected void init() {
                super.init();
                Bootstrap.install(this);
                getMarkupSettings().setStripWicketTags(true).setCompressWhitespace(true).setStripComments(true);
                // getApplication().getDebugSettings().setAjaxDebugModeEnabled(false);
                // setStatelessHint(true);
                // setVersioned(false);

                getRequestCycleSettings().setRenderStrategy(RequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);
            }
        };
    }

    private Path baseDir = Paths.get("store");

    @Bean
    MetaDataStorage getMetaDataStorage() {
        return new MetaDataStorage(baseDir);
    }

    @Bean
    ModelStore getModelStore() {
        return ModelStore.builder()
                .withModules(new StandardCoreModule(baseDir), new DriftJdbcModule(), new DriftFileSystemModule(), new DriftElasticSearchModule())
                .build();
    }

    @Bean
    IDGenerator idGenerator() {
        return new IDGeneratorUUIDImpl();
    }

}
