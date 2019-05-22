package io.drift.core.systemdescription;

import io.drift.core.system.SystemDescription;
import io.drift.core.system.SystemDescriptionDomainService;
import org.springframework.stereotype.Component;

@Component
public class SystemDescriptionDomainserviceImpl implements SystemDescriptionDomainService {

    private final SystemDescriptionStorage systemDescriptionStorage;

    public SystemDescriptionDomainserviceImpl(SystemDescriptionStorage systemDescriptionStorage) {
        this.systemDescriptionStorage = systemDescriptionStorage;
    }

    public SystemDescription getSystemDescription() {
            return systemDescriptionStorage.load();
    }

}
