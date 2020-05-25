package com.scene.domain.file;

import java.io.IOException;
import java.io.InputStream;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import org.apache.tika.io.IOUtils;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTests {

    private byte[] data;
    @Mock
    private FileRepo fileRepo;
    @Mock
    private FileTypeRepo fileTypeRepo;
    @InjectMocks
    private FileService fileService;

    @Before
    public void setup_data() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/test-fixtures/fixture-1.jpg");
        data = IOUtils.toByteArray(resourceAsStream);
    }

    @Test
    public void should_upload_file_and_generate_file_id() {
        Mockito.when(fileTypeRepo.getWhitelist()).thenReturn(asList("image/jpeg", "image/png"));

        File file = fileService.upload(data);

        Mockito.verify(fileRepo).save(file);
        assertNotNull(file.getId());
        assertThat(file.getContent(), is(data));
    }

    @Test(expected = InvalidFileTypeException.class)
    public void should_check_file_type() {
        Mockito.when(fileTypeRepo.getWhitelist()).thenReturn(singletonList("image/png"));
        fileService.upload(data);
    }

}

