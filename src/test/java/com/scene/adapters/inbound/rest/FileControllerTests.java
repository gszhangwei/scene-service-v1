package com.scene.adapters.inbound.rest;

import com.scene.application.FileApplicationService;
import com.scene.domain.file.File;
import com.scene.domain.file.InvalidFileTypeException;
import static java.util.UUID.randomUUID;
import static org.apache.tika.io.IOUtils.toByteArray;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileApplicationService fileApplicationService;

    @Test
    public final void should_upload_file_successfully() throws Exception {
        byte[] bytes = toByteArray(this.getClass().getResourceAsStream("/test-fixtures/fixture-1.jpg"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "fixture-1.jpeg", "image/jpeg", bytes);
        File file = new File(randomUUID(), bytes);
        when(fileApplicationService.upload(bytes)).thenReturn(file);
        this.mockMvc.perform(multipart("/api/v1/files")
                .file(mockFile))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$", is(file.getId().toString())));
    }

    @Test
    public final void should_upload_file_check_file_type_invalid() throws Exception{
        byte[] bytes = toByteArray(this.getClass().getResourceAsStream("/test-fixtures/fixture-1.jpg"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "fixture-1.jpeg", "image/jpeg", bytes);
        when(fileApplicationService.upload(bytes)).thenThrow(new InvalidFileTypeException());
        this.mockMvc.perform(multipart("/api/v1/files")
                .file(mockFile))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.errCode", is(ErrorCode.INVALID_FILE_TYPE.getCode())))
                .andExpect(jsonPath("$.errMsg", is(ErrorCode.INVALID_FILE_TYPE.getMessage())));
    }
}
