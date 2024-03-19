package com.book.management.Controller;

import com.book.management.Model.File;
import com.book.management.Service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/all-files")
    public ResponseEntity<List<File>> getAllFiles(){
        List<File> responseBody = fileService.getAllFiles();
        return new ResponseEntity<>(responseBody, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String responseBody = fileService.uploadFile(multipartFile);
        return new ResponseEntity<>(responseBody, HttpStatusCode.valueOf(200));
    }

    @GetMapping("download-file/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) throws MalformedURLException {
        Resource resource = fileService.downloadFile(filename);


        String contentType = "application/octet-stream";
        String headerValue = "inline; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
