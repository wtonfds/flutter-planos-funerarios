package br.com.monitoratec.farol.graphql.model.input.document;

import br.com.monitoratec.farol.graphql.model.dtos.document.DocumentClientTypeDTO;

import java.util.List;

public class DocumentsInput {
    private String image;
    private DocumentClientTypeDTO documentClientTypeDTO;

    public DocumentsInput(String image, DocumentClientTypeDTO documentClientTypeDTO) {
        this.image = image;
        this.documentClientTypeDTO = documentClientTypeDTO;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DocumentClientTypeDTO getDocumentClientTypeDTO() {
        return documentClientTypeDTO;
    }

    public void setDocumentClientTypeDTO(DocumentClientTypeDTO documentClientTypeDTO) {
        this.documentClientTypeDTO = documentClientTypeDTO;
    }
}
