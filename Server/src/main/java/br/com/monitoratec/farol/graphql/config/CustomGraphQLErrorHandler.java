package br.com.monitoratec.farol.graphql.config;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.core.GraphQLErrorHandler;
import graphql.validation.ValidationError;
import graphql.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.List;

public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {
    @Override
    public boolean errorsPresent(List<GraphQLError> errors) {
        return errors.size() > 0;
    }

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        List<GraphQLError> newErrors = new ArrayList<>(errors.size());

        errors.forEach(graphQLError -> {
            newErrors.add(this.processError(graphQLError));
        });

        return newErrors;
    }

    protected GraphQLError processError(GraphQLError graphQLError) {
        GraphQLError outputtingError = graphQLError;

        if (graphQLError instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionWhileDataFetching = (ExceptionWhileDataFetching) graphQLError;

            if (exceptionWhileDataFetching.getException() instanceof ErrorOnProcessingRequestException) {
                // An ErrorOnProcessingRequestException exception, caused by this backend
                ErrorOnProcessingRequestException errorOnProcessingRequestException = (ErrorOnProcessingRequestException) exceptionWhileDataFetching.getException();
                outputtingError = new WrappedGraphQLError(errorOnProcessingRequestException);
            } else {
                // Unexpected exception
                outputtingError = new WrappedGraphQLError(exceptionWhileDataFetching);
            }

        } else if (graphQLError instanceof ValidationError) {
            ValidationError validationError = (ValidationError) graphQLError;
            if (validationError.getValidationErrorType().equals(ValidationErrorType.WrongType)) {
                outputtingError = new WrappedGraphQLError(validationError);
            }
        }

        return outputtingError;
    }
}
