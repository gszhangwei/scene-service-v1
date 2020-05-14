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
    private IStorageClient iStorageClient;

    @Mock
    private IFileTypeRepo iFileTypeRepo;

    @InjectMocks
    private StaticFileService staticFileService;

    @Test
    public void should_upload_file_and_generate_file_name() throws IOException, MimeTypeException {
        Mockito.when(iStorageClient.getFilePath(anyString())).thenReturn(TEST_FILE_PATH);
        Mockito.when(iFileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));

        byte[] data = getBytesFromResource("/static-file/category.jpg");
        StaticFileService.UploadFileResult uploadFileResult = staticFileService.uploadFile(data);

        Mockito.verify(iStorageClient).uploadFile(eq(data), any(StaticFileInfo.class));
        assertTrue(uploadFileResult.getFileName().endsWith(".jpg"));
        assertThat(uploadFileResult.getFilePath(), is(TEST_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void should_upload_file_and_generate_file_name_fail() throws IOException, MimeTypeException {

        Mockito.when(iFileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));
        Mockito.doThrow(new RuntimeException()).when(iStorageClient).uploadFile(any(), any());
        byte[] data = getBytesFromResource("/static-file/category.jpg");

        staticFileService.uploadFile(data);
    }

    @Test(expected = InvalidFileTypeException.class)
    public void should_upload_file_check_file_type_invalid() throws MimeTypeException {

        Mockito.when(iFileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));
        byte[] data = "test type err file".getBytes();
        staticFileService.uploadFile(data);
    }

    private byte[] getBytesFromResource(String resourcePath) throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream(resourcePath);
        return IOUtils.toByteArray(resourceAsStream);
    }

}

