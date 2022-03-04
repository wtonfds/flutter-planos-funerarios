package br.com.monitoratec.farol.sql.model.lotteryNumber;

import br.com.monitoratec.farol.sql.model.user.FarolUser;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "frl_lottery_number")
public class LotteryNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_lottery_number_sequence")
    @SequenceGenerator(name="frl_lottery_number_sequence", allocationSize = 1)
    private Long id;
    private String originalNumber1;
    private String originalNumber2;
    private String originalNumber3;
    private String originalNumber4;
    private String originalNumber5;
    private String generatedNumbers;
    private LocalDate drawDay;

    @ManyToOne
    private FarolUser approvedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGeneratedNumbers() {
        return generatedNumbers;
    }

    public void setGeneratedNumbers(String generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }

    public LocalDate getDrawDay() {
        return drawDay;
    }

    public void setDrawDay(LocalDate drawDay) {
        this.drawDay = drawDay;
    }

    public FarolUser getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(FarolUser approvedBy) {
        this.approvedBy = approvedBy;
    }

    public static class Fields {
        public static final String ID = "id";
    }
}
