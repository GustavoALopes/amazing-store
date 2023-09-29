package com.developerjorney.application.product.dtos.views;

import com.developerjorney.application.base.dtos.ResponseBase;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import com.developerjorney.domain.product.entities.Product;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@Getter
public class ProductListViewModel extends ResponseBase<Page<ProductViewModel>> {

    public ProductListViewModel() {
        this(null, new HashSet<>());
    }

    private ProductListViewModel(
        final Page<ProductViewModel> data,
        final Set<INotification> messages
    ) {
        super(data, messages);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<ProductViewModel> products;

        private Set<INotification> notifications;

        public Builder() {
            this.products = new ArrayList<>();
            this.notifications = new HashSet<>();
        }

        public Builder addProduct(final ProductViewModel product) {
            this.products.add(product);
            return this;
        }

        public Builder addAllProduct(final List<ProductViewModel> products) {
            this.products = products;
            return this;
        }

        public Builder addNotification(final INotification notification) {
            this.notifications.add(notification);
            return this;
        }

        public Builder addAllNotification(final Set<INotification> notifications) {
            this.notifications = notifications;
            return this;
        }

        public ProductListViewModel build() {
            return new ProductListViewModel(
                    new PageImpl<>(this.products, PageRequest.of(0, 20), this.products.size()),
                    this.notifications
            );
        }
    }

    public static ProductListViewModel create(
            final Page<Product> page
    )  {
        return new ProductListViewModel(
                page.map(ProductViewModel::create),
                Collections.emptySet()
        );
    }
}
