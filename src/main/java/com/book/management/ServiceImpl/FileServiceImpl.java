package com.book.management.ServiceImpl;

import com.book.management.Exception.FileNotFoundException;
import com.book.management.Model.File;
import com.book.management.Repository.FileRepository;
import com.book.management.Service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private Path rootpath;
    private final FileRepository fileRepository;

    private final SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        Path filePath = rootpath.resolve(filename);
        //save file to system
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioException){
            throw new IOException("File Not Uploaded");
        }

        //save file info to database
        File file = File.builder()
                .filename(filename)
                .size(toMB(multipartFile.getSize()))
                .fileUri(filePath.toString())
                .uploadDate(simpleDateFormat.format(new Date()))
                .build();
        fileRepository.save(file);
        return "File successfully uploaded";
    }

    @Override
    public Resource downloadFile(String filename) throws MalformedURLException {
        File file = fileRepository.findByFilename(filename).orElseThrow(()->new FileNotFoundException("File Not Found"));

        Path resourcePath = Paths.get(file.getFileUri());

        return new UrlResource(resourcePath.toUri());
    }

    public String toMB(long number){
        long divisor = 1024 *1024;
        double results = ((double) number / divisor);
        return String.format("%.2f mb", results);
    }


}
