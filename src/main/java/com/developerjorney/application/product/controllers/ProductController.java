package com.developerjorney.application.product.controllers;

import com.developerjorney.application.base.controllers.BaseController;
import com.developerjorney.application.base.dtos.ResponseBase;
import com.developerjorney.application.base.dtos.views.ErrorsNotificationsViewModel;
import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.application.product.dtos.inputs.CreateProductInputModel;
import com.developerjorney.application.product.dtos.views.ProductListViewModel;
import com.developerjorney.application.product.queries.interfaces.IProductQuery;
import com.developerjorney.application.product.usecases.CreateProductUseCase;
import com.developerjorney.core.patterns.notification.interfaces.INotificationSubscriber;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiVersions.V1 +  "/products")
public class ProductController extends BaseController {

    private final CreateProductUseCase createProductUseCase;

    private final IProductQuery query;

    public ProductController(
            final INotificationSubscriber subscriber,
            final CreateProductUseCase createProductUseCase,
            final IProductQuery query
    ) {
        super(subscriber);
        this.createProductUseCase = createProductUseCase;
        this.query = query;
    }

    @GetMapping
    public ResponseEntity<ProductListViewModel> getListProduct(
            final @PageableDefault(size = 20) Pageable page
    ) {
        final var result = this.query.getListProduct(page);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ResponseBase> createProduct(
        final @RequestBody CreateProductInputModel inputModel
    ) {
        final var result = this.createProductUseCase.execute(inputModel);
        if(!result) {
            final var view = ErrorsNotificationsViewModel.create(this.getNotifications());
            return ResponseEntity.badRequest().body(view);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
