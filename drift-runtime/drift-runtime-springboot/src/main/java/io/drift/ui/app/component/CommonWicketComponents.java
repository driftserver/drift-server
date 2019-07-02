package io.drift.ui.app.component;

import io.drift.core.WicketComponentFactory;
import io.drift.core.WicketComponentFactoryMethod;
import io.drift.core.system.NullDetails;
import io.drift.ui.app.component.settings.MissingConnectionDetailsComponent;
import io.drift.ui.app.page.system.ConnectionDetailsView;
import org.springframework.stereotype.Component;

@Component
@WicketComponentFactory
public class CommonWicketComponents {

    @WicketComponentFactoryMethod(
            dataType = NullDetails.class,
            viewType = ConnectionDetailsView.class
    )
    public MissingConnectionDetailsComponent missingConnectionDetailsComponent(String id, NullDetails nullDetails) {
        return new MissingConnectionDetailsComponent(id);
    }


}
