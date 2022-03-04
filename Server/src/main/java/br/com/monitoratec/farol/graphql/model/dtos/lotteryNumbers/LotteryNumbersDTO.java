package br.com.monitoratec.farol.graphql.model.dtos.lotteryNumbers;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.model.dtos.user.FarolUserBasicDTO;
import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;

public class LotteryNumbersDTO {
    private Long id;
    private String originalNumber1;
    private String originalNumber2;
    private String originalNumber3;
    private String originalNumber4;
    private String originalNumber5;
    private String generatedNumbers;
    private Date drawDay;
    private FarolUserBasicDTO approvedBy;

    public LotteryNumbersDTO(LotteryNumber lotteryNumber) {
        this.id = lotteryNumber.getId();
        this.originalNumber1 = lotteryNumber.getOriginalNumber1();
        this.originalNumber2 = lotteryNumber.getOriginalNumber2();
        this.originalNumber3 = lotteryNumber.getOriginalNumber3();
        this.originalNumber4 = lotteryNumber.getOriginalNumber4();
        this.originalNumber5 = lotteryNumber.getOriginalNumber5();
        this.generatedNumbers = lotteryNumber.getGeneratedNumbers();
        this.drawDay = new Date(lotteryNumber.getDrawDay());
        if (lotteryNumber.getApprovedBy() != null) {
            this.approvedBy = new FarolUserBasicDTO(lotteryNumber.getApprovedBy());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneratedNumbers() {
        return generatedNumbers;
    }

    public void setGeneratedNumbers(String generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    public Date getDrawDay() {
        return drawDay;
    }

    public void setDrawDay(Date drawDay) {
        this.drawDay = drawDay;
    }

    public FarolUserBasicDTO getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(FarolUserBasicDTO approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getOriginalNumber1() {
        return originalNumber1;
    }

    public void setOriginalNumber1(String originalNumber1) {
        this.originalNumber1 = originalNumber1;
    }

    public String getOriginalNumber2() {
        return originalNumber2;
    }

    public void setOriginalNumber2(String originalNumber2) {
        this.originalNumber2 = originalNumber2;
    }

    public String getOriginalNumber3() {
        return originalNumber3;
    }

    public void setOriginalNumber3(String originalNumber3) {
        this.originalNumber3 = originalNumber3;
    }

    public String getOriginalNumber4() {
        return originalNumber4;
    }

    public void setOriginalNumber4(String originalNumber4) {
        this.originalNumber4 = originalNumber4;
    }

    public String getOriginalNumber5() {
        return originalNumber5;
    }

    public void setOriginalNumber5(String originalNumber5) {
        this.originalNumber5 = originalNumber5;
    }
}
