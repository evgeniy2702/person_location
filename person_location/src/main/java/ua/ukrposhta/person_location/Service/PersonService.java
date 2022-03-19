package ua.ukrposhta.person_location.Service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.ukrposhta.person_location.geocoder.Geocoder;
import ua.ukrposhta.person_location.model.Person;
import ua.ukrposhta.person_location.repositoriy.PersonRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@EnableScheduling
public class PersonService {

    @Value("${cron}")
    protected String cron_data = "0 0 0/1 ? * *";
    private PersonRepo personRepo;
    private Geocoder geocoder;

    @Autowired
    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Autowired
    public void setPersonRepo(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }


//    @Cacheable(value = "person")
    public List<Person> findAll(){
        log.info("get users list from db ");
        return personRepo.findAll();
    }


    public Person getById(Long id){
        log.info("get person from db by id = {}", id);
        return personRepo.getPersonById(id);
    }

//    @Cacheable(value = "directorate")
    public List<String> allDirectorateName(){
        return personRepo.allDirectorateName();
    }

    public List<Person> personListByDirectorate(String directorate){
        return personRepo.findPersonByDirectorate(directorate);
    }

//    @CacheEvict(value = "persons")
    public Person updatePerson(Person person){
        log.info("Update cache of persons : {}", person.getGeolocation());
        return personRepo.saveAndFlush(person);
    }

    public List<Person> personByPhone(String phone) {
        return personRepo.findPersonByPhone(phone);
    }

    public List<Person> findPersonByLastname(String lastname){
        log.info("get person from db by partname = {}", lastname);
        return personRepo.findPersonByLastname(lastname);
    }

    public List<Person> findPersonByLastModified(LocalDateTime dateStart, LocalDateTime dateEnd){
        return personRepo.findPersonByLast_modified(dateStart, dateEnd);
    }

    @Scheduled(cron = "0 37 * ? * *")
    public void updateGeolocationData(){
        log.info("update geolocation data");
        checkChangeGeolocation(personRepo.findAll());
    }

    private void checkChangeGeolocation(List<Person> personList){

        try {
            for (Person person : personList) {

                geocoderSetUp(person);
            }

        } catch (Exception e ) {
            e.getStackTrace();
        }
    }


    public void geocoderSetUp(Person person){

        Pattern pattern = Pattern.compile("^\\d+.\\d+,\\d+.\\d+");
        Matcher matcher = pattern.matcher(person.getGeolocation());

        if(matcher.find()) {

            String lat = person.getGeolocation().split(",")[0];
            String lon = person.getGeolocation().split(",")[1];

            String geo = geocoder.GeocodeSync(lat, lon);

            JSONObject jsonBody = new JSONObject(geo);

            String jsonAddressString = jsonBody.getString("display_name");

          if(!person.getText_location().equalsIgnoreCase(jsonAddressString)) {

                System.out.println(!person.getText_location().equalsIgnoreCase(jsonAddressString));
                person.setText_location(jsonAddressString);

                this.updatePerson(person);

            }

        } else {

            person.setText_location(person.getGeolocation());

            this.updatePerson(person);

        }
    }
}
