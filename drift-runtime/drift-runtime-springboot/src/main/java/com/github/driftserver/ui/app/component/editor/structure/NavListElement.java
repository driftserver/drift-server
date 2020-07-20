package com.github.driftserver.ui.app.component.editor.structure;

import com.github.driftserver.ui.infra.WicketUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.List;
import java.util.function.Supplier;

public class NavListElement extends EditorElement {

    private Supplier<List<String>> elements;

    private WicketUtil.SerializableBiConsumer<AjaxRequestTarget, String> linkAction;

    public NavListElement(Supplier<List<String>> elements, WicketUtil.SerializableBiConsumer<AjaxRequestTarget, String> linkAction) {
        this.elements = elements;
        this.linkAction = linkAction;
    }

    public Supplier<List<String>> getElements() {
        return elements;
    }

    public WicketUtil.SerializableBiConsumer<AjaxRequestTarget, String> getLinkAction(String element) {
        return linkAction;
    }
}
