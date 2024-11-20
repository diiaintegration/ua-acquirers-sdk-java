package ua.gov.diia.client.sdk.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class StubsUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ClassLoader classLoader = StubsUtil.class.getClassLoader();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T loadStub(String path, Class<T> clazz) {
        try {
            URL resource = classLoader.getResource(path);
            if (resource == null) throw new FileNotFoundException(path);

            return OBJECT_MAPPER.readValue(new File(resource.getFile()), clazz);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String loadAsString(String path) {
        try {
            URL resource = classLoader.getResource(path);
            if (resource == null) throw new FileNotFoundException(path);
            byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private StubsUtil() {
        throw new UnsupportedOperationException();
    }
}
