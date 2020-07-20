package io.drift.core.systemdescription;

import io.drift.core.metamodel.ModelException;
import io.drift.core.metamodel.ModelStore;
import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;
import io.drift.core.system.SystemDescription;
import org.springframework.stereotype.Component;

@Component
public class SystemDescriptionStorage {

    private static final ModelURN SYSTEM_DESCRIPTION_URN = ModelURN.of(new ModelId("system"), new ModelId("systemdescription"));

    private final ModelStore modelStore;

    public SystemDescriptionStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
    }

    public SystemDescription load() {
        try {
            SystemDescription systemDescription = modelStore.read(SYSTEM_DESCRIPTION_URN, SystemDescription.class);
            return systemDescription;
        } catch (ModelException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
