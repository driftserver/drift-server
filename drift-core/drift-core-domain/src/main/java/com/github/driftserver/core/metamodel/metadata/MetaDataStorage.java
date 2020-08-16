package com.github.driftserver.core.metamodel.metadata;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetaDataStorage {

    private ObjectMapper objectMapper;

    private FileSystemMetaDataURNResolver urnResolver;

    public MetaDataStorage(Path baseDir) {
        initMapper();
        urnResolver = new FileSystemMetaDataURNResolver(baseDir);
    }

    private void initMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public void writeMetaData(ModelURN modelURN, MetaData metaData) throws ModelException {
        ModelURN parentURN = modelURN.getParentURN();
        List<MetaData> metaDataList = readList(parentURN);
        metaDataList.add(metaData);
        writeList(metaDataList, parentURN);
    }

    public MetaData readMetaData(ModelURN modelURN) throws ModelException {
        ModelURN parentURN = modelURN.getParentURN();
        ModelId modelId = modelURN.getLastFragment();
        return readList(parentURN).stream()
                .filter(metaData -> metaData.getModelId().equals(modelId))
                .findFirst()
                .get();
    }

    public List<MetaData> listMetaData(ModelURN parentURN) throws ModelException {
        return readList(parentURN);
    }

    private void writeList(List<MetaData> metaDataList, ModelURN parentURN) throws ModelException {
        try {
            Writer writer = urnResolver.getWriter(parentURN);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, metaDataList);
        } catch (Exception e) {
            throw new ModelException(String.format("error writing metadata list to parent URN. parentURN=%s, list=%s", parentURN, metaDataList), e);
        }
    }

    private List<MetaData> readList(ModelURN parentURN) throws ModelException {
        try {
            Optional<Reader> reader = urnResolver.getReader(parentURN);
            if (reader.isPresent()) {
                return objectMapper.readValue(reader.get(), new TypeReference<List<MetaData>>(){});
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new ModelException(String.format("error listing metadata for parent URN. parentURN=%s", parentURN), e);
        }
    }

}