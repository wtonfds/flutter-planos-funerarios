
package br.com.monitoratec.farol.soap.ginfes;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 *
 */
@WebServiceClient(name = "ServiceGinfesImplService", targetNamespace = "http://homologacao.ginfes.com.br", wsdlLocation = "file:/C:/Dev/farol/FarolBackend/src/main/resources/wsdl/ginfes-homolog.wsdl")
public class ServiceGinfesImplService
    extends Service
{

    private final static URL SERVICEGINFESIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException SERVICEGINFESIMPLSERVICE_EXCEPTION;
    private final static QName SERVICEGINFESIMPLSERVICE_QNAME = new QName("http://homologacao.ginfes.com.br", "ServiceGinfesImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Dev/farol/FarolBackend/src/main/resources/wsdl/ginfes-homolog.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SERVICEGINFESIMPLSERVICE_WSDL_LOCATION = url;
        SERVICEGINFESIMPLSERVICE_EXCEPTION = e;
    }

    public ServiceGinfesImplService() {
        super(__getWsdlLocation(), SERVICEGINFESIMPLSERVICE_QNAME);
    }

    public ServiceGinfesImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SERVICEGINFESIMPLSERVICE_QNAME, features);
    }

    public ServiceGinfesImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICEGINFESIMPLSERVICE_QNAME);
    }

    public ServiceGinfesImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVICEGINFESIMPLSERVICE_QNAME, features);
    }

    public ServiceGinfesImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServiceGinfesImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns ServiceGinfesImpl
     */
    @WebEndpoint(name = "ServiceGinfesImplPort")
    public ServiceGinfesImpl getServiceGinfesImplPort() {
        return super.getPort(new QName("http://homologacao.ginfes.com.br", "ServiceGinfesImplPort"), ServiceGinfesImpl.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServiceGinfesImpl
     */
    @WebEndpoint(name = "ServiceGinfesImplPort")
    public ServiceGinfesImpl getServiceGinfesImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://homologacao.ginfes.com.br", "ServiceGinfesImplPort"), ServiceGinfesImpl.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SERVICEGINFESIMPLSERVICE_EXCEPTION!= null) {
            throw SERVICEGINFESIMPLSERVICE_EXCEPTION;
        }
        return SERVICEGINFESIMPLSERVICE_WSDL_LOCATION;
    }

}