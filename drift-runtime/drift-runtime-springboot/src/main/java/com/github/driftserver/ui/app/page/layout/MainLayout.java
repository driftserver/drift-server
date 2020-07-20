package com.github.driftserver.ui.app.page.layout;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

import static com.github.driftserver.ui.infra.WicketUtil.addCssResource;

public abstract class MainLayout extends WebPage {

	public MainLayout() {
		// add(new Menu("menu"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		addCssResource("bootstrap.min.css", MainLayout.class, response);
		addCssResource("css/sidebar.css", MainLayout.class, response);
		// addCssResource("css/font-awesome-5.8.2.css", MainLayout.class, response);
	}

}
