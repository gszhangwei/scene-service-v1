package com.scene.adapters.inbound.rest;

import com.scene.application.FileApplicationService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileController {
    @Autowired
    private FileApplicationService fileApplicationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {"multipart/form-data"})
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileApplicationService.upload(file.getBytes()).getId().toString();
    }
}
