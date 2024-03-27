package com.book.management.Service;

import com.book.management.Dto.FileUpdateDto;
import com.book.management.Dto.FileUploadDto;
import com.book.management.Model.File;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileService {
    List<File> getAllFiles();

    String uploadFile(FileUploadDto fileUploadDto) throws IOException;

    Resource downloadFile(Long fileid) throws MalformedURLException;

    ResponseEntity<?> deleteFile(Long fileid) throws IOException;

    ResponseEntity<?> getBookByAuthor(String author);

    ResponseEntity<?> getByPublisher(String publisher);

    ResponseEntity<?> getFilesByFilename(String filename);

    ResponseEntity<?> updateFileDetails(FileUpdateDto fileUploadDto, Long fileid);

    ResponseEntity<?> getByCategory(String category);
}
