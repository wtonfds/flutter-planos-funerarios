package br.com.monitoratec.farol.graphql.model.responses.common;

import br.com.monitoratec.farol.auth.exceptions.AuthorizationTokenMissingException;
import br.com.monitoratec.farol.auth.exceptions.AuthorizationTokenUnrecognizedException;
import br.com.monitoratec.farol.auth.exceptions.NonAuthorizedException;
import br.com.monitoratec.farol.graphql.exceptions.MappedFieldErrorValidationToCommonResponse;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.google.gson.annotations.Expose;
import graphql.validation.ValidationError;
import graphql.validation.ValidationErrorType;

import java.util.concurrent.TimeoutException;

public class CommonResponse {
    @Expose
    private Long statusCode;

    @Expose
    private String description;

    @Expose
    private boolean isError;

    public CommonResponse() {
    }

    public CommonResponse(Long statusCode, String description, boolean isError) {
        this.statusCode = statusCode;
        this.description = description;
        this.isError = isError;
    }

    public static CommonResponse buildFromException(Throwable exception) {
        if (exception instanceof AuthorizationTokenMissingException) {
            return StatusCodes.Error.Login.AUTHORIZATION_MISSING;
        } else if (exception instanceof AuthorizationTokenUnrecognizedException) {
            return StatusCodes.Error.Login.AUTHORIZATION_INVALID;
        } else if (exception instanceof NonAuthorizedException) {
            return StatusCodes.Error.Login.NON_AUTHORIZED;
        } else if (exception instanceof TimeoutException) {
            return StatusCodes.Error.Login.REQUEST_TIMED_OUT;
        } else {
            CommonResponse somethingUnexpectedResponse = StatusCodes.Error.Login.SOMETHING_UNEXPECTED_HAPPENED.createCopy();
            somethingUnexpectedResponse.setDescription(somethingUnexpectedResponse.getDescription() + ": " + exception.getMessage());

            return somethingUnexpectedResponse;
        }
    }

    public CommonResponse createCopy() {
        return new CommonResponse(this.statusCode, this.description, this.isError);
    }

    public static CommonResponse fromGraphqlValidationError(ValidationError validationError) {
        if (validationError.getValidationErrorType() == ValidationErrorType.WrongType) {
            String argumentFailed = CommonResponse.getArgumentNameFromValidationErrorString(validationError.getDescription());
            String argumentType = CommonResponse.getFieldTypeFromValidationErrorString(validationError.getDescription());
            String submittedValue = CommonResponse.getSubmittedValueFromValidationErrorString(validationError.getDescription());

            MappedFieldErrorValidationToCommonResponse mappedFieldErrorValidationToCommonResponse = CommonResponse.getMappedField(argumentType);
            CommonResponse commonResponse = StatusCodes.fromMappedFieldErrorValidation(mappedFieldErrorValidationToCommonResponse);

            String parsingWarning = String.format("Impossible to create the argument:{%s}, of type:${%s} with the value:{%s}. ExpectedPattern={%s}", argumentFailed, argumentType, submittedValue,
                    mappedFieldErrorValidationToCommonResponse.fieldExpectedPattern());

            commonResponse.setDescription(commonResponse.getDescription() + " " + parsingWarning);
            return commonResponse;
        }

        return StatusCodes.Error.Login.SOMETHING_UNEXPECTED_HAPPENED;
    }

    private static String getArgumentNameFromValidationErrorString(String description) {
        String cutString = description.substring(description.indexOf("'") + 1);
        return cutString.substring(0, cutString.indexOf("'"));
    }

    private static String getSubmittedValueFromValidationErrorString(String description) {
        String searchingString = "='";
        String cutString = description.substring(description.indexOf(searchingString) + searchingString.length());
        return cutString.substring(0, cutString.indexOf("'"));
    }

    private static String getFieldTypeFromValidationErrorString(String description) {
        String searchingString = "not a valid '";
        String cutString = description.substring(description.indexOf(searchingString) + searchingString.length());
        return cutString.substring(0, cutString.indexOf("'"));
    }

    private static MappedFieldErrorValidationToCommonResponse getMappedField(String type) {
        String mappingType = type.toUpperCase();

        try {
            return MappedFieldErrorValidationToCommonResponse.valueOf(mappingType);
        } catch (Exception e) {
            e.printStackTrace();
            return MappedFieldErrorValidationToCommonResponse.UNMAPPED;
        }
    }


    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "statusCode=" + statusCode +
                ", description='" + description + '\'' +
                ", isError=" + isError +
                '}';
    }
}
