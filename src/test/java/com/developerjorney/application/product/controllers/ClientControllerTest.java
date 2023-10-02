package com.developerjorney.application.product.controllers;

import com.developerjorney.application.client.controllers.ClientController;
import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ClientController.class
})
public class ClientControllerTest {

    private static final String URL = ApiVersions.V1 + "/clients";

    private static final String IMPORT_CLIENT = URL + "/import";
    @Autowired
    private ClientController controller;

    @MockBean
    private ImportClientUseCase importClientUseCase;

    private MockMvc mockMvc;

    private final ObjectMapper mapper;

    public ClientControllerTest() {
        this.mapper = new ObjectMapper();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
                .build();
    }

    @Test
    public void shouldImportClient() throws Exception {
        //Inputs
        final var input = new ImportClientInput(
                "Cliente",
                "A",
                "2000-12-01"
        );

        //Mock
        BDDMockito.given(this.importClientUseCase.execute(Mockito.eq(input)))
                .willReturn(true);

        //Execution
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.post(IMPORT_CLIENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
