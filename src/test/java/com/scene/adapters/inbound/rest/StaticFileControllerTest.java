package com.scene.adapters.inbound.rest;

import com.scene.application.FileApplicationService;
import com.scene.domain.file.InvalidFileTypeException;
import com.scene.domain.file.StaticFileService;
import org.apache.tika.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(StaticFileController.class)
public class StaticFileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FileApplicationService staticFileService;

    @Test
    public final void should_upload_file_successfully() throws Exception {
        byte[] bytes = IOUtils.toByteArray(this.getClass().getResourceAsStream("/static-file/category.jpg"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "category.jpeg", "image/jpeg", bytes);
        when(staticFileService.uploadFile(bytes)).thenReturn(new StaticFileService.UploadFileResult("1.jpeg", "/static-file/1.jpeg"));
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/static-files/upload")
                .file(mockFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName", Matchers.is("1.jpeg")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filePath", Matchers.is("/static-file/1.jpeg")));
    }

    @Test
    public final void should_upload_file_fail() throws Exception {
        byte[] bytes = IOUtils.toByteArray(this.getClass().getResourceAsStream("/static-file/category.jpg"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "category.jpeg", "image/jpeg", bytes);
        when(staticFileService.uploadFile(bytes)).thenThrow(new RuntimeException());
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/static-files/upload")
                .file(mockFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode", is(ErrorCode.INTERNAL_SERVER_ERROR.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg", is(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())));
    }

    @Test
    public final void should_upload_file_check_file_type_invalid() throws Exception{
        byte[] bytes = IOUtils.toByteArray(this.getClass().getResourceAsStream("/static-file/category.jpg"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "category.jpeg", "image/jpeg", bytes);
        when(staticFileService.uploadFile(bytes)).thenThrow(new InvalidFileTypeException());
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/static-files/upload")
                .file(mockFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode", is(ErrorCode.INVALID_FILE_TYPE.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg", is(ErrorCode.INVALID_FILE_TYPE.getMessage())));
    }
}
