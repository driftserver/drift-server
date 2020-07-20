package io.drift.core.metamodel;

public enum ModelFormat {

    YAML("yaml"),
    JSON("json");

    ModelFormat(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    private String fileExtension;

    public String getFileExtension() {
        return fileExtension;
    }

}
