package ua.ukrposhta.person_location.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.ukrposhta.person_location.config.CacheConfig;
import ua.ukrposhta.person_location.geocoder.Geocoder;
import ua.ukrposhta.person_location.model.Person;
import ua.ukrposhta.person_location.repositoriy.PersonRepo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@PropertySource("classpath:properties/site.properties")
public class PersonService {

    @Value("${cron}")
    protected String cron_data = "0 0 0/3 ? * *";
    private CacheConfig ehcacheManager;
    private PersonRepo personRepo;
    private Geocoder geocoder;
    private Logger logger = LoggerFactory.getLogger(PersonService.class);

    private String YOUR_API_KEY = "AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s";

    @Autowired
    public void setEhcacheManager(CacheConfig ehcacheManager) {
        this.ehcacheManager = ehcacheManager;
    }

    @Autowired
    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Autowired
    public void setPersonRepo(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }


    @Cacheable(value = "allPersons", key = "'full_list'")
    public List<Person> findAll(){
        logger.info("Get all persons list from db findAll method PersonService.class");
        return personRepo.findAll();
    }

    private void refreshCache() throws IOException {
        logger.info("Refresh cache start in refreshCache method PersonService.class");

        logger.info("Evict cache with name " + ehcacheManager.cacheManager()
                .getCache("allPersons").getName() + " evict is " +
                ehcacheManager.cacheManager().getCache("allPersons").evictIfPresent("full_list"));

        List<Person> listCache = personRepo.findAll();

        ehcacheManager.cacheManager()
                .getCache("allPersons").put("full_list", listCache);

        logger.info("Refresh cache end");
    }

    //    @CachePut(value = "allPersons", key = "'full_list'")
    public List<Person> findAllSorting(){
        return personRepo.findAll(Sort.by(Direction.DESC, "lastname"));
    }

    public Person getById(Long id){
        logger.info("Get person from db by id = " + id + " / getById method PersonService.class" );
        return personRepo.getById(id);
    }

    @Cacheable(value = "directorate", key = "'list_directorate'")
    public List<String> allDirectorateName(){
        logger.info("Get all directorate name list from db allDirectorateName method PersonService.class");
        return personRepo.allDirectorateName().stream().map(dir -> {
            if(dir.equalsIgnoreCase(""))
                dir = "Не_заповнено";
            return dir;
        } ).collect(Collectors.toList());
    }

    public List<Person> personListByDirectorate(String directorate){
        logger.info("Get all persons list from db by directorate name = " + directorate + " / personListByDirectorate method PersonService.class");
        if(directorate.equalsIgnoreCase("Не_заповнено"))
            directorate = "";

        return personRepo.findPersonByDirectorate(directorate);
    }

//    @Cacheable(value = "persons", key = "#person.id")
    public Person updatePerson(Person person){
        logger.info("Update person data in id = " + person.getId() + " / updatePerson method PersonService.class");
        return personRepo.saveAndFlush(person);
    }

    public List<Person> personByPhone(String phone) {
        logger.info("Get persons list from db by phone = " + phone + " / personByPhone method PersonService.class");
        return personRepo.findPersonByPhone(phone);
    }

    public List<Person> findPersonByLastname(String lastname){
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

    @Scheduled(cron = "${cron}")
    public void updateGeolocationData() throws IOException {
        logger.info("Update geolocation data in db if change by scheduled cron  = " + cron_data + " / updateGeolocationData method PersonService.class");
        checkChangeGeolocationData(Objects.requireNonNull(ehcacheManager.cacheManager().getCache("allPersons")).get("full_list", List.class));
    }

    private void checkChangeGeolocationData(List<Person> personList)throws IOException{

        logger.info("Start checkChangeGeolocationIfDifferentGeolocationOrText_locationData method PersonService.class");

        for (Person person : personList) {
            try {
                if(!person.getGeolocation().equalsIgnoreCase(personRepo.findById(person.getId()).get().getGeolocation()) ||
                        person.getText_location().equalsIgnoreCase("") ) {

                    geocoderSetUp(personRepo.findById(person.getId()).get());
                }

            } catch (Exception e ) {

                logger.error("ERROR in geocoderSetUp method PersonService.class in identify the GEO string : " + Arrays.toString(e.getStackTrace()));

                String  state = "Місце знаходження визначити не вдалося. Перевірте координати.";

                person.setText_location(state);

                person.setState(state);

                person.setLink_geolocation("https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                        + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru");

                this.updatePerson(person);

            }
        }

        refreshCache();

        List<Person> removePersonList = personRepo.findAll();
        removePersonList.removeAll(personList);
        logger.info("Size of list of objects that do not include in cache = " + removePersonList.size());

        for ( Person person : removePersonList) {
            try {

                geocoderSetUp(person);

            } catch (Exception e) {

                logger.error("ERROR in geocoderSetUp method PersonService.class in identify the GEO string : " + Arrays.toString(e.getStackTrace()));

                person.setLink_geolocation("https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                        + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru");

                String state = "Місце знаходження визначити не вдалося. Перевірте координати.";

                person.setText_location(state);

                person.setState(state);

                this.updatePerson(person);

            }
        }

        refreshCache();

        logger.info("------- End to identify of geolocation in checkChangeGeolocationIfDifferentGeolocationOrText_locationData method -------");

    }



    public void geocoderSetUp(Person person){

        Pattern pattern = Pattern.compile("^\\d+.\\d+,\\d+.\\d+|^\\d+.\\d+,-\\d+.\\d+|^-\\d+.\\d+,\\d+.\\d+|^-\\d+.\\d+,-\\d+.\\d+");
        Matcher matcher = pattern.matcher(person.getGeolocation());

        logger.info("Start geocoderSetUp method PersonService.class");
        System.out.println("Start geocoderSetUp method PersonService.class");

        if(matcher.find()) {

            String lat = person.getGeolocation().split(",")[0];
            String lon = person.getGeolocation().split(",")[1];

            String linkGoogleMap ="https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                    + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru";

            String geo = geocoder.GeocodeSync(lat, lon);

            JSONObject jsonBody = new JSONObject(geo);

            System.out.println("---------------------------------------------");

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

                    state = json.getString("city");

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
                person.setState("Місце знаходження визначити не вдалося. Перевірте координати.");
                person.setLink_geolocation("https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                        + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru");

                this.updatePerson(person);

        }
    }
}
