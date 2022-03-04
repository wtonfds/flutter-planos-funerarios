package br.com.monitoratec.farol.service.invoice;

import br.com.monitoratec.farol.service.invoice.model.*;
import br.com.monitoratec.farol.soap.ginfes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);
    private static final DatatypeFactory DATATYPE_FACTORY = DatatypeFactory.newDefaultInstance();

    private final InvoiceServiceProvider provider;
    private final XmlSigner xmlSigner;

    public InvoiceService(InvoiceServiceProvider provider, XmlSigner xmlSigner) {
        this.provider = provider;
        this.xmlSigner = xmlSigner;
    }

    public EnviarLoteRpsResposta postInvoice(PostInvoiceParameters parameters) {
        final EnviarLoteRpsEnvio envio = toPayload(parameters);
        final String response;
        EnviarLoteRpsResposta enviarLoteRpsResposta;
        try {
            final ServiceGinfesImpl service = provider.getServiceGinfes();

            final String cabecalhoXml = generateHeader();
            final String unsignedPayload = toXml(envio);
            String signedPayload = xmlSigner.signXml(unsignedPayload, "Rps", "InfRps");
            signedPayload = xmlSigner.signXml(signedPayload, null, "LoteRps"); //TODO not sure if we should use "LoteRps" here or null; if using null, (maybe) it's necessary to remove the ID from it (see the method below).
            response = service.recepcionarLoteRpsV3(cabecalhoXml, signedPayload);

            JAXBContext jaxbContext = JAXBContext.newInstance(EnviarLoteRpsResposta.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(response);

            enviarLoteRpsResposta = (EnviarLoteRpsResposta) unmarshaller.unmarshal(reader);
        } catch (JAXBException | WebServiceException e) {
            throw new InvoiceException(e);
        }
        return enviarLoteRpsResposta;
    }

    private String generateHeader() throws JAXBException {
        final Cabecalho cabecalho = new Cabecalho();
        cabecalho.setVersao("3");
        cabecalho.setVersaoDados("3");
        return toXml(cabecalho);
    }

    private EnviarLoteRpsEnvio toPayload(PostInvoiceParameters parameters) {
        final EnviarLoteRpsEnvio envio = new EnviarLoteRpsEnvio();

        final TcLoteRps lote = new TcLoteRps();
        lote.setId(UUID.randomUUID().toString()); //TODO: is generating a random String like this ok?
        lote.setNumeroLote(parameters.getLotNumber());
        lote.setCnpj(parameters.getCnpj());
        lote.setInscricaoMunicipal(parameters.getMunicipalRegistry());

        final List<RpsInfo> rpsInfoList = parameters.getRpsInfoList();
        lote.setQuantidadeRps(rpsInfoList.size());
        lote.setListaRps(toListaRps(rpsInfoList));

        envio.setLoteRps(lote);
        return envio;
    }

    private TcLoteRps.ListaRps toListaRps(List<RpsInfo> rpsInfoList) {
        final TcLoteRps.ListaRps listaRps = new TcLoteRps.ListaRps();

        final List<TcRps> rpsList = listaRps.getRps();

        for (RpsInfo info : rpsInfoList) {
            final TcRps rps = new TcRps();
            rps.setInfRps(toInfRps(info));
            rpsList.add(rps);
        }

        return listaRps;
    }

    private TcInfRps toInfRps(RpsInfo info) {
        final TcInfRps infRps = new TcInfRps();
        XMLGregorianCalendar xmlGregorianCalendar = toXmlGregorianCalendar(info.getEmissionDate());
        xmlGregorianCalendar.setTime(info.getEmissionDate().getHour(), info.getEmissionDate().getMinute(), info.getEmissionDate().getSecond());
        infRps.setId("1200"); //TODO: is generating a random String like this ok?
        infRps.setIdentificacaoRps(toIdentificacaoRps(info.getRpsIdentification()));
        infRps.setDataEmissao(xmlGregorianCalendar);
        infRps.setNaturezaOperacao(info.getOperationNature().getValue());
        info.getSpecialTaxRegime().ifPresent(regime -> infRps.setRegimeEspecialTributacao(regime.getValue()));
        infRps.setOptanteSimplesNacional((byte) (info.isNationalSimple() ? 1 : 2));
        infRps.setIncentivadorCultural((byte) (info.isCulturalPromoter() ? 1 : 2));
        infRps.setStatus(info.getStatus().getValue());
        info.getReplacedRps().ifPresent(identification -> infRps.setRpsSubstituido(toIdentificacaoRps(identification)));
        infRps.setServico(toDadosServico(info.getServiceData()));

        final TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj(info.getProviderCnpj());
        info.getProviderMunicipalRegistry().ifPresent(prestador::setInscricaoMunicipal);
        infRps.setPrestador(prestador);

        infRps.setTomador(toDadosTomador(info.getTakerData()));
        info.getServiceIntermediaryInfo().ifPresent(intermediaryInfo -> infRps.setIntermediarioServico(toIdentificacaoIntermediario(intermediaryInfo)));
        info.getConstructionData().ifPresent(constructionData -> infRps.setConstrucaoCivil(toDadosConstrucaoCivil(constructionData)));

        return infRps;
    }

    private TcIdentificacaoRps toIdentificacaoRps(RpsIdentification rpsIdentification) {
        final TcIdentificacaoRps identificacaoRps = new TcIdentificacaoRps();
        identificacaoRps.setNumero(rpsIdentification.getNumber());
        identificacaoRps.setSerie(rpsIdentification.getSeries());
        identificacaoRps.setTipo(rpsIdentification.getType().getValue());

        return identificacaoRps;
    }

    private XMLGregorianCalendar toXmlGregorianCalendar(LocalDateTime dateTime) {
        return DATATYPE_FACTORY.newXMLGregorianCalendar(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), 0,
                DatatypeConstants.FIELD_UNDEFINED); //Note: this will use the system's default timezone (defined to GMT); is this correct?
    }

    private TcDadosServico toDadosServico(ServiceData data) {
        final TcDadosServico dadosServico = new TcDadosServico();
        dadosServico.setValores(toValores(data.getValues()));
        dadosServico.setItemListaServico(data.getServiceListItemCode());
        data.getCnaeCode().ifPresent(dadosServico::setCodigoCnae);
        data.getMunicipalTaxCode().ifPresent(dadosServico::setCodigoTributacaoMunicipio);
        dadosServico.setDiscriminacao(data.getDiscrimination());
        dadosServico.setCodigoMunicipio(data.getCityCode());

        return dadosServico;
    }

    private TcValores toValores(Values values) {
        final TcValores valores = new TcValores();
        valores.setValorServicos(values.getServicesValue());
        values.getDeductionsValue().ifPresent(valores::setValorDeducoes);
        values.getPisValue().ifPresent(valores::setValorPis);
        values.getCofinsValue().ifPresent(valores::setValorCofins);
        values.getInssValue().ifPresent(valores::setValorInss);
        values.getTaxIncomeValue().ifPresent(valores::setValorIr);
        values.getCsllValue().ifPresent(valores::setValorCsll);
        valores.setIssRetido((byte) (values.isRetainedTaxIncome() ? 1 : 2));
        values.getIssValue().ifPresent(valores::setValorIss);
        values.getRetainedIssValue().ifPresent(valores::setValorIssRetido);
        values.getOtherRetentions().ifPresent(valores::setOutrasRetencoes);
        values.getAliquot().ifPresent(valores::setAliquota);
        values.getUnconditionedDiscount().ifPresent(valores::setDescontoIncondicionado);
        values.getConditionedDiscount().ifPresent(valores::setDescontoCondicionado);

        valores.setBaseCalculo(values.getCalculationBasis());
        valores.setValorLiquidoNfse(values.getInvoiceNetValue());

        return valores;
    }

    private TcDadosTomador toDadosTomador(TakerData takerData) {
        final TcDadosTomador dadosTomador = new TcDadosTomador();
        takerData.getTakerInfo().ifPresent(takerInfo -> dadosTomador.setIdentificacaoTomador(toIdentificacaoTomador(takerInfo)));
        takerData.getCompanyName().ifPresent(dadosTomador::setRazaoSocial);
        takerData.getAddress().ifPresent(address -> dadosTomador.setEndereco(toEndereco(address)));
        takerData.getContact().ifPresent(contact -> dadosTomador.setContato(toContato(contact)));

        return dadosTomador;
    }

    private TcIdentificacaoTomador toIdentificacaoTomador(TakerInfo takerInfo) {
        final TcIdentificacaoTomador tomador = new TcIdentificacaoTomador();
        takerInfo.getCpfOrCnpj().ifPresent(cpfOrCnpj -> tomador.setCpfCnpj(toTcCpfCnpj(cpfOrCnpj)));
        takerInfo.getMunicipalRegistry().ifPresent(tomador::setInscricaoMunicipal);

        return tomador;
    }

    private TcEndereco toEndereco(FullAddress address) {
        final TcEndereco endereco = new TcEndereco();
        address.getStreet().ifPresent(endereco::setEndereco);
        address.getNumber().ifPresent(endereco::setNumero);
        address.getComplement().ifPresent(endereco::setComplemento);
        address.getNeighborhood().ifPresent(endereco::setBairro);
        address.getCityCode().ifPresent(endereco::setCodigoMunicipio);
        address.getProvince().ifPresent(endereco::setUf);
        address.getZipCode().ifPresent(endereco::setCep);

        return endereco;
    }

    private TcContato toContato(Contact contact) {
        final TcContato contato = new TcContato();
        contact.getPhone().ifPresent(contato::setTelefone);
        contact.getEmail().ifPresent(contato::setEmail);

        return contato;
    }

    private TcIdentificacaoIntermediarioServico toIdentificacaoIntermediario(ServiceIntermediaryInfo intermediaryInfo) {
        final TcIdentificacaoIntermediarioServico servico = new TcIdentificacaoIntermediarioServico();
        servico.setRazaoSocial(intermediaryInfo.getCompanyName());
        servico.setCpfCnpj(toTcCpfCnpj(intermediaryInfo.getCpfOrCnpj()));
        intermediaryInfo.getMunicipalRegistry().ifPresent(servico::setRazaoSocial);

        return servico;
    }

    private TcDadosConstrucaoCivil toDadosConstrucaoCivil(ConstructionData constructionData) {
        final TcDadosConstrucaoCivil construcaoCivil = new TcDadosConstrucaoCivil();
        construcaoCivil.setCodigoObra(constructionData.getConstructionCode());
        construcaoCivil.setArt(constructionData.getArtCode());
        return construcaoCivil;
    }

    private String toXml(Object object) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
        final Marshaller marshaller = context.createMarshaller();

        final StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);

        return writer.toString();
    }

    public ConsultarSituacaoLoteRpsResposta getInvoiceStatus(GetInvoiceStatusParameters parameters) {
        final ConsultarSituacaoLoteRpsEnvio envio = toPayload(parameters);
        final String response;
        ConsultarSituacaoLoteRpsResposta consultarSituacaoLoteRpsResposta;
        try {
            final ServiceGinfesImpl service = provider.getServiceGinfes();

            final String cabecalhoXml = generateHeader();
            final String unsignedPayload = toXml(envio);
            final String signedPayload = xmlSigner.signXml(unsignedPayload, null, null);

            response = service.consultarSituacaoLoteRpsV3(cabecalhoXml, signedPayload);

            JAXBContext jaxbContext = JAXBContext.newInstance(ConsultarSituacaoLoteRpsResposta.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(response);

            consultarSituacaoLoteRpsResposta = (ConsultarSituacaoLoteRpsResposta) unmarshaller.unmarshal(reader);
        } catch (JAXBException | WebServiceException e) {
            throw new InvoiceException(e);
        }

        return consultarSituacaoLoteRpsResposta;
    }

    private ConsultarSituacaoLoteRpsEnvio toPayload(GetInvoiceStatusParameters parameters) {
        final ConsultarSituacaoLoteRpsEnvio envio = new ConsultarSituacaoLoteRpsEnvio();
        envio.setPrestador(getIdentificacaoPrestador(parameters));
        envio.setProtocolo(parameters.getProtocol());
        return envio;
    }

    private TcIdentificacaoPrestador getIdentificacaoPrestador(GetInvoiceStatusParameters parameters) {
        final TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj(parameters.getProviderCnpj());
        parameters.getProviderMunicipalRegistry().ifPresent(prestador::setInscricaoMunicipal);

        return prestador;
    }

    private ConsultarNfseEnvio.PeriodoEmissao getPeriodoEmissao(DateRange range) {
        final ConsultarNfseEnvio.PeriodoEmissao periodo = new ConsultarNfseEnvio.PeriodoEmissao();
        periodo.setDataInicial(toXmlGregorianCalendar(range.getStart()));
        periodo.setDataFinal(toXmlGregorianCalendar(range.getEnd()));

        return periodo;
    }

    private XMLGregorianCalendar toXmlGregorianCalendar(LocalDate date) {
        return DATATYPE_FACTORY.newXMLGregorianCalendarDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), DatatypeConstants.FIELD_UNDEFINED);
    }

    private TcCpfCnpj toTcCpfCnpj(CpfOrCnpj cpfOrCnpj) {
        final TcCpfCnpj cpfCnpj = new TcCpfCnpj();
        if (cpfOrCnpj.isCpf()) {
            cpfCnpj.setCpf(cpfOrCnpj.getValue());
        } else {
            cpfCnpj.setCnpj(cpfOrCnpj.getValue());
        }

        return cpfCnpj;
    }

    public String getInvoice(GetInvoiceParameters parameters) {
        final String response;
        final ConsultarLoteRpsEnvio envio = toPayload(parameters);
        ConsultarLoteRpsResposta consultarLoteRpsResposta;
        try {
            final ServiceGinfesImpl service = provider.getServiceGinfes();

            final String cabecalhoXml = generateHeader();
            final String unsignedPayload = toXml(envio);

            final String signedPayload = xmlSigner.signXml(unsignedPayload, null, null);

            response = service.consultarLoteRpsV3(cabecalhoXml, signedPayload);

            JAXBContext jaxbContext = JAXBContext.newInstance(ConsultarLoteRpsResposta.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(response);

            consultarLoteRpsResposta = (ConsultarLoteRpsResposta) unmarshaller.unmarshal(reader);
            if (consultarLoteRpsResposta.getListaNfse() == null) {
                return null;
            }
        } catch(JAXBException | WebServiceException e) {
            throw new InvoiceException(e);
        }

        return response;
    }

    private ConsultarLoteRpsEnvio toPayload(GetInvoiceParameters parameters) {
        final ConsultarLoteRpsEnvio envio = new ConsultarLoteRpsEnvio();
        envio.setPrestador(getIdentificacaoPrestador(parameters));
        envio.setProtocolo(parameters.getProtocol());
        return envio;
    }

    private TcIdentificacaoPrestador getIdentificacaoPrestador(GetInvoiceParameters parameters) {
        final TcIdentificacaoPrestador prestador = new TcIdentificacaoPrestador();
        prestador.setCnpj(parameters.getProviderCnpj());
        prestador.setInscricaoMunicipal(parameters.getProviderMunicipalRegistry());

        return prestador;
    }
}
