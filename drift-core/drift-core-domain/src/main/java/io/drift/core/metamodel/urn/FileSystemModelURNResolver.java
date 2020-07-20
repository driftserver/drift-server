package io.drift.core.metamodel.urn;

import io.drift.core.metamodel.*;
import io.drift.core.metamodel.id.ModelId;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemModelURNResolver implements ModelURNResolver {

    private Path baseDir;

    public FileSystemModelURNResolver(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public Reader getReader(ModelURN urn, ModelDescriptor modelDescriptor) throws ModelException {
        try {
            Path path = getPath(urn, modelDescriptor);
            return new BufferedReader(new FileReader(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving urn to filesystem reader. urn=%s, descriptor=%s", urn, modelDescriptor), e);
        }
    }

    @Override
    public Writer getWriter(ModelURN urn, ModelDescriptor descriptor) throws ModelException {
        try {
            Path path = getPath(urn, descriptor);
            Files.createDirectories(path.getParent());
            return new BufferedWriter(new FileWriter(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving urn to filesystem writer. urn=%s, descriptor=%s", urn, descriptor), e);
        }
    }

    Path getPath(ModelURN urn, ModelDescriptor descriptor) {
        ModelURN parentUrn = urn.getParentURN();
        ModelId modelId = urn.getLastFragment();
        Path path = getParentPath(parentUrn).resolve(getFileName(modelId, descriptor));
        System.out.println("path: " + path);
        return path;
    }

    Path getParentPath(ModelURN parentURN) {
        Path path = baseDir;
        for (ModelId pathSegment : parentURN.getFragments()) {
            path = path.resolve(pathSegment.getId());
        }
        return path;
    }

    String getFileName(ModelId modelId, ModelDescriptor descriptor) {
        return modelId.getId() + "." + descriptor.getFormat().getFileExtension();
    }

}
