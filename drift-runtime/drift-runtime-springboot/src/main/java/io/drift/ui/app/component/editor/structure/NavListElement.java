package io.drift.ui.app.component.editor.structure;

import io.drift.ui.infra.WicketUtil.SerializableBiConsumer;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.List;
import java.util.function.Supplier;

public class NavListElement extends EditorElement {

    private Supplier<List<String>> elements;

    private SerializableBiConsumer<AjaxRequestTarget, String> linkAction;

    public NavListElement(Supplier<List<String>> elements, SerializableBiConsumer<AjaxRequestTarget, String> linkAction) {
        this.elements = elements;
        this.linkAction = linkAction;
    }

    public Supplier<List<String>> getElements() {
        return elements;
    }

    public SerializableBiConsumer<AjaxRequestTarget, String> getLinkAction(String element) {
        return linkAction;
    }
}
