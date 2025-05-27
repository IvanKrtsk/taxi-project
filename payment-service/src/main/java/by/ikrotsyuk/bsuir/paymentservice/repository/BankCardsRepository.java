package by.ikrotsyuk.bsuir.paymentservice.repository;

import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankCardsRepository extends JpaRepository<BankCardEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<BankCardEntity> findAllWithLockingByAccount(AccountEntity account);

    List<BankCardEntity> findAllByAccount(AccountEntity account);

    boolean existsByCardNumberAndExpirationDateAndAccount(String cardNumber, String expirationDate, AccountEntity account);

    Optional<BankCardEntity> findByAccountAndId(AccountEntity account, Long id);
}
