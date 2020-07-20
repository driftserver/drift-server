package io.drift.core.metamodel.serialization;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.drift.core.metamodel.Model;
import io.drift.core.metamodel.ModelDescriptor;
import io.drift.core.metamodel.ModelException;
import io.drift.core.metamodel.ModelFormat;

import java.io.Reader;
import java.io.Writer;

abstract public class AbstractJacksonModelSerializer extends ModelSerializer {

    protected ObjectMapper objectMapper;

    public AbstractJacksonModelSerializer(ModelFormat format) {
        super(format);
    }

    @Override
    public Model read(Reader reader, ModelDescriptor descriptor) throws ModelException {
        try  {
            return (Model) objectMapper.readValue(reader, descriptor.getModelClass());
        } catch (Exception e) {
            throw new ModelException(String.format("error reading model. reader=%s, descriptor=%s", reader, descriptor), e);
        }
    }

    @Override
    public void write(Writer writer,Model model, ModelDescriptor descriptor) throws ModelException {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, model);
        } catch (Exception e) {
            throw new ModelException(String.format("error writing model. writer=%s, model=%s, descriptor=%s", writer, model, descriptor), e);
        }
    }

    public void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

}
