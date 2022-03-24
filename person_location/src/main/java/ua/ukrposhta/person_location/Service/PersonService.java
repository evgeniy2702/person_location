package ua.ukrposhta.person_location.Service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.ukrposhta.person_location.geocoder.Geocoder;
import ua.ukrposhta.person_location.model.Person;
import ua.ukrposhta.person_location.repositoriy.PersonRepo;
import ua.ukrposhta.person_location.utils.ConsoleLogger;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    private ConsoleLogger logger = ConsoleLogger.getInstance();

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
        logger.info("Get all persons list from db findAll method PersonService.class");
        return personRepo.findAll();
    }


    public Person getById(Long id){
        log.info("get person from db by id = {}", id);
        logger.info("Get person from db by id = " + id + " / getById method PersonService.class" );
        return personRepo.getPersonById(id);
    }

//    @Cacheable(value = "directorate")
    public List<String> allDirectorateName(){
        logger.info("Get all directorate name list from db allDirectorateName method PersonService.class");
        return personRepo.allDirectorateName();
    }

    public List<Person> personListByDirectorate(String directorate){
        logger.info("Get all persons list from db by directorate name = " + directorate + " / personListByDirectorate method PersonService.class");
        return personRepo.findPersonByDirectorate(directorate);
    }

//    @CacheEvict(value = "persons")
    public Person updatePerson(Person person){
        log.info("Update person : {}", person.getGeolocation());
        logger.info("Update person data in id = " + person.getId() + " / updatePerson method PersonService.class");
        return personRepo.saveAndFlush(person);
    }

    public List<Person> personByPhone(String phone) {
        logger.info("Get persons list from db by phone = " + phone + " / personByPhone method PersonService.class");
        return personRepo.findPersonByPhone(phone);
    }

    public List<Person> findPersonByLastname(String lastname){
        log.info("get person from db by partname = {}", lastname);
        logger.info("Get persons list from db by part of lastname = " + lastname + " / findPersonByLastname method PersonService.class");
        return personRepo.findPersonByLastname(lastname);
    }

    public List<Person> findPersonByLastModified(LocalDateTime dateStart, LocalDateTime dateEnd){
        logger.info("Get persons list from db by last_modified dateStart = " + dateStart + " dateEnd = " + dateEnd + " / findPersonByLastModified method PersonService.class");
        return personRepo.findPersonByLast_modified(dateStart, dateEnd);
    }

    public List<Person> findPersonByVacation(boolean vacation){
        logger.info("Get persons list from db by vocation = " + vacation + " / findPersonByVacation method PersonService.class");
        return personRepo.findPersonByVacation(vacation);
    }

    public List<Person> findPersonByRefugee(boolean refugee){
        logger.info("Get persons list from db by refugee = " + refugee + " / findPersonByRefugee method PersonService.class");
        return personRepo.findPersonByRefugee(refugee);
    }

    public List<Person> findPersonByState(String state){
        logger.info("Get persons list from db by state = " + state + " / findPersonByState method PersonService.class");
        return personRepo.findPersonByState(state);
    }

    public List<Person> findPersonByAbleForWork(boolean able_for_work){
        logger.info("Get persons list from db by able_for_work = " + able_for_work + " / findPersonByAbleForWork method PersonService.class");
        return personRepo.findPersonByAble_for_work(able_for_work);
    }

    public List<Person> findPersonByWorkRemote(boolean work_remote){
        logger.info("Get persons list from db by work_remote = " + work_remote + " / findPersonByWorkRemote method PersonService.class");
        return personRepo.findPersonByWork_remote(work_remote);
    }

    public List<Person> findPersonByWorkByPlace(boolean work_by_place){
        logger.info("Get persons list from db by work_by_place = " + work_by_place + " / findPersonByWorkByPlace method PersonService.class");
        return personRepo.findPersonByWork_by_place(work_by_place);
    }

    public List<Person> findPersonByWarZone(boolean war_zone){
        logger.info("Get persons list from db by war_zone = " + war_zone + " / findPersonByWarZone method PersonService.class");
        return personRepo.findPersonByWar_zone(war_zone);
    }

    public List<String> allStatesList(){
        logger.info("Get states list from db / allStatesList method PersonService.class");
        return personRepo.allStatesList();
    }

    // update data_table data in column text_location every hour if change geolocation data
    @Scheduled(cron = "0 0 * ? * *")
    public void updateGeolocationData(){
        log.info("update geolocation data");
        logger.info("Update geolocation data in db if change by scheduled cron  = " + cron_data + " / updateGeolocationData method PersonService.class");
        checkChangeGeolocation(personRepo.findAll());
    }

    private void checkChangeGeolocation(List<Person> personList){

        logger.info("Start checkChangeGeolocation method PersonService.class");

            for (Person person : personList) {
                try {

                    geocoderSetUp(person);

                } catch (Exception e ) {

                    logger.error("ERROR : " + Arrays.toString(e.getStackTrace()));
                    System.out.println(Arrays.toString(e.getStackTrace()));
                    e.getStackTrace();

                }
            }


    }


    public void geocoderSetUp(Person person){

        Pattern pattern = Pattern.compile("^\\d+.\\d+,\\d+.\\d+|^\\d+.\\d+,-\\d+.\\d+|^-\\d+.\\d+,\\d+.\\d+|^-\\d+.\\d+,-\\d+.\\d+");
        Matcher matcher = pattern.matcher(person.getGeolocation());

        logger.info("Start geocoderSetUp method PersonService.class");
        System.out.println("Start geocoderSetUp method PersonService.class");

        if(matcher.find()) {

            String lat = person.getGeolocation().split(",")[0];
            String lon = person.getGeolocation().split(",")[1];

            String YOUR_API_KEY = "AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s";
            String linkGoogleMap ="https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                    + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru";

            String geo = geocoder.GeocodeSync(lat, lon);

            JSONObject jsonBody = new JSONObject(geo);

            System.out.println("---------------------------------------------");
            System.out.println("jsonBody " + jsonBody);

            logger.info("---------------------------------------------");
            logger.info("jsonBody " + jsonBody);

            String state = "";

            if(!jsonBody.keySet().contains("error")) {
                String addressString = jsonBody.getString("display_name");

                System.out.println("---------------------------------------------");
                System.out.println("addressString " + addressString);

                logger.info("---------------------------------------------");
                logger.info("addressString " + addressString);

                JSONObject json = jsonBody.getJSONObject("address");

                if (json.keySet().contains("state")) {

                    state = json.getString("state");

                    System.out.println("---------------------------------------------");
                    System.out.println("state " + state);

                    logger.info("---------------------------------------------");
                    logger.info("state " + state);

                } else if (json.keySet().contains("city")) {

                    state = "м." + json.getString("city");

                    System.out.println("---------------------------------------------");
                    System.out.println("city " + state);

                    logger.info("---------------------------------------------");
                    logger.info("city " + state);

                } else if (json.keySet().contains("town")) {

                    state = "м." + json.getString("town") + " обл. неизвестна";

                    System.out.println("---------------------------------------------");
                    System.out.println("town " + state);

                    logger.info("---------------------------------------------");
                    logger.info("town " + state);

                } else if (json.keySet().contains("district")) {

                    state = json.getString("district") + " обл. неизвестна";

                    System.out.println("---------------------------------------------");
                    System.out.println("district " + state);

                    logger.info("---------------------------------------------");
                    logger.info("district " + state);

                } else if (json.keySet().contains("country")) {

                    state = json.getString("country");

                    System.out.println("---------------------------------------------");
                    System.out.println("country " + state);

                    logger.info("---------------------------------------------");
                    logger.info("country " + state);


                } else {

                    state = "Місце знаходження визначити не вдалося. Перевірте координати.";

                    logger.info("---------------------------------------------");
                    logger.info("Місце знаходження визначити не вдалося. Перевірте координати.");
                }

                if(!addressString.equalsIgnoreCase(person.getText_location()) ||
                        person.getText_location().isEmpty() ||
                        person.getText_location().equalsIgnoreCase("")) {

                    person.setText_location(addressString);

                    this.updatePerson(person);

                }

            } else {

                state = "Місце знаходження визначити не вдалося. Перевірте координати.";

                logger.info("---------------------------------------------");
                logger.info("Місце знаходження визначити не вдалося. Перевірте координати.");
            }

            if(!state.equalsIgnoreCase(person.getState()) ||
                person.getState().isEmpty() ||
                person.getState().equalsIgnoreCase("") ||
                !linkGoogleMap.equalsIgnoreCase(person.getLink_geolocation()) ||
                person.getLink_geolocation().isEmpty() ||
                person.getLink_geolocation().equalsIgnoreCase("")){

                person.setLink_geolocation(linkGoogleMap);
                person.setState(state);

                this.updatePerson(person);
            }

            } else {

                person.setText_location(person.getGeolocation());

                this.updatePerson(person);

        }
    }
}
