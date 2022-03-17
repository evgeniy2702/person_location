package ua.ukrposhta.person_location.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.ukrposhta.person_location.model.Person;
import ua.ukrposhta.person_location.repositoriy.PersonRepo;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    private PersonRepo personRepo;

    @Autowired
    public void setPersonRepo(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public List<Person> findAll(){
        log.info("get users list from db ");
        return personRepo.findAll();
    }


    public Person getById(Long id){
        log.info("get person from db by id = {}", id);
        return personRepo.getPersonById(id);
    }

    public List<String> allDirectorateName(){
        return personRepo.allDirectorateName();
    }

    public List<Person> personListByDirectorate(String directorate){
        return personRepo.findPersonByDirectorate(directorate);
    }

    public Person updatePerson(Person person){
        log.info("Update cache of persons : {}", person.getGeolocation());
        return personRepo.save(person);
    }

    public List<Person> personByPhone(String phone) {
        return personRepo.findPersonByPhone(phone);
    }
}
