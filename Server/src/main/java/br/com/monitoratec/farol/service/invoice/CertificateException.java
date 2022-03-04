package br.com.monitoratec.farol.service.invoice;

public class CertificateException extends RuntimeException {
    public CertificateException(String message) {
        super(message);
    }

    public CertificateException(Throwable cause) {
        super(cause);
    }
}
