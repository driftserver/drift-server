package io.drift.ui.app.component.stacktrace;

import io.drift.core.recording.ProblemDescription;
import io.drift.ui.infra.ListSelector;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static io.drift.ui.infra.WicketUtil.*;

public class ProblemDescriptionListComponent extends Panel {

    private ListSelector exceptionSelector = new ListSelector(null);
    private ListSelector problemSelector = new ListSelector(exceptionSelector);

    private WebMarkupContainer overview, problemDetail, exceptionDetail;
    private IModel<List<ProblemDescription>> problemDescriptions;

    public ProblemDescriptionListComponent(String id, IModel<List<ProblemDescription>> problemDescriptions) {
        super(id);
        this.problemDescriptions = problemDescriptions;
        setOutputMarkupId(true);
        add(overview = new WebMarkupContainer("overview"));
        overview.add(listView("problems", problemDescriptions, (item) -> {

            ProblemDescription problemDescription = item.getModelObject();
            item.add(new Label("description", getFormatted(problemDescription)));

            Link select = ajaxLink("select", (target) -> {
                problemSelector.select(0);
                target.add(ProblemDescriptionListComponent.this);
            });

            item.add(select);

        }));
        add(problemDetail = new WebMarkupContainer("problemDetail"));
        add(exceptionDetail = new WebMarkupContainer("exceptionDetail"));

    }

    private String getFormatted(ProblemDescription problemDescription) {
        return String.format(
                "%s while %s for %s ",
                problemDescription.getProblem(),
                problemDescription.getAction(),
                problemDescription.getLocation()
        );
    }

    protected void onConfigure() {
        super.onConfigure();
        overview.setVisible(!problemSelector.isSelected());
        problemDetail.setVisible(problemSelector.isSelected() && !exceptionSelector.isSelected());
        exceptionDetail.setVisible(problemSelector.isSelected() && exceptionSelector.isSelected());

        if (problemDetail.isVisible()) {
            replace(problemDetail = new WebMarkupContainer("problemDetail"));
            ProblemDescription problemDescription = problemDescriptions.getObject().get(problemSelector.getSelection());

            problemDetail.add(ajaxLink("unselect", (target) -> {
                problemSelector.emptySelection();
                target.add(ProblemDescriptionListComponent.this);
            }));

            if (problemDescription.isDriftException()) {
                ExternalLink externalLink = externalLink("documentationLink", "https://drift-io.gitbook.io/drift-server/errorcode/" + problemDescription.getDriftException().getCode());
                problemDetail.add(externalLink);
                externalLink.add(label("errorCode", problemDescription.getDriftException().getCode()));
            } else {
                ExternalLink externalLink = externalLink("documentationLink", "");
                externalLink.setVisible(false);
                problemDetail.add(externalLink);
            }

            problemDetail.add(label("description", getFormatted(problemDescription)));

            problemDetail.add(listView("exceptions", problemDescription.getMessages(), (item) -> {
                item.add(label("message", item.getModelObject()));
                item.add(ajaxLink("selectException", (target) -> {
                    exceptionSelector.select(0);
                    target.add(ProblemDescriptionListComponent.this);
                }));

            }));
        }

        if (exceptionDetail.isVisible()) {
            replace(exceptionDetail = new WebMarkupContainer("exceptionDetail"));

            exceptionDetail.add(ajaxLink("unselectException", (target) -> {
                exceptionSelector.emptySelection();
                target.add(ProblemDescriptionListComponent.this);
            }));

            ProblemDescription problemDescription = problemDescriptions.getObject().get(problemSelector.getSelection());
            exceptionDetail.add(label("exception", stackTraceToString(problemDescription.getException())));

        }

    }

    private String stackTraceToString(Exception e) {
        try (
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
        ) {
            e.printStackTrace(pw);
            return sw.toString();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}