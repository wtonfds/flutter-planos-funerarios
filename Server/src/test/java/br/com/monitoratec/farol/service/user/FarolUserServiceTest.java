package br.com.monitoratec.farol.service.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.user.ResetFarolUserPasswordInput;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.User.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class FarolUserServiceTest {
    private FarolUserService service;

    @Autowired
    private FarolUserRepository repository;

    @Mock
    private PasswordUtils passwordUtils;

    @Mock
    private MailService mailService;

    @Value("${mail.reset-password-from}")
    private String resetPasswordFrom;

    @Value("${mail.reset-password-subject}")
    private String resetPasswordSubject;

    @Value("${mail.reset-password-body}")
    private String resetPasswordBody;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setup() {
        service = new FarolUserService(repository, passwordUtils, mailService, resetPasswordFrom, resetPasswordSubject, resetPasswordBody);
    }

    @Test
    void testResetPassword() {
        final ResetFarolUserPasswordInput input = new ResetFarolUserPasswordInput("10047828021", "user1@example.com");

        final String newPassword = "X*s9Y&Z8ad7(";
        when(passwordUtils.generateRandom()).thenReturn(newPassword);

        final String newPasswordHashed = "$2a$10$C9AmjL8ypMvfo6tzcW.JXOgtzkrq.Pp/zf9SgCiZmLxEp.7v2e2pS";
        when(passwordUtils.hashPassword(newPassword)).thenReturn(newPasswordHashed);

        service.resetPassword(input);
        em.flush(); //Force the update

        final FarolUser user = em.find(FarolUser.class, 1L);
        assertEquals(newPasswordHashed, user.getPasswordHash());
        assertTrue(user.isTemporaryPassword());

        final String expectedBody = resetPasswordBody.replace("<new_password>", newPassword);

        verify(passwordUtils).generateRandom();
        verify(passwordUtils).hashPassword(newPassword);
        verify(mailService).send(resetPasswordFrom, user.getEmail(), resetPasswordSubject, expectedBody);
        verifyNoMoreInteractions(passwordUtils, mailService);
    }

    @Test
    void testResetPasswordNonExistingCpf() {
        final ResetFarolUserPasswordInput input = new ResetFarolUserPasswordInput("12345678910", "user1@example.com");

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.resetPassword(input));
        assertEquals(USER_NOT_FOUND, exception.getCommonResponse());

        verifyNoInteractions(passwordUtils, mailService);
    }

    @Test
    void testResetPasswordWrongEmail() {
        final ResetFarolUserPasswordInput input = new ResetFarolUserPasswordInput("10047828021", "wrong@example.com");

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.resetPassword(input));
        assertEquals(USER_NOT_FOUND, exception.getCommonResponse());

        verifyNoInteractions(passwordUtils, mailService);
    }
}
