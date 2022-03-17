package ua.ukrposhta.person_location.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ukrposhta.person_location.model.Person;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {


    @Query("SELECT p FROM Person p WHERE p.phone IS NOT NUll ORDER BY p.lastname")
    List<Person> findAll();

    @Query("SELECT p.directorate FROM Person p WHERE p.directorate IS NOT NULL AND p.directorate <> '' ORDER BY p.directorate")
    List<String> allDirectorateName();

    @Query("SELECT p FROM Person p WHERE p.directorate = :directorate ORDER BY p.lastname")
    List<Person> findPersonByDirectorate(@Param("directorate") String directorate);

    @Query("SELECT p FROM Person p WHERE p.phone = :phone ORDER BY p.lastname")
    List<Person> findPersonByPhone(@Param("phone") String phone);

    Person getPersonById(Long id);

//    @Query("SELECT p FROM Person p WHERE p.directorate = :directorate and p.last_modified LIKE :last_modified ORDER BY p.lastname")
//    List<Person> findPersonByDirectorateAndLast_modifiedIsContaining(@Param("directorate") String directorate,
//                                                                    @Param("last_modified") LocalDateTime last_modified );
//
//    @Query("SELECT p FROM Person p WHERE p.last_modified = :last_modified ORDER BY p.lastname")
//    List<Person> findPersonByLast_modified(@Param("last_modified") LocalDateTime last_modified);

    //    controller listPersonByLastname

//    @Query("SELECT p FROM Person p WHERE p.lastname = :lastname ORDER BY p.lastname")
//    List<Person> findPersonByLastname(String lastname);
//
//    List<Person> findPersonByLastnameIsContaining( String partOfLastname);

//    @Query("SELECT p FROM Person p WHERE p.phone IS NOT NUll ORDER BY p.lastname DESC")
//    List<Person> findAllrevers();

}