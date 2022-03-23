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

    public List<Person> findPersonByVacation(boolean vacation){
        return personRepo.findPersonByVacation(vacation);
    }

    public List<Person> findPersonByRefugee(boolean refugee){
        return personRepo.findPersonByRefugee(refugee);
    }

    public List<Person> findPersonByState(String state){
        return personRepo.findPersonByState(state);
    }

    public List<Person> findPersonByAbleForWork(boolean able_for_work){
        return personRepo.findPersonByAble_for_work(able_for_work);
    }

    public List<Person> findPersonByWorkRemote(boolean work_remote){
        return personRepo.findPersonByWork_remote(work_remote);
    }

    public List<Person> findPersonByWorkByPlace(boolean work_by_place){
        return personRepo.findPersonByWork_by_place(work_by_place);
    }

    public List<Person> findPersonByWarZone(boolean war_zone){
        return personRepo.findPersonByWar_zone(war_zone);
    }

    public List<String> allStatesList(){
        return personRepo.allStatesList();
    }

    // update data_table data in column text_location every hour if change geolocation data
    @Scheduled(cron = "0 0 * ? * *")
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

            System.out.println(Arrays.toString(e.getStackTrace()));
            e.getStackTrace();

        }
    }


    public void geocoderSetUp(Person person){

        Pattern pattern = Pattern.compile("^\\d+.\\d+,\\d+.\\d+|^\\d+.\\d+,-\\d+.\\d+|^-\\d+.\\d+,\\d+.\\d+|^-\\d+.\\d+,-\\d+.\\d+");
        Matcher matcher = pattern.matcher(person.getGeolocation());

        System.out.println("enter");

        if(matcher.find()) {

            String lat = person.getGeolocation().split(",")[0];
            String lon = person.getGeolocation().split(",")[1];

            String YOUR_API_KEY = "AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s";
            String linkGoogleMap ="https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                    + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru";

            String geo = geocoder.GeocodeSync(lat, lon);

            JSONObject jsonBody = new JSONObject(geo);

            String addressString = jsonBody.getString("display_name");

            System.out.println("---------------------------------------------");
            System.out.println("addressString " + addressString);

            JSONObject json = jsonBody.getJSONObject("address");

            String state = "";

            if( json.keySet().contains("state")) {

                state = json.getString("state");
                System.out.println("---------------------------------------------");
                System.out.println("state " + state);

            } else if(json.keySet().contains("city")){

                state = "м." + json.getString("city");
                System.out.println("---------------------------------------------");
                System.out.println("city " + state);

            } else if(json.keySet().contains("town")){

                state = "м." + json.getString("town") + " обл. неизвестна";
                System.out.println("---------------------------------------------");
                System.out.println("town " + state);

            } else if(json.keySet().contains("district")){

                state = json.getString("district") + " обл. неизвестна";
                System.out.println("---------------------------------------------");
                System.out.println("district " + state);

            } else {

                state = json.getString("country");
                System.out.println("---------------------------------------------");
                System.out.println("country " + state);

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

            if(!addressString.equalsIgnoreCase(person.getText_location()) ||
                    person.getText_location().isEmpty() ||
                    person.getText_location().equalsIgnoreCase("")) {

                person.setText_location(addressString);

                this.updatePerson(person);

                }

            } else {

                person.setText_location(person.getGeolocation());

                this.updatePerson(person);

        }
    }
}
