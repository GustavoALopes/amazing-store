package com.developerjorney.application.client.controllers;

import com.developerjorney.BaseTest;
import com.developerjorney.application.base.dtos.PageableResponse;
import com.developerjorney.application.client.dtos.input.CreateAddressInput;
import com.developerjorney.application.client.dtos.input.GetAllClientsInput;
import com.developerjorney.application.client.dtos.input.ImportClientInput;
import com.developerjorney.application.client.dtos.view.ClientReportView;
import com.developerjorney.application.client.queries.ClientQuery;
import com.developerjorney.application.client.usecases.CreateAddressUseCase;
import com.developerjorney.application.client.usecases.ImportClientUseCase;
import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.application.exceptions.GlobalExceptionHandler;
import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.developerjorney.core.patterns.result.ProcessResult;
import com.developerjorney.domain.client.entities.validations.CreateAddressDomainInputValidation;
import com.developerjorney.domain.client.entities.validations.ImportClientDomainInputValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ClientController.class
})
public class ClientControllerTest {

    private static final String URL = ApiVersions.V1 + "/clients";

    private static final String IMPORT_CLIENT = URL + "/import";

    private static final String CREATE_CLIENT_ADDRESS = URL + "/{clientId}/address";

    @Autowired
    private ClientController controller;

    @MockBean
    private ClientQuery query;

    @MockBean
    private ImportClientUseCase importClientUseCase;

    @MockBean
    private CreateAddressUseCase createAddressUseCase;

    @MockBean
    private INotificationSubscriber notificationSubscriber;

    private MockMvc mockMvc;

    private final ObjectMapper mapper;

    private final BaseTest baseTest;

    public ClientControllerTest() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        this.baseTest = new BaseTest();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void shouldImportClient() throws Exception {
        //Inputs
        final var input = new ImportClientInput(
                "Cliente",
                "A",
                "email@test.com",
                LocalDate.parse("2000-12-01"),
                null
        );
        final var client = this.baseTest.makeClient(input);

        //Mock
        BDDMockito.given(this.importClientUseCase.execute(Mockito.eq(input)))
                .willReturn(ProcessResult.success(client));

        //Execution
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.post(IMPORT_CLIENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    public void shouldNotifyWhenTryCreateInvalidClient() throws Exception {
        //Inputs
        final var invalidInput = new ImportClientInput(
                "nomeXPTO",
                "lastNameXPTO",
                "emailXPTO",
                LocalDate.parse("2000-12-01"),
                null
        );

        final var message = "Some test notification";

        final Set<INotification> notifications = Set.of(
                new Notification(
                        NotificationTypeEnum.ERROR,
                        ImportClientDomainInputValidation.NAME_IS_REQUIRED,
                        message
                )
        );

        //Mock
        BDDMockito.given(this.importClientUseCase.execute(invalidInput))
                .willReturn(ProcessResult.fail());

        BDDMockito.given(this.notificationSubscriber.getNotifications())
                .willReturn(notifications);

        //Execution
        final var payload = this.mapper.writeValueAsString(invalidInput);
        final var request = MockMvcRequestBuilders.post(IMPORT_CLIENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].type").value(NotificationTypeEnum.ERROR.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].code").value(ImportClientDomainInputValidation.NAME_IS_REQUIRED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].message").value(message));
    }

    @Test
    public void shouldGetClientReport() throws Exception {
        //Input
        final var input = new GetAllClientsInput(
                "Client",
                "A",
                "email@test.com",
                LocalDate.parse("2000-12-01"),
                LocalDate.parse("2001-12-01")
        );

        final var viewModel = PageableResponse.create(
                0,
                1,
                20,
                1,
                Set.of(ClientReportView.create(
                        UUID.randomUUID(),
                        "Cliente",
                        "A",
                        "2000-12-01",
                        "email@test.com"
                ))
        );

        //Mock
        BDDMockito.given(this.query.listAll(
            Mockito.any(GetAllClientsInput.class),
            Mockito.any(Pageable.class)
        ))
            .willReturn(viewModel);

        //Execution
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        final var client = viewModel.getContent().stream().findFirst().orElse(null);
        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].name").value(client.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].lastName").value(client.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].birthdate").value(client.getBirthdate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].email").value(client.getEmail()));

    }

    //Address
    @Test
    public void shouldNotifyOkIfCreatedAddressWithSuccess() throws Exception {
        //Input
        final var input = new CreateAddressInput(
                "BR",
                "RJ",
                "Blumenau",
                "Itoupava Norte",
                "R. dois de setembro",
                "832",
                "89052-000",
                "Proximo a Chevrolet"
        );

        final var clientId = UUID.randomUUID();
        final var useCaseParams = Pair.with(
                clientId,
                input
        );

        //Mock
        BDDMockito.given(this.createAddressUseCase.execute(useCaseParams))
                .willReturn(true);

        //Execute
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.post(CREATE_CLIENT_ADDRESS, clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldNotifyBadRequestWhenExistsSomeProblem() throws Exception {
        //Input
        final var input = new CreateAddressInput(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        final var clientId = UUID.randomUUID();
        final var useCaseParams = Pair.with(
                clientId,
                input
        );

        //Mock
        BDDMockito.given(this.createAddressUseCase.execute(useCaseParams))
                .willReturn(false);

        final Set<INotification> notifications = Set.of(
                new Notification(
                        NotificationTypeEnum.ERROR,
                        CreateAddressDomainInputValidation.CITY_IS_REQUIRED_CODE,
                        "Some test notification"
                )
        );

        BDDMockito.given(this.notificationSubscriber.getNotifications())
                .willReturn(notifications);

        //Execute
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.post(CREATE_CLIENT_ADDRESS, clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").isNotEmpty());
    }
}
