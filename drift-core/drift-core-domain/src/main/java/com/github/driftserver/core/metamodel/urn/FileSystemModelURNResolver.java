package com.github.driftserver.core.metamodel.urn;

import com.github.driftserver.core.metamodel.MetaData;
import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelFormat;
import com.github.driftserver.core.metamodel.id.ModelId;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class FileSystemModelURNResolver implements ModelURNResolver {

    private Path baseDir;

    public FileSystemModelURNResolver(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public Reader getReader(ModelURN urn, ModelFormat modelFormat, Class<? extends Model> modelClass) throws ModelException {
        try {
            Path path = getPath(urn, modelFormat, modelClass);
            return new BufferedReader(new FileReader(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving urn to filesystem reader. urn=%s, format=%s", urn, modelFormat), e);
        }
    }

    @Override
    public Writer getWriter(ModelURN urn, ModelFormat format, Class<? extends Model> modelClass) throws ModelException {
        try {
            Path path = getPath(urn, format, modelClass);
            Files.createDirectories(path.getParent());
            return new BufferedWriter(new FileWriter(path.toFile()));
        } catch (IOException e) {
            throw new ModelException(String.format("error resolving urn to filesystem writer. urn=%s, format=%s", urn, format), e);
        }
    }

    @Override
    public List<MetaData> listMetaData(ModelURN parentUrn) throws ModelException {
        try {
            Path parentPath = getParentPath(parentUrn);
            List<MetaData> list = new ArrayList<>();
            try (DirectoryStream<Path> files = Files.newDirectoryStream(parentPath, path -> !Files.isDirectory(path))) {
                for (Path path : files) {
                    list.add(getMetaData(path, parentUrn));
                }
            }
            return list;
        } catch (IOException e) {
            throw new ModelException(String.format("error listing metadata for parent urn. urn=%s", parentUrn), e);
        }
    }

    private MetaData getMetaData(Path path, ModelURN parentUrn) throws ModelException {
        try {
            String fileName = path.getFileName().toString();
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
            String idPart = fileNameWithoutExtension.substring(0, fileNameWithoutExtension.lastIndexOf('-'));
            String typePart = fileNameWithoutExtension.substring((idPart + '-').length());

            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

            FileTime lastModifiedFileTime = attr.lastModifiedTime();
            LocalDateTime lastModified = LocalDateTime.ofInstant( lastModifiedFileTime.toInstant(), ZoneId.systemDefault());

            FileTime creationFileTime = attr.creationTime();
            LocalDateTime created = LocalDateTime.ofInstant(creationFileTime.toInstant(), ZoneId.systemDefault());

            ModelId modelId = new ModelId(idPart);
            ModelURN modelURN = parentUrn.resolve(modelId);

            MetaData metaData = new MetaData();

            metaData.setType(typePart);
            metaData.setCreatedTimeStamp(created);
            metaData.setLastModifiedTimeStamp(lastModified);
            metaData.setModelId(modelId);
            metaData.setModelURN(modelURN);

            return metaData;
        } catch (IOException e) {
            throw new ModelException(String.format("error getting metadata for path =%s", path), e);
        }
    }

    Path getPath(ModelURN urn, ModelFormat format, Class<? extends Model> modelClass) {
        String typeName = getTypeName(modelClass);
        ModelURN parentUrn = urn.getParentURN();
        ModelId modelId = urn.getLastFragment();
        Path path = getParentPath(parentUrn).resolve(getFileName(modelId, format, typeName));
        return path;
    }

    Path getParentPath(ModelURN parentURN) {
        Path path = baseDir;
        for (ModelId pathSegment : parentURN.getFragments()) {
            path = path.resolve(pathSegment.getId());
        }
        return path;
    }

    String getTypeName(Class<? extends Model> modelClass) {
        return modelClass.getSimpleName().toLowerCase();
    }

    String getFileName(ModelId modelId, ModelFormat format, String typeName) {
        return modelId.getId() + '-' + typeName + "." + format.getFileExtension();
    }

}
