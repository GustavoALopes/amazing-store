package com.developerjorney.application.product.dtos.views;

import com.developerjorney.application.base.dtos.ResponseBase;
import com.developerjorney.core.patterns.notification.interfaces.INotification;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreatedProductViewModel extends ResponseBase<CreatedProductViewModel> {

    public CreatedProductViewModel(
            final CreatedProductViewModel data,
            final Set<INotification> messages
    ) {
        super(data, messages);
    }
}
