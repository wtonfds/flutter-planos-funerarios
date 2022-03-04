package br.com.monitoratec.farol.sql.repository.push;

import br.com.monitoratec.farol.sql.model.push.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    List<Preferences> findAll();
}
