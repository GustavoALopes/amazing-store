package com.developerjorney.application.product.dtos.viewmodel;

import com.developerjorney.application.dtos.base.ResponseBase;
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
