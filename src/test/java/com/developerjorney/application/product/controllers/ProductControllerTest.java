package com.developerjorney.application.product.controllers;

import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.application.product.dtos.inputs.CreateProductInputModel;
import com.developerjorney.application.product.dtos.views.ProductListViewModel;
import com.developerjorney.application.product.dtos.views.ProductViewModel;
import com.developerjorney.application.product.queries.interfaces.IProductQuery;
import com.developerjorney.application.product.usecases.CreateProductUseCase;
import com.developerjorney.configurations.NotificationPublisherConfig;
import com.developerjorney.core.patterns.notification.NotificationPublisherInMemory;
import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.patterns.notification.enums.NotificationTypeEnum;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import com.developerjorney.core.patterns.notification.models.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Set;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProductController.class
})
public class ProductControllerTest {

    private static final String URI = ApiVersions.V1 + "/products";

    @Autowired
    private ProductController controller;

    @MockBean
    private INotificationSubscriber subscriber;

    @MockBean
    private IProductQuery query;

    @MockBean
    private CreateProductUseCase createProductUseCase;

    private MockMvc mockMvc;

    private final ObjectMapper mapper;

    public ProductControllerTest() {
        this.mapper = new ObjectMapper();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void shouldGetProductList() throws Exception {
        //Inputs
        final var viewModel = ProductListViewModel.builder()
                .addProduct(ProductViewModel.create("XPTO", "description XPTO"))
                .build();

        //Mock
        BDDMockito.given(this.query.getListProduct(PageRequest.of(0, 20)))
                .willReturn(viewModel);

        //Request
        final var payload = this.mapper.writeValueAsString(viewModel);
        final var request = MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        final var productViewModel = viewModel.getData().stream().findFirst().orElse(null);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].code").value(productViewModel.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].description").value(productViewModel.getDescription()));
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        //Inputs
        final var input = new CreateProductInputModel(
                "CODE XPTO",
                "Descrpiton XPTO"
        );

        //Mock
        BDDMockito.given(this.createProductUseCase.execute(
                Mockito.eq(input)
        )).willReturn(true);

        //Execute
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldReturnErrorsMessagesWhenTryCreateProduct() throws Exception {
        //Inputs
        final var input = new CreateProductInputModel(
                "",
                ""
        );
        final var notification = new Notification(
                NotificationTypeEnum.ERROR,
                "XPTO",
                "Message XPTO"
        );

        //Mock
        BDDMockito.given(this.subscriber.getNotifications())
            .willReturn(Set.of(notification));
        BDDMockito.given(this.createProductUseCase.execute(
                Mockito.eq(input)
        )).willReturn(false);

        //Execute
        final var payload = this.mapper.writeValueAsString(input);
        final var request = MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        this.mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].type").value(notification.type().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].code").value(notification.code()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].message").value(notification.message()));
    }
}
