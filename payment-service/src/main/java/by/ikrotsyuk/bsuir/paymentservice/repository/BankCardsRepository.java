package by.ikrotsyuk.bsuir.paymentservice.repository;

import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankCardsRepository extends JpaRepository<BankCardEntity, Long> {
    List<BankCardEntity> findAllByAccount(AccountEntity account);
}
