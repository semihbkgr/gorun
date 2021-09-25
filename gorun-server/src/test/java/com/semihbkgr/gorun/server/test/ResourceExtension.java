package com.semihbkgr.gorun.server.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class ResourceExtension implements BeforeAllCallback {

    private static final String DEFAULT_TEST_RESOURCE_ROOT_DIR = "./test-files";

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        var rootDirPath = Path.of(DEFAULT_TEST_RESOURCE_ROOT_DIR);
        var optionalTest = extensionContext.getTestClass();
        if (optionalTest.isPresent()) {
            var resourcesDir = optionalTest.get().getDeclaredAnnotation(ResourcesDir.class);
            if (resourcesDir != null) {
                rootDirPath = Path.of(resourcesDir.path());
            }
        }
        log.info("Resources root dir path: {}", rootDirPath.toString());
        Files.walk(rootDirPath).parallel().forEach(path -> {
            if (!Files.isDirectory(path)) {
                try {
                    final var filename = path.getFileName().toString();
                    if (Resources.nameContentResourceMap.containsKey(filename))
                        throw new IllegalStateException("File already exists in resources, filename: " + filename);
                    else
                        Resources.nameContentResourceMap.put(filename, Files.readString(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        log.info("Resources loaded successfully, size: {}", Resources.nameContentResourceMap.size());
    }


}
