package com.github.driftserver.core.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

public class SubSystemEnvironmentKeyDeserializer extends KeyDeserializer {



    @Override
    public SubSystemEnvironmentKey deserializeKey(String string, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String[] parts = string.split("::");
        SubSystemKey subSystemKey  = new SubSystemKey(parts[0]);
        EnvironmentKey environmentKey = new EnvironmentKey(parts[1]);
        return new SubSystemEnvironmentKey(subSystemKey, environmentKey);
    }
}
