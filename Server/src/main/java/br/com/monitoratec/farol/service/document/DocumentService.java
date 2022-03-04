package br.com.monitoratec.farol.service.document;

import br.com.monitoratec.farol.graphql.model.input.document.DocumentsInput;
import br.com.monitoratec.farol.service.s3.S3Service;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.model.user.DocumentsClient;
import br.com.monitoratec.farol.sql.repository.document.DocumentClientRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;

@Service
public class DocumentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentService.class);

    private final ClientRepository clientRepository;
    private final S3Service s3Service;
    private final DocumentClientRepository documentClientRepository;

    public DocumentService(
            ClientRepository clientRepository,
            S3Service s3Service,
            DocumentClientRepository documentClientRepository
    ) {
        this.clientRepository = clientRepository;
        this.s3Service = s3Service;
        this.documentClientRepository = documentClientRepository;
    }

    @Transactional
    public void saveDependentsDocuments(List<Client> dependents) {
        LOGGER.info("DocumentService - saveDependentsDocuments");
        clientRepository.saveAll(dependents);

        for (Client c : dependents) {
            if (c.getDocumentsInput() != null) {
                for (DocumentsInput d : c.getDocumentsInput()) {
                    DocumentsClient documentsClient = new DocumentsClient();
                    String image = d.getImage();
                    byte[] bI = Base64.decodeBase64(image.getBytes());
                    String imageUrl = s3Service.uploadImage(bI);
                    documentsClient.setActive(true);
                    documentsClient.setClient(c);
                    documentsClient.setDocumentClientType(d.getDocumentClientTypeDTO().name());
                    documentsClient.setUrlDocument(imageUrl);
                    documentClientRepository.save(documentsClient);
                }
            }
        }
    }

    @Transactional
    public void saveCurrentClientDocuments(List<DocumentsInput> holderDocumentsInputs, Client currentClient) {
        LOGGER.info("DocumentService - saveCurrentClientDocuments");
        for (DocumentsInput d : holderDocumentsInputs) {
            DocumentsClient documentsClient = new DocumentsClient();
            String image = d.getImage();
            byte[] bI = Base64.decodeBase64(image.getBytes());
            String imageUrl = s3Service.uploadImage(bI);
            documentsClient.setActive(true);
            documentsClient.setClient(currentClient);
            documentsClient.setDocumentClientType(d.getDocumentClientTypeDTO().name());
            documentsClient.setUrlDocument(imageUrl);
            documentClientRepository.save(documentsClient);
        }
    }
}
