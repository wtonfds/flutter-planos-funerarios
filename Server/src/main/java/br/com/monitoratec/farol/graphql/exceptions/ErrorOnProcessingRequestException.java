package br.com.monitoratec.farol.graphql.exceptions;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorOnProcessingRequestException extends RuntimeException implements GraphQLError {

    private CommonResponse commonResponse;

    public ErrorOnProcessingRequestException(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    @Override
    public String getMessage() {
        return this.commonResponse.getDescription();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return new ArrayList<>();
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public List<Object> getPath() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getExtensions() {
        return new HashMap<>();
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }
}
