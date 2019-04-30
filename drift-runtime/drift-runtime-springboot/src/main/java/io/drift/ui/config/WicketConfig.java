package io.drift.ui.config;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.RequestCycleSettings.RenderStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

@Configuration
public class WicketConfig {

	@Bean
	public WicketApplicationInitConfiguration getBean() {
		return new WicketApplicationInitConfiguration() {
			@Override
			public void init(WebApplication webApplication) {

				webApplication.getMarkupSettings().setStripWicketTags(true).setCompressWhitespace(true).setStripComments(true);
				// getApplication().getDebugSettings().setAjaxDebugModeEnabled(false);
				// setStatelessHint(true);
				// setVersioned(false);

				webApplication.getRequestCycleSettings().setRenderStrategy(RenderStrategy.ONE_PASS_RENDER);
			}
		};
	}

}
