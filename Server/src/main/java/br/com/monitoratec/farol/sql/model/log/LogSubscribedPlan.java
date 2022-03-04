package br.com.monitoratec.farol.sql.model.log;

import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.SystemUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "frl_log_subscribed_plan")
public class LogSubscribedPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_plan_sequence")
    @SequenceGenerator(name = "frl_plan_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 511, nullable = false)
    private String commentary;

    @Column(nullable = false)
    private LocalDateTime reactivationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SystemUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SubscribedPlan subscribedPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public LocalDateTime getReactivationDate() {
        return reactivationDate;
    }

    public void setReactivationDate(LocalDateTime reactivationDate) {
        this.reactivationDate = reactivationDate;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public SubscribedPlan getSubscribedPlan() {
        return subscribedPlan;
    }

    public void setSubscribedPlan(SubscribedPlan plan) {
        this.subscribedPlan = plan;
    }
}
