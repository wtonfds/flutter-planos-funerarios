package br.com.monitoratec.farol.sql.model.user;

import javax.persistence.*;

@Entity
@Table(name = "frl_documents_client")
public class DocumentsClient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_documents_sequence")
    @SequenceGenerator(name = "frl_documents_sequence", allocationSize = 1)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Client.class)
    private Client client;

    @Column(nullable = false)
    private String urlDocument;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false)
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getUrlDocument() {
        return urlDocument;
    }

    public void setUrlDocument(String urlDocument) {
        this.urlDocument = urlDocument;
    }

    public String getDocumentClientType() {
        return documentType;
    }

    public void setDocumentClientType(String documentClientType) {
        this.documentType = documentClientType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
