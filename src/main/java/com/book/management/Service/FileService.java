package com.book.management.Service;

import com.book.management.Model.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileService {
    List<File> getAllFiles();

    String uploadFile(MultipartFile multipartFile) throws IOException;

    Resource downloadFile(String filename) throws MalformedURLException;
}
