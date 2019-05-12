package io.drift.ui.app.page.layout;

import io.drift.ui.infra.WicketUtil;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

import static io.drift.ui.infra.WicketUtil.addCssResource;

public abstract class MainLayout extends WebPage {

	public MainLayout()  {
		add(new Menu("menu"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		addCssResource("bootstrap.min.css", MainLayout.class, response);
		// addCssResource("css/font-awesome-5.8.2.css", MainLayout.class, response);
	}

}
