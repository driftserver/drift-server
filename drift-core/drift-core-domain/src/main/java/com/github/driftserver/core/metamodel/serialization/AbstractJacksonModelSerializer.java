package com.github.driftserver.core.metamodel.serialization;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelFormat;

import java.io.Reader;
import java.io.Writer;

abstract public class AbstractJacksonModelSerializer extends ModelSerializer {

    protected ObjectMapper objectMapper;

    public AbstractJacksonModelSerializer(ModelFormat format) {
        super(format);
    }

    @Override
    public Model read(Reader reader, Class<? extends Model> modelClass) throws ModelException {
        try  {
            return objectMapper.readValue(reader, modelClass);
        } catch (Exception e) {
            throw new ModelException(String.format("error reading model. reader=%s, modelClass=%s", reader, modelClass), e);
        }
    }

    @Override
    public void write(Writer writer,Model model) throws ModelException {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, model);
        } catch (Exception e) {
            throw new ModelException(String.format("error writing model. writer=%s, model=%s", writer, model), e);
        }
    }

    public void registerJacksonModule(Module module) {
        objectMapper.registerModule(module);
    }

}
