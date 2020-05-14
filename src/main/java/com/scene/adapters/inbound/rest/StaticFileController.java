package com.scene.adapters.inbound.rest;

import com.scene.application.FileApplicationService;
import com.scene.domain.file.StaticFileService;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class StaticFileController {
    @Autowired
    public FileApplicationService staticFileService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = {"/upload"}, consumes = {"multipart/form-data"})
    public StaticFileService.UploadFileResult uploadFile(@RequestParam("file") MultipartFile file) throws IOException, MimeTypeException {
        return staticFileService.uploadFile(file.getBytes());
    }
}
