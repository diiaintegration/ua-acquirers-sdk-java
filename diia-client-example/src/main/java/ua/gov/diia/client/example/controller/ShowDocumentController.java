package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Controller
public class ShowDocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowDocumentController.class);

    @Value("${documents.base.dir}")
    private String documentsBaseDir;

    @GetMapping("/show-document/{requestId}")
    public ResponseEntity<byte[]> showDocument(@PathVariable(name = "requestId") String requestId) {
        return showFile(requestId, "pdf", MediaType.APPLICATION_PDF, false);
    }

    @GetMapping("/show-auth/{requestIdHash}")
    public ResponseEntity<byte[]> showAuth(@PathVariable(name = "requestIdHash") String requestIdHash) {
        return showFile(requestIdHash, "p7s", MediaType.APPLICATION_OCTET_STREAM, true);
    }

    @GetMapping("/show-signed/{requestId}")
    public ResponseEntity<byte[]> showSignedHashes(@PathVariable(name = "requestId") String requestId) {
        return showFile(requestId, "zip", MediaType.APPLICATION_OCTET_STREAM, true);
    }

    private ResponseEntity<byte[]> showFile(String requestId, String fileSuffix, MediaType mediaType, boolean attachment) {
        byte[] contents;
        String fileName;
        try {
            Path dirPath = findPackageDirectory(requestId);

            Path filePath = findFileWithSuffix(dirPath, fileSuffix);
            fileName = filePath.getFileName().toString();

            // read file and return it
            contents = Files.readAllBytes(filePath);

        } catch (Exception e) {
            LOGGER.error("Can't find document by requestId={}", requestId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // return file
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        if (attachment) {
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
        }
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    private Path findFileWithSuffix(Path dirPath, String fileSuffix) throws IOException {
        Path filePath;
        try (Stream<Path> filePathStream = Files.list(dirPath)) {
            filePath = filePathStream.filter(p -> p.getFileName().toString().toLowerCase().endsWith(fileSuffix))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Can't find pdf file by requestId " + dirPath.getFileName()));
        }
        return filePath;
    }

    private Path findPackageDirectory(String requestId) throws IOException {
        Path dirPath;
        try (Stream<Path> dirPathStream = Files.list(Paths.get(documentsBaseDir))) {
            dirPath = dirPathStream.filter(p -> p.getFileName().toString().contains(requestId))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Can't find dir by requestId " + requestId));
        }
        return dirPath;
    }
}
