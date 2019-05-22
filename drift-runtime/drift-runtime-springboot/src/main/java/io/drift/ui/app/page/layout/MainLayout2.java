package io.drift.ui.app.page.layout;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

import static io.drift.ui.infra.WicketUtil.addCssResource;

public abstract class MainLayout2 extends WebPage {

	public MainLayout2()  {
		add(new Menu("menu"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
	}

}
