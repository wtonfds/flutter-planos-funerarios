package br.com.monitoratec.farol.graphql.model.dtos.tem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterResponseWrapperDTO {
    @JsonProperty("payloadcpf")
    private RegisterResponseDTO registerResponseDTO;

    private Integer status;

    private String message;

    public RegisterResponseWrapperDTO(RegisterResponseDTO registerResponseDTO, Integer status, String message) {
        this.registerResponseDTO = registerResponseDTO;
        this.status = status;
        this.message = message;
    }

    public RegisterResponseDTO getRegisterResponseDTO() {
        return registerResponseDTO;
    }

    public void setRegisterResponseDTO(RegisterResponseDTO registerResponseDTO) {
        this.registerResponseDTO = registerResponseDTO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
