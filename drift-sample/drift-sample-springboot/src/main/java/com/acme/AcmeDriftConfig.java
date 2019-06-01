package com.acme;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import de.agilecoders.wicket.core.Bootstrap;
import io.drift.core.store.IDGenerator;
import io.drift.core.store.IDGeneratorUUIDImpl;
import io.drift.core.store.ModelStore;
import io.drift.core.store.serialization.JsonModelSerializer;
import io.drift.core.store.storage.FileSystemModelStorage;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.infra.DriftJDBCJacksonModule;
import io.drift.ui.config.DefaultDriftSpringSecurityConfig;
import org.apache.wicket.settings.RequestCycleSettings;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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


    @Bean(name="modelStore")
    AcmeModelStoreFactory modelStoreFactory() {
        return new AcmeModelStoreFactory();
    }

    @Bean
    ModelStore getModelStore() throws Exception {
        return modelStoreFactory().getObject();
    }

    @Bean
    IDGenerator idGenerator() {
        return new IDGeneratorUUIDImpl();
    }

}
