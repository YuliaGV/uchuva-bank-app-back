package com.ravenprojects.uchuva_bank.repository;

import com.ravenprojects.uchuva_bank.model.IdDocumentType;
import com.ravenprojects.uchuva_bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByIdDocumentTypeAndIdDocumentNumber(IdDocumentType type, String number);

    Optional<User> findByIdDocumentTypeAndIdDocumentNumber(IdDocumentType type, String number);

    List<User> findByActiveTrue();

}
