package br.com.monitoratec.farol.utils.lottery;

import br.com.monitoratec.farol.graphql.model.dtos.lotteryNumbers.LotteryNumbersDTO;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.sql.repository.lotteryNumber.LotteryNumberRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LotteryNumberUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryNumberUtils.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final RestTemplate restTemplate;
    private final LotteryNumberRepository lotteryNumberRepository;
    private final GeneralParameterizationRepository generalParameterizationRepository;

    public LotteryNumberUtils(RestTemplate restTemplate, LotteryNumberRepository lotteryNumberRepository, GeneralParameterizationRepository generalParameterizationRepository){
        this.restTemplate = restTemplate;
        this.lotteryNumberRepository = lotteryNumberRepository;
        this.generalParameterizationRepository = generalParameterizationRepository;
    }

    private LotteryDTO getLotteryNumbers() {

        GeneralParameterization generalParameterization = generalParameterizationRepository.findAll().get(0);

        String url = generalParameterization.getLotteryURL();

        LotteryDTO lotteryDTO = new LotteryDTO();
        try {
            final String dto = restTemplate.getForObject( url,
                    String.class);

            LOGGER.info(dto);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            lotteryDTO = objectMapper.readValue(dto, LotteryDTO.class);
            LOGGER.info(lotteryDTO.toString());
        } catch (Exception e){
            LOGGER.error(String.valueOf(e));
        }
        return lotteryDTO;
    }

    public LotteryNumbersDTO generateCustomNumber() {
        LotteryDTO lotteryDTO = getLotteryNumbers();
        LotteryResultDTO result = lotteryDTO.getPremios().get(0);

        String generatedNumber = result.premio1.substring(result.premio1.length()-1);
        generatedNumber = generatedNumber.concat(result.premio2.substring(result.premio2.length()-1));
        generatedNumber = generatedNumber.concat(result.premio3.substring(result.premio3.length()-1));
        generatedNumber = generatedNumber.concat(result.premio4.substring(result.premio4.length()-1));
        generatedNumber = generatedNumber.concat(result.premio5.substring(result.premio5.length()-1));

        LOGGER.info(generatedNumber);

        LotteryNumber lotteryNumber = new LotteryNumber();
        lotteryNumber.setOriginalNumber1(result.getPremio1());
        lotteryNumber.setOriginalNumber2(result.getPremio2());
        lotteryNumber.setOriginalNumber3(result.getPremio3());
        lotteryNumber.setOriginalNumber4(result.getPremio4());
        lotteryNumber.setOriginalNumber5(result.getPremio5());
        lotteryNumber.setGeneratedNumbers(generatedNumber);



        lotteryNumber.setDrawDay(LocalDate.parse(result.getDataExtracao(), formatter));
        lotteryNumber = lotteryNumberRepository.save(lotteryNumber);

//        LotteryNumber lotteryNumber = new LotteryNumber(lotteryDTO);


        return new LotteryNumbersDTO(lotteryNumber);

    }

    private static class LotteryDTO {
        private List<LotteryResultDTO> premios;

        public List<LotteryResultDTO> getPremios() {
            return premios;
        }

        public void setPremios(List<LotteryResultDTO> premios) {
            this.premios = premios;
        }
    }

    private static class LotteryResultDTO {
        String premio1;
        String premio2;
        String premio3;
        String premio4;
        String premio5;
        String extracao;

        public String getDataExtracao() {
            return dataExtracao;
        }

        public void setDataExtracao(String dataExtracao) {
            this.dataExtracao = dataExtracao;
        }

        String dataExtracao;

        public String getPremio1() {
            return premio1;
        }

        public void setPremio1(String premio1) {
            this.premio1 = premio1;
        }

        public String getPremio2() {
            return premio2;
        }

        public void setPremio2(String premio2) {
            this.premio2 = premio2;
        }

        public String getPremio3() {
            return premio3;
        }

        public void setPremio3(String premio3) {
            this.premio3 = premio3;
        }

        public String getPremio4() {
            return premio4;
        }

        public void setPremio4(String premio4) {
            this.premio4 = premio4;
        }

        public String getPremio5() {
            return premio5;
        }

        public void setPremio5(String premio5) {
            this.premio5 = premio5;
        }

        public String getExtracao() {
            return extracao;
        }

        public void setExtracao(String extracao) {
            this.extracao = extracao;
        }
    }

}

