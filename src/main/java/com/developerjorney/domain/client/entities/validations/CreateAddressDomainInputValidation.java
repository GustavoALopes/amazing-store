package com.developerjorney.domain.client.entities.validations;

import com.developerjorney.core.patterns.validation.BaseValidation;
import com.developerjorney.core.patterns.validation.enums.ValidateTypeEnum;
import com.developerjorney.core.patterns.validation.models.ValidateMessage;
import com.developerjorney.domain.client.entities.inputs.CreateAddressDomainInput;

public class CreateAddressDomainInputValidation extends BaseValidation<CreateAddressDomainInput> {

    private static final String PREFIX = "CREATE_ADDRESS";

    public static final String COUNTRY_IS_REQUIRED_CODE = PREFIX + "_COUNTRY_IS_REQUIRED";

    public static final String COUNTRY_IS_INVALID_CODE = PREFIX + "_COUNTRY_IS_INVALID";

    public static final String STATE_IS_REQUIRED_CODE = PREFIX + "_STATE_IS_REQUIRED";

    public static final String STATE_IS_INVALID_CODE =  PREFIX + "_STATE_IS_INVALID";

    public static final String CITY_IS_REQUIRED_CODE =  PREFIX + "_CITY_IS_REQUIRED";

    public static final String NEIGHBORHOOD_IS_REQUIRED_CODE =  PREFIX + "_NEIGHBORHOOD_IS_REQUIRED";

    public static final String NEIGHBORHOOD_IS_BIGGEST_THAN_MAX_SIZE_CODE =  PREFIX + "_NEIGHBORHOOD_IS_BIGGEST_THAN_MAX_SIZE";

    public static final String ZIP_CODE_IS_REQUIRED_CODE =  PREFIX + "_ZIP_CODE_IS_REQUIRED";

    public static final String ZIP_CODE_IS_INVALID_CODE =  PREFIX + "_ZIP_CODE_IS_INVALID";

    public static final String STREET_IS_REQUIRED_CODE =  PREFIX + "_STREET_IS_REQUIRED";

    public static final String STREET_IS_BIGGEST_THAN_MAX_SIZE_CODE =  PREFIX + "_STREET_IS_BIGGEST_THAN_MAX_SIZE";

    public static final String NUMBER_IS_REQUIRED_CODE =  PREFIX + "_NUMBER_IS_REQUIRED";

    @Override
    public void internalValidate(final CreateAddressDomainInput input) {
        if(this.stringIsNullOrEmpty(input.getCountry())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    COUNTRY_IS_REQUIRED_CODE,
                    "The country of address is required"
            ));
        } else if(input.getCountry().length() > 3 ||
            input.getCountry().length() < 2) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    COUNTRY_IS_INVALID_CODE,
                    "The country of address need be the country code like 'BR', 'USA', 'CH',..."
            ));
        }

        if(this.stringIsNullOrEmpty(input.getState())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    STATE_IS_REQUIRED_CODE,
                    "The state of address is required"
            ));
        } else if(input.getState().length() != 2) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    STATE_IS_INVALID_CODE,
                    "The state of address need be the state code like 'RJ', 'NY'..."
            ));
        }

        if(this.stringIsNullOrEmpty(input.getCity())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    CITY_IS_REQUIRED_CODE,
                    "The city of address is required"
            ));
        }

        if(this.stringIsNullOrEmpty(input.getNeighborhood())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    NEIGHBORHOOD_IS_REQUIRED_CODE,
                    "The neighborhood of address is required"
            ));
        } else if (input.getNeighborhood().length() > 100) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    NEIGHBORHOOD_IS_BIGGEST_THAN_MAX_SIZE_CODE,
                    "The neighborhood of address has a limit of 100 character"
            ));
        }

        if(this.stringIsNullOrEmpty(input.getZipCode())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    ZIP_CODE_IS_REQUIRED_CODE,
                    "The zip code of address is required"
            ));
        } else if (input.getZipCode().length() > 11 ||
                input.getZipCode().length() < 5) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    ZIP_CODE_IS_INVALID_CODE,
                    "The zip code of address need to be between 5 and 11 character"
            ));
        }

        if(this.stringIsNullOrEmpty(input.getStreet())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    STREET_IS_REQUIRED_CODE,
                    "The street of address is required"
            ));
        } else if (input.getStreet().length() > 100) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    STREET_IS_BIGGEST_THAN_MAX_SIZE_CODE,
                    "The street of address has a limit of 100 character"
            ));
        }

        if(this.stringIsNullOrEmpty(input.getNumber())) {
            this.addMessage(ValidateMessage.create(
                    ValidateTypeEnum.ERROR,
                    NUMBER_IS_REQUIRED_CODE,
                    "The number of address is required"
            ));
        }
    }
}
