package com.developerjorney.application.product.dtos.viewmodel;

import com.developerjorney.application.dtos.base.ResponseBase;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ProductListViewModel extends ResponseBase<Set<ProductViewModel>> {

    public ProductListViewModel() {
        this(new HashSet<>(), new HashSet<>());
    }

    private ProductListViewModel(
        final Set<ProductViewModel> data
    ) {
        this(data, new HashSet<>());
    }

    private ProductListViewModel(
            final Set<ProductViewModel> data,
            final Set<INotification> messages
    ) {
        super(data, messages);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Set<ProductViewModel> products;

        private Set<INotification> notifications;

        public Builder() {
            this.products = new HashSet<>();
            this.notifications = new HashSet<>();
        }

        public Builder addProduct(final ProductViewModel product) {
            this.products.add(product);
            return this;
        }

        public Builder addAllProduct(final Set<ProductViewModel> products) {
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
                    this.products,
                    this.notifications
            );
        }
    }
}
