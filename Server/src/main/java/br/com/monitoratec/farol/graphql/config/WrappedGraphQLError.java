package br.com.monitoratec.farol.graphql.config;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import graphql.ErrorClassification;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.validation.ValidationError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrappedGraphQLError implements GraphQLError {
    private static final String EXCEPTION_MESSAGE_TEXT = "Error processing request, please see extensions field for more details";
    private static final String VALIDATION_ERROR_MESSAGE_TEXT = "Error validating some fields of the inputs, please see extensions field for more details";
    private static final String MESSAGE_FIELD = "original_message";
    private static final String COMMON_RESPONSE_FIELD = "common_response";

    private final List<SourceLocation> locations;
    private final ErrorClassification errorType;
    private String message;
    private final Map<String, Object> extensions;

    public WrappedGraphQLError(GraphQLError graphQLError) {
        this.locations = graphQLError.getLocations();
        this.errorType = graphQLError.getErrorType();
        this.message = graphQLError.getMessage();

        if (graphQLError.getExtensions() != null) {
            this.extensions = graphQLError.getExtensions();
        } else {
            this.extensions = new HashMap<>();
        }
    }

    public WrappedGraphQLError(ExceptionWhileDataFetching exceptionWhileDataFetching) {
        this((GraphQLError) exceptionWhileDataFetching);

        this.extensions.put(MESSAGE_FIELD, this.getMessage());
        this.message = EXCEPTION_MESSAGE_TEXT;

        CommonResponse commonResponse = CommonResponse.buildFromException(exceptionWhileDataFetching.getException());

        this.extensions.put(COMMON_RESPONSE_FIELD, commonResponse);
    }

    public WrappedGraphQLError(ValidationError validationError) {
        this((GraphQLError) validationError);

        this.extensions.put(MESSAGE_FIELD, this.getMessage());
        this.message = VALIDATION_ERROR_MESSAGE_TEXT;

        CommonResponse commonResponse = CommonResponse.fromGraphqlValidationError(validationError);

        this.extensions.put(COMMON_RESPONSE_FIELD, commonResponse);
    }

    public WrappedGraphQLError(ErrorOnProcessingRequestException errorOnProcessingRequestException) {
        this((GraphQLError) errorOnProcessingRequestException);

        this.extensions.put(MESSAGE_FIELD, this.getMessage());
        this.message = EXCEPTION_MESSAGE_TEXT;

        this.extensions.put(COMMON_RESPONSE_FIELD, errorOnProcessingRequestException.getCommonResponse());
    }

    @Override
    public List<SourceLocation> getLocations() {
        return this.locations;
    }

    @Override
    public ErrorClassification getErrorType() {
        return this.errorType;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return this.extensions;
    }
}
