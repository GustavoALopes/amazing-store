package com.developerjorney.application.product.usecases;


import com.developerjorney.application.product.dtos.inputs.CreateProductInputModel;
import com.developerjorney.configurations.MockUnitOfWork;
import com.developerjorney.configurations.NotificationPublisherConfig;
import com.developerjorney.core.RequestScopeAttribute;
import com.developerjorney.core.patterns.notification.NotificationPublisherInMemory;
import com.developerjorney.core.patterns.notification.NotificationSubscriber;
import com.developerjorney.core.patterns.notification.interfaces.INotificationPublisher;
import com.developerjorney.core.persistence.unitofwork.UnitOfWork;
import com.developerjorney.domain.product.entities.Product;
import com.developerjorney.domain.product.entities.validations.CreateProductDomainInputValidation;
import com.developerjorney.domain.product.repositories.IProductRepository;
import com.developerjorney.domain.product.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.function.Function;
import java.util.stream.Collectors;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CreateProductUseCase.class,
        ProductService.class,
        UnitOfWork.class,
        MockUnitOfWork.class,
        NotificationPublisherConfig.class,
        NotificationPublisherInMemory.class
})
public class CreateProductUseCaseTest {

    @Autowired
    private CreateProductUseCase useCase;

    @SpyBean
    private INotificationPublisher notificationPublisher;

    @SpyBean
    private NotificationSubscriber notificationSubscriber;

    @MockBean
    private IProductRepository repository;

    @BeforeEach
    public void setup() {
        RequestContextHolder.setRequestAttributes(new RequestScopeAttribute());
    }


    @Test
    public void shouldCreateProduct() {
        //Inputs
        final var input = new CreateProductInputModel(
            "XPTO",
                "Description XPTO"
        );

        //Mock
        Mockito.when(this.repository.persist(Mockito.any(Product.class))).thenReturn(true);

        //Execute
        final var response = this.useCase.execute(input);

        Assertions.assertThat(response).isTrue();

        Mockito.verify(this.repository, Mockito.times(1)).persist(Mockito.any(Product.class));
    }

    @Test
    public void shouldFailAndNotifyReasons() {
        //Inputs
        final var input = new CreateProductInputModel(
                "",
                ""
        );

        //Execute
        final var response = this.useCase.execute(input);

        Assertions.assertThat(response).isFalse();
        Assertions.assertThat(this.notificationSubscriber.getNotifications()).isNotEmpty();

        final var notification = this.notificationSubscriber.getNotifications().stream().collect(Collectors.toMap(
                k -> k.code(),
                Function.identity()
        ));

        Assertions.assertThat(notification.containsKey(CreateProductDomainInputValidation.CREATE_PRODUCT_CODE_VALIDATE_CODE)).isTrue();

        Mockito.verify(this.repository, Mockito.never()).persist(Mockito.any(Product.class));
    }
}
