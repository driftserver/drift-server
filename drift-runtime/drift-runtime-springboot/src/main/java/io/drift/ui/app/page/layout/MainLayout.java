package io.drift.ui.app.page.layout;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

public abstract class MainLayout extends WebPage {

	public MainLayout()  {
		add(new Menu("menu"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(MainLayout.class, "bootstrap.min.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}


}
