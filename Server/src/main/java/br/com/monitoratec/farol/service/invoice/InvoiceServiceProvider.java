package br.com.monitoratec.farol.service.invoice;

import br.com.monitoratec.farol.soap.ginfes.ServiceGinfesImpl;
import br.com.monitoratec.farol.soap.ginfes.ServiceGinfesImplService;
import com.sun.xml.ws.developer.JAXWSProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.io.IOException;

@Service
public class InvoiceServiceProvider {
    private final String ginfesUrl;
    private final Resource wsdlLocation;
    private final CertificateManager certificateManager;

    public InvoiceServiceProvider(@Value("${ginfes.url}") String ginfesUrl, @Value("${ginfes.wsdl-location}") Resource wsdlLocation,
                                  CertificateManager certificateManager) {
        this.ginfesUrl = ginfesUrl;
        this.wsdlLocation = wsdlLocation;
        this.certificateManager = certificateManager;
    }

    public ServiceGinfesImpl getServiceGinfes() {
        try {
            final ServiceGinfesImplService service = new ServiceGinfesImplService(wsdlLocation.getURL(), new QName(ginfesUrl, "ServiceGinfesImplService"));
            final ServiceGinfesImpl port = service.getPort(new QName(ginfesUrl, "ServiceGinfesImplPort"), ServiceGinfesImpl.class);

            final SSLSocketFactory factory = certificateManager.createSslSocketFactory();
            ((BindingProvider) port).getRequestContext().put(JAXWSProperties.SSL_SOCKET_FACTORY, factory);

            return port;
        } catch (IOException e) {
            throw new InvoiceException(e);
        }
    }
}
