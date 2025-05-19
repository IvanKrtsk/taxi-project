package by.ikrotsyuk.bsuir.paymentservice.repository;

import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByUserIdAndAccountType(Long userId, AccountTypes accountType);
    Optional<AccountEntity> findByUserIdAndAccountType(Long userId, AccountTypes accountType);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountEntity> findFirstWithLockingByUserIdAndAccountType(Long userId, AccountTypes accountType);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountEntity> findFirstWithLockingById(Long id);
}
