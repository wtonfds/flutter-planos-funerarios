package br.com.monitoratec.farol.service.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.user.ResetFarolUserPasswordInput;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.User.USER_NOT_FOUND;

@Service
public class FarolUserService {
    private final FarolUserRepository repository;
    private final PasswordUtils passwordUtils;
    private final MailService mailService;
    private final String resetPasswordFrom;
    private final String resetPasswordSubject;
    private final String resetPasswordBody;

    public FarolUserService(FarolUserRepository repository, PasswordUtils passwordUtils, MailService mailService,
                            @Value("${mail.reset-password-from}") String resetPasswordFrom,
                            @Value("${mail.reset-password-subject}") String resetPasswordSubject,
                            @Value("${mail.reset-password-body}") String resetPasswordBody) {
        this.repository = repository;
        this.passwordUtils = passwordUtils;
        this.mailService = mailService;
        this.resetPasswordFrom = resetPasswordFrom;
        this.resetPasswordSubject = resetPasswordSubject;
        this.resetPasswordBody = resetPasswordBody;
    }

    //Rollback the transaction if sending the e-mail fails
    @Transactional
    public void resetPassword(ResetFarolUserPasswordInput input) {
        final FarolUser user = repository.findByCPFAndActiveTrueAndEmail(input.getCpf(), input.getEmail())
                .orElseThrow(() -> new ErrorOnProcessingRequestException(USER_NOT_FOUND));

        final String password = passwordUtils.generateRandom();

        //As this method is transactional, we don't need to call repository.save(user) after this
        user.setPasswordHash(passwordUtils.hashPassword(password));
        user.setTemporaryPassword(true);

        final String mailBody = resetPasswordBody.replace("<new_password>", password);
        mailService.send(resetPasswordFrom, user.getEmail(), resetPasswordSubject, mailBody);
    }
}
