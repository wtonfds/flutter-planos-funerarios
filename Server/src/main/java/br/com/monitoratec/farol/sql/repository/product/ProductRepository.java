package br.com.monitoratec.farol.sql.repository.product;

import br.com.monitoratec.farol.sql.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByAccreditedId(Long accreditedId, Pageable pageable);
}
