package com.developerjorney.application.client.controllers;

import com.developerjorney.application.base.dtos.DefaultResponse;
import com.developerjorney.application.client.controllers.ClientController;
import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.configurations.NotificationPublisherConfig;
import com.developerjorney.core.patterns.notification.NotificationPublisherInMemory;
import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.domain.client.entities.validations.ImportClientDomainInputValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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
import org.testcontainers.shaded.com.google.common.base.Functions;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @MockBean
    private INotificationSubscriber notificationSubscriber;

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

    @Test
    public void shouldNotifyWhenTryCreateInvalidProduct() throws Exception {
        //Inputs
        final var invalidInput = new ImportClientInput(
                null,
                null,
                null
        );

        final Set<INotification> notifications = Set.of(
                new Notification(
                        NotificationTypeEnum.ERROR,
                        ImportClientDomainInputValidation.NAME_IS_REQUIRED,
                        "Nome obrigatório"
                ),
                new Notification(
                        NotificationTypeEnum.ERROR,
                        ImportClientDomainInputValidation.LAST_NAME_IS_REQUIRED,
                        "Sobrenome obrigatório"
                ),
                new Notification(
                        NotificationTypeEnum.ERROR,
                        ImportClientDomainInputValidation.BIRTHDATE_IS_REQUIRED,
                        "Data de nascimento obrigatória"
                )
        );

        //Mock
        BDDMockito.given(this.importClientUseCase.execute(invalidInput))
                .willReturn(false);

        BDDMockito.given(this.notificationSubscriber.getNotifications())
                .willReturn(notifications);

        //Execution
        final var payload = this.mapper.writeValueAsString(invalidInput);
        final var request = MockMvcRequestBuilders.post(IMPORT_CLIENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        final var httpResponse = this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").isNotEmpty())
                .andReturn();

        final List<INotification> response = JsonPath.read(
                httpResponse.getResponse().getContentAsString(),
                "$.messages"
        );

        Assertions.assertThat(response.size()).isEqualTo(notifications.size());
//        Assertions.assertThat(response.get(0).type()).isEqualTo(ImportClientDomainInputValidation.NAME_IS_REQUIRED);
//        Assertions.assertThat(response.get(1).type()).isEqualTo(ImportClientDomainInputValidation.LAST_NAME_IS_REQUIRED);
//        Assertions.assertThat(response.get(2).type()).isEqualTo(ImportClientDomainInputValidation.BIRTHDATE_IS_REQUIRED);
////
//        final var notificationMap = notifications.stream().collect(Collectors.toMap(
//                key -> key.code(),
//                Function.identity()
//        ));
//
//        response.forEach(notification -> {
//            Assertions.assertThat(
//                    notificationMap.containsKey(notification.code())
//            ).isTrue();
//        });

    }
}
