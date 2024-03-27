package com.book.management.ServiceImpl;

import com.book.management.Dto.FileUpdateDto;
import com.book.management.Dto.FileUploadDto;
import com.book.management.Exception.FileNotFoundException;
import com.book.management.Exception.UserNotFoundException;
import com.book.management.Model.Access;
import com.book.management.Model.Category;
import com.book.management.Model.File;
import com.book.management.Model.Publisher;
import com.book.management.Repository.FileRepository;
import com.book.management.Repository.PublisherRepository;
import com.book.management.Service.FileService;
import org.hibernate.query.Page;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private Path rootpath;
    private final FileRepository fileRepository;

    private final PublisherRepository publisherRepository;

    private final SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, PublisherRepository publisherRepository) {
        this.fileRepository = fileRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public String uploadFile(FileUploadDto fileUploadDto) throws IOException {
        Path folderPath = rootpath.resolve(fileUploadDto.getCategory());
        if (!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String filename = fileUploadDto.getFilename();
        Path filePath = folderPath.resolve(filename);
        //save file to system
        try(InputStream inputStream = fileUploadDto.getMultipartFile().getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioException){
            throw new IOException("File Not Uploaded");
        }

        Publisher publisher = publisherRepository.findById(fileUploadDto.getPublisherID()).orElseThrow(()->new UserNotFoundException("Publisher Not Found"));
        //save file info to database
        File file = File.builder()
                .filename(filename)
                .size(toMB(fileUploadDto.getMultipartFile().getSize()))
                .fileUri(filePath.toString())
                .uploadDate(simpleDateFormat.format(new Date()))
                .access(Access.valueOf(fileUploadDto.getAccess()))
                .category(Category.valueOf(fileUploadDto.getCategory()))
                .publisher(publisher)
                .author(fileUploadDto.getAuthor())
                .build();
        fileRepository.save(file);
        return "File successfully uploaded";
    }

    @Override
    public Resource downloadFile(Long fileid) throws MalformedURLException {
        File file = fileRepository.findById(fileid).orElseThrow(()->new FileNotFoundException("File Not Found"));

        Path resourcePath = Paths.get(file.getFileUri());

        return new UrlResource(resourcePath.toUri());
    }

    @Override
    public ResponseEntity<?> deleteFile(Long fileid) throws IOException {
        Optional<File> optionalFile = fileRepository.findById(fileid);
        if (optionalFile.isEmpty()){
            return new ResponseEntity<>(new FileNotFoundException("File Not Found"), HttpStatusCode.valueOf(404));
        }

        var file = optionalFile.get();

        Path filePath = Path.of(file.getFileUri());
        Files.delete(filePath);

        fileRepository.delete(file);

        return new ResponseEntity<>("File Successfully Deleted", HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> getBookByAuthor(String author) {
        List<File> filesByAuthor = fileRepository.findByAuthor(author);
        return new ResponseEntity<>(filesByAuthor,HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> getByPublisher(String publisher) {
        Long publisherID = publisherRepository.findIdByName(publisher);
        List<File> booksByPublisher = fileRepository.findBooksByPublisherId(publisherID);
        if (booksByPublisher!= null) {
            return new ResponseEntity<>(booksByPublisher, HttpStatusCode.valueOf(200));
        }else {
            return new ResponseEntity<>(new NullPointerException("Books Not Found"), HttpStatusCode.valueOf(200));
        }
    }

    @Override
    public ResponseEntity<?> getFilesByFilename(String filename) {
        List<File> filesByName = fileRepository.findFilesByName(filename);
        return new ResponseEntity<>(filesByName, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> updateFileDetails(FileUpdateDto fileUpdateDto, Long fileid) {
        Optional<File> optionalFile = fileRepository.findById(fileid);
        if (optionalFile.isEmpty()){
            return new ResponseEntity<>(new FileNotFoundException("File Not Found"), HttpStatusCode.valueOf(404));
        }
        File file = optionalFile.get();

        file.setAccess(Access.valueOf(fileUpdateDto.getAccess()));
        file.setCategory(Category.valueOf(fileUpdateDto.getCategory()));
        file.setFilename(fileUpdateDto.getFilename());
        file.setAuthor(fileUpdateDto.getAuthor());

        fileRepository.save(file);

        return new ResponseEntity<>("File Details Update Complete", HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<?> getByCategory(String category) {
        List<File> filesByCategory = fileRepository.findByCategory(category, Page.first(4));
        if (filesByCategory.isEmpty()){
            return new ResponseEntity<>(new NullPointerException(), HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<>(filesByCategory, HttpStatusCode.valueOf(200));
    }

    public String toMB(long number){
        long divisor = 1024 *1024;
        double results = ((double) number / divisor);
        return String.format("%.2f mb", results);
    }


}
