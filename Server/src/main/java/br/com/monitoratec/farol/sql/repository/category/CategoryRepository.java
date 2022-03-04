package br.com.monitoratec.farol.sql.repository.category;

import br.com.monitoratec.farol.sql.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
