package com.scene.domain.file;

import org.apache.tika.io.IOUtils;
import org.apache.tika.mime.MimeTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class StaticFileServiceTest {

    private static final String TEST_FILE_PATH = "/static-file/1.jpeg";

    @Mock
    private StorageClient storageClient;

    @Mock
    private FileTypeRepo fileTypeRepo;

    @InjectMocks
    private StaticFileService staticFileService;

    @Test
    public void should_upload_file_and_generate_file_name() throws IOException, MimeTypeException {
        Mockito.when(storageClient.getFilePath(anyString())).thenReturn(TEST_FILE_PATH);
        Mockito.when(fileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));

        byte[] data = getBytesFromResource("/static-file/category.jpg");
        StaticFileService.UploadFileResult uploadFileResult = staticFileService.uploadFile(data);

        Mockito.verify(storageClient).uploadFile(eq(data), any(StaticFileInfo.class));
        assertTrue(uploadFileResult.getFileName().endsWith(".jpg"));
        assertThat(uploadFileResult.getFilePath(), is(TEST_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void should_upload_file_and_generate_file_name_fail() throws IOException, MimeTypeException {

        Mockito.when(fileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));
        Mockito.doThrow(new RuntimeException()).when(storageClient).uploadFile(any(), any());
        byte[] data = getBytesFromResource("/static-file/category.jpg");

        staticFileService.uploadFile(data);
    }

    @Test(expected = InvalidFileTypeException.class)
    public void should_upload_file_check_file_type_invalid() throws MimeTypeException {

        Mockito.when(fileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));
        byte[] data = "test type err file".getBytes();
        staticFileService.uploadFile(data);
    }

    private byte[] getBytesFromResource(String resourcePath) throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream(resourcePath);
        return IOUtils.toByteArray(resourceAsStream);
    }

}

