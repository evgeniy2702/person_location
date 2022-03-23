package ua.ukrposhta.person_location.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ukrposhta.person_location.model.Person;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {


    @Query("SELECT p FROM Person p WHERE p.phone IS NOT NUll ORDER BY p.lastname")
    List<Person> findAll();

    @Query("SELECT DISTINCT p.directorate FROM Person p WHERE p.directorate IS NOT NULL AND p.directorate <> '' ORDER BY p.directorate")
    List<String> allDirectorateName();

    @Query("SELECT DISTINCT p.state FROM Person p WHERE p.state IS NOT NULL  AND p.state <> '' ORDER BY p.state")
    List<String> allStatesList();

    @Query("SELECT p FROM Person p WHERE p.directorate = :directorate ORDER BY p.lastname")
    List<Person> findPersonByDirectorate(@Param("directorate") String directorate);

    @Query("SELECT p FROM Person p WHERE p.phone = :phone ORDER BY p.lastname")
    List<Person> findPersonByPhone(@Param("phone") String phone);

    Person getPersonById(Long id);


//    @Query("SELECT p FROM Person p WHERE p.directorate = :directorate and p.last_modified LIKE :last_modified ORDER BY p.lastname")
//    List<Person> findPersonByDirectorateAndLast_modifiedIsContaining(@Param("directorate") String directorate,
//                                                                    @Param("last_modified") LocalDateTime last_modified );
//
    @Query("SELECT p FROM Person p WHERE p.last_modified >= :date_start AND p.last_modified < :date_end ORDER BY p.lastname")
    List<Person> findPersonByLast_modified(@Param("date_start") LocalDateTime date_start,
                                           @Param("date_end") LocalDateTime date_end);

    @Query("SELECT p FROM Person p WHERE LOWER(p.lastname) LIKE LOWER(CONCAT('%',:lastname,'%')) ORDER BY p.lastname")
    List<Person> findPersonByLastname(@Param("lastname") String lastname);

    @Query("SELECT p FROM Person p WHERE p.vacation = :vacation ORDER BY p.lastname")
    List<Person> findPersonByVacation(@Param("vacation") boolean vacation);

    @Query("SELECT p FROM Person p WHERE p.refugee = :refugee ORDER BY p.lastname")
    List<Person> findPersonByRefugee(@Param("refugee") boolean refugee);

    @Query("SELECT p FROM Person p WHERE p.able_for_work = :ableForWork ORDER BY p.lastname")
    List<Person> findPersonByAble_for_work(@Param("ableForWork") boolean able_for_work);

    @Query("SELECT p FROM Person p WHERE p.work_remote = :workRemote ORDER BY p.lastname")
    List<Person> findPersonByWork_remote(@Param("workRemote") boolean work_remote);

    @Query("SELECT p FROM Person p WHERE p.work_by_place = :workByPlace ORDER BY p.lastname")
    List<Person> findPersonByWork_by_place(@Param("workByPlace") boolean work_by_place);

    @Query("SELECT p FROM Person p WHERE p.war_zone = :warZone ORDER BY p.lastname")
    List<Person> findPersonByWar_zone(@Param("warZone") boolean war_zone
    );

    List<Person> findPersonByState(String state);
//
//    @Query("SELECT p FROM Person p WHERE p.vacation = true AND p.refugee = true")
//    List<Person> findPersonByRefugeeAndVacation();

//    @Query("SELECT p FROM Person p WHERE p.phone IS NOT NUll ORDER BY p.lastname DESC")
//    List<Person> findAllrevers();

}