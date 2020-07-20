package com.github.driftserver.ui.app.component;

import com.github.driftserver.core.WicketComponentFactory;
import com.github.driftserver.core.WicketComponentFactoryMethod;
import com.github.driftserver.core.system.NullDetails;
import com.github.driftserver.ui.app.component.settings.MissingConnectionDetailsComponent;
import com.github.driftserver.ui.app.page.system.ConnectionDetailsView;
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
