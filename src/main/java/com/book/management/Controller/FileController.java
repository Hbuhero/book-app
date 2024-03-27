package com.book.management.Controller;

import com.book.management.Dto.FileUploadDto;
import com.book.management.Model.File;
import com.book.management.Service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.book.management.Dto.FileUpdateDto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //Apply pagination and sorting
    @GetMapping("/all-files")
    public ResponseEntity<List<File>> getAllFiles(){
        List<File> responseBody = fileService.getAllFiles();
        return new ResponseEntity<>(responseBody, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/file/")
    public ResponseEntity<?> getFileByFileName(@RequestParam String filename){
        return fileService.getFilesByFilename(filename);
    }


    //TO-DO shift these to publisher using ID {upload, delete, update}
    @PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(@ModelAttribute FileUploadDto fileUploadDto) throws IOException {
        String responseBody = fileService.uploadFile(fileUploadDto);
        return new ResponseEntity<>(responseBody, HttpStatusCode.valueOf(200));
    }

    @GetMapping("download-file/{fileid}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileid) throws MalformedURLException {
        Resource resource = fileService.downloadFile(fileid);


        String contentType = "application/octet-stream";
        String headerValue = "inline; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @DeleteMapping("/delete/{fileid}")
    public ResponseEntity<?> deleteFile(@PathVariable Long fileid) throws IOException {
        return fileService.deleteFile(fileid);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBookByAuthor(@PathVariable String author){
        return fileService.getBookByAuthor(author);
    }

    @GetMapping("/publisher/{publisher}")
    public ResponseEntity<?> getBooksByPublisher(@PathVariable String publisher){
        return fileService.getByPublisher(publisher);
    }

    @PostMapping("/update-details/{fileid}")
    public ResponseEntity<?> updateFileDetails(@RequestBody FileUpdateDto fileUpdateDto, @PathVariable Long fileid){
        return fileService.updateFileDetails(fileUpdateDto, fileid);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getBooksByCategory(@PathVariable String category){
        return fileService.getByCategory(category);
    }

}
