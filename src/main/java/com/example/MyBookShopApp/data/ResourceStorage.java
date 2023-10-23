package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.Entity.BookFile;
import com.example.MyBookShopApp.data.Repository.BookFileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class ResourceStorage {
    @Value("${upload.path}")
    String uploadPath;
    @Value("${download.path}")
    String downloadPath;
    private final BookFileRepository bookFileRepository;

    public ResourceStorage(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }

    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {
        String resourceURI = null;
        if (!new File(uploadPath).exists()) {
            Files.createDirectories(Paths.get(uploadPath));
            Logger.getLogger(this.getClass().getSimpleName()).info("created file path" + uploadPath);
        }
        String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path path = Paths.get(uploadPath, fileName);
        resourceURI = "/img/" + fileName;
        file.transferTo(path);// uploading file
        Logger.getLogger(this.getClass().getSimpleName()).info(fileName + "uploaded file OK! ");
        return resourceURI;
    }

    public byte[] getBookFileByArray(String hash) throws IOException {
        Path path = Paths.get(downloadPath, bookFileRepository.findBookFileByHash(hash).getPath());
        return Files.readAllBytes(path);

    }

    public MediaType getBookFileMide(String hash) {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        String mimeType = URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        if (mimeType != null) {
            return MediaType.parseMediaType(mimeType);
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public Path getBookFilePath(String hash) {
        return Paths.get(bookFileRepository.findBookFileByHash(hash).getPath());
    }
}
