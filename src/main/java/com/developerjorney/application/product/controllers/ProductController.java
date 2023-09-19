package com.developerjorney.application.product.controllers;

import com.developerjorney.application.enums.ApiVersions;
import com.developerjorney.application.product.dtos.viewmodel.ProductListViewModel;
import com.developerjorney.application.product.queries.interfaces.IProductQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiVersions.V1 +  "/products")
public class ProductController {

    private final IProductQuery query;

    public ProductController(final IProductQuery query) {
        this.query = query;
    }

    @GetMapping
    public ResponseEntity<ProductListViewModel> getListProduct(
            final @PageableDefault(size = 20) Pageable page
    ) {
        final var result = this.query.getListProduct(page);
        return ResponseEntity.ok(result);
    }
}
