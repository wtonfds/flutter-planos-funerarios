package br.com.monitoratec.farol.graphql.model.dtos.document;

import br.com.monitoratec.farol.sql.model.user.DocumentsClient;

public class DocumentClientDTO {
    private Long id;
    private String urlDocument;
    private DocumentClientTypeDTO documentClientType;
    private Boolean active;

    public DocumentClientDTO(DocumentsClient documentsClient) {
        this.id = documentsClient.getId();
        this.urlDocument = documentsClient.getUrlDocument();
        this.documentClientType = DocumentClientTypeDTO.valueOf(documentsClient.getDocumentClientType());
        this.active = documentsClient.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlDocument() {
        return urlDocument;
    }

    public void setUrlDocument(String urlDocument) {
        this.urlDocument = urlDocument;
    }

    public DocumentClientTypeDTO getDocumentClientTypeDTO() {
        return documentClientType;
    }

    public void setDocumentClientTypeDTO(DocumentClientTypeDTO documentClientType) {
        this.documentClientType = documentClientType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
