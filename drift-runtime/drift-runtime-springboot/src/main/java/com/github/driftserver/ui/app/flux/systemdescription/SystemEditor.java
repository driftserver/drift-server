package com.github.driftserver.ui.app.flux.systemdescription;

import com.github.driftserver.ui.app.component.editor.component.NavListComponent;
import com.github.driftserver.ui.app.component.editor.document.CompositeDocumentElement;
import com.github.driftserver.ui.app.component.editor.document.RootElement;
import com.github.driftserver.ui.app.component.editor.structure.EditorElement;
import com.github.driftserver.ui.app.component.editor.structure.EditorStructure;
import com.github.driftserver.ui.app.component.editor.structure.NavListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class SystemEditor {

    @Autowired
    private SystemStore systemStore;

    public RootElement getSystemDocumentDescriptor() {

        RootElement rootElement = new RootElement();

        CompositeDocumentElement envList = new CompositeDocumentElement();

        return rootElement;

    }

    private org.apache.wicket.Component rootComponent;

    public EditorStructure getEditorStructure() {
        EditorElement rootElement = new NavListElement(new Supplier<List <String>>() {

            @Override
            public List<String> get() {
                ArrayList list = new ArrayList();
                list.add("Environments");
                list.add("System Structure");
                return list;
            }
        }, (target, element) -> {
            target.add(rootComponent);
            System.out.println(element + " clicked.");
        });
        EditorStructure editorStructure = new EditorStructure();
        editorStructure.setRootElement(rootElement);
        return editorStructure;

    }

    public org.apache.wicket.Component render(String id, EditorElement editorElement) {
        org.apache.wicket.Component component = null;
        if (editorElement instanceof NavListElement) {
            NavListElement navListElement = (NavListElement) editorElement;
            component = new NavListComponent(id, navListElement);
        }
        rootComponent = component;
        rootComponent.setOutputMarkupId(true);
        return component;
    }

}
