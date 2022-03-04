package br.com.monitoratec.farol.service.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.service.s3.S3Service;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ClientService {
    private final MailService mailService;
    private final String resetPasswordFrom;
    private final String resetPasswordSubject;
    private final String resetPasswordBody;
    private final ClientRepository clientRepository;
    private final S3Service s3Service;

    public ClientService(MailService mailService,
                         S3Service s3Service,
                         @Value("${mail.reset-password-from}") String resetPasswordFrom,
                         @Value("${mail.reset-password-subject}") String resetPasswordSubject,
                         @Value("${mail.reset-password-body}") String resetPasswordBody,
                         ClientRepository clientRepository) {

        this.mailService = mailService;
        this.resetPasswordFrom = resetPasswordFrom;
        this.resetPasswordSubject = resetPasswordSubject;
        this.resetPasswordBody = resetPasswordBody;
        this.clientRepository = clientRepository;
        this.s3Service = s3Service;
    }

    //Rollback the transaction if sending the e-mail fails
    @Transactional
    public void sendTemporaryPasswordByEmail(Client client, String password) {
        if (client.getEmail().isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.IMPOSSIBLE_TO_RECOVER_PASSWORD);
        }
        final String mailBody = resetPasswordBody.replace("<new_password>", password);
        mailService.send(resetPasswordFrom, client.getEmail(), resetPasswordSubject, mailBody);
    }

    public List<Client> getDependentsForReactivation(Client holder) {
        List<Client> dependents = clientRepository.findAllByHolder(holder);

        for (Client dependent : dependents) {
            dependent.setDeleted(false);
        }

        return dependents;
    }

    public String generateDischargeStatement(String html) throws IOException, DocumentException {
        final byte[] pdfData;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            pdfData = outputStream.toByteArray();
        }

        return s3Service.uploadPdf(pdfData);
    }
}
