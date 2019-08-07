package io.drift.jdbc.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.drift.core.recording.SubSystemDescription;
import io.drift.core.recording.SystemInteraction;
import io.drift.core.system.*;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;

import java.io.IOException;

public class DriftJDBCJacksonModule extends SimpleModule {


    public DriftJDBCJacksonModule() {
        super();
        registerSubtypes(
                DBSnapShot.class,
                DBMetaData.class,
                DBDelta.class,
                JDBCConnectionDetails.class
        );


    }

}
