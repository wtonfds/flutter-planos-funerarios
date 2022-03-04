package br.com.monitoratec.farol.sql.repository.lotteryNumber;

import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LotteryNumberRepository extends JpaRepository<LotteryNumber, Long> {

    Optional<LotteryNumber> findById(Long id);

    Page<LotteryNumber> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_lottery_numbers", nativeQuery = true)
    void refreshLotteryNumbersMaterializedView();

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_lottery_numbers_winners", nativeQuery = true)
    void refreshLotteryWinnersMaterializedView();

}
