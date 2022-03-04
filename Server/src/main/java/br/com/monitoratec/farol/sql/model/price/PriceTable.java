package br.com.monitoratec.farol.sql.model.price;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PriceTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_price_table_sequence")
    @SequenceGenerator(name = "frl_price_table_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PriceTableAgeRange> ageRanges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceTableAgeRange> getAgeRanges() {
        return ageRanges;
    }

    public void setAgeRanges(List<PriceTableAgeRange> ageRanges) {
        this.ageRanges = ageRanges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceTable that = (PriceTable) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PriceTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Fields {
        public static final String ID = "id";
    }
}
