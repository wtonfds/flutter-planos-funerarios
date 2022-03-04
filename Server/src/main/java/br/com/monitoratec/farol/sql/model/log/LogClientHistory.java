package br.com.monitoratec.farol.sql.model.log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "frl_log_client_history")
public class LogClientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_log_client_history_sequence")
    @SequenceGenerator(name = "frl_log_client_history_sequence", allocationSize = 1)
    private Long id;

    @Column
    private String type;

    @Column
    private String description;

    @Column
    private LocalDateTime date;

    @Column
    private String clientCpf;

    @Column
    private String clientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
