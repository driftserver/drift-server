package com.github.driftserver.ui.app.component.popup;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class Popup extends ModalWindow {

	private static final ResourceReference POPUP_CSS = new CssResourceReference(Popup.class, "popup.css");

	public Popup(String id) {
		super(id);
		super.setCssClassName("popup");
		setAutoSize(true);
		showUnloadConfirmation(false);
	}

//	@Override
//	protected ResourceReference newCssResource() {
//		return POPUP_CSS;
//	}

}
