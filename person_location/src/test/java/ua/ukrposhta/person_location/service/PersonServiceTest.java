package ua.ukrposhta.person_location.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.ukrposhta.person_location.AbstractTest;
import ua.ukrposhta.person_location.model.Person;

import java.util.stream.Collectors;


@Slf4j
class PersonServiceTest extends AbstractTest {


    private PersonService personService;


   @Autowired
   public void setPersonService(PersonService personService){
       this.personService = personService;
   }


    @Test
    void findAllTest() {

       getAndPrint();

       getAndPrint();

    }

    private void getAndPrint() {
        log.info("--------------------------------------");
        log.info("persons list cache : {}", personService.findAll().stream().map(Person::getGeolocation).collect(Collectors.toList()));
        log.info("hashcode : {}",
                personService.findAll().stream().
                        map(Person::getGeolocation).collect(Collectors.toList()).hashCode() ==
                personService.findAll().stream().map(Person::getGeolocation).collect(Collectors.toList()).hashCode());
    }

}