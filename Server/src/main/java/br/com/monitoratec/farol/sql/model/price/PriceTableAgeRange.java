package br.com.monitoratec.farol.sql.model.price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "frl_price_table_age_range")
public class PriceTableAgeRange {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_price_table_age_range_sequence")
    @SequenceGenerator(name = "frl_price_table_age_range_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private int startAge;

    @Column(nullable = false)
    private int endAge;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceTableAgeRange that = (PriceTableAgeRange) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PriceTableAgeRange{" +
                "id=" + id +
                ", startAge=" + startAge +
                ", endAge=" + endAge +
                ", value=" + value +
                '}';
    }
}
