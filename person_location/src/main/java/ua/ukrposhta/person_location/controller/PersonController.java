package ua.ukrposhta.person_location.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukrposhta.person_location.Service.PersonService;
import ua.ukrposhta.person_location.geocoder.Geocoder;
import ua.ukrposhta.person_location.model.Person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/","/person-location-data/","/person-location-data"})
public class PersonController {

    private String YOUR_API_KEY = "AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s";
    List<Person> personListCache = new ArrayList<>();
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    private Geocoder geocoder;

    @Autowired
    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }


    @GetMapping({"","current-date"})
    public ModelAndView startPage(ModelAndView modelAndView){

        List<String> directorateList = personService.allDirectorateName();

        if(personListCache.isEmpty() || personService.findAll().size() <=3 ){
            personListCache = checkChangeGeolocation(personListCache);
        }

        if(personListCache.hashCode() != personService.findAll().hashCode())
            personListCache = checkChangeGeolocation(personListCache);

        addInModelAndView(modelAndView, personListCache, "", directorateList);

        return modelAndView;
    }

    @PostMapping("person-list/by-lastname")
    public ModelAndView listPersonByLastName(@RequestParam(name = "name", required = false) String partLastname,
                                             ModelAndView modelAndView){

        List<String> directorateList = personService.allDirectorateName();
        List<Person> personListDB = personService.findAll();
        List<Person> personList = new ArrayList<>();

        if(personListCache.hashCode() != personListDB.hashCode())
            personListCache = checkChangeGeolocation(personListCache);

        if(!personListCache.isEmpty()){
            for (Person person : personListCache) {
                if(person.getLastname().toLowerCase().contains(partLastname.toLowerCase())){
                    personList.add(person);
                }
            }
        }

        addInModelAndView(modelAndView,personList,"",directorateList);
        return modelAndView;
    }

    @PostMapping("person-list/by-phone")
    public ModelAndView personByPhone(@RequestParam(name = "phone", required = false) String phone,
                                ModelAndView modelAndView){

        List<Person> personListDB = personService.findAll();

        if(personListCache.hashCode() != personListDB.hashCode())
            checkChangeGeolocation(personListCache);

        List<String> directorateList = personService.allDirectorateName();
        List<Person> person = personService.personByPhone(phone);

        addInModelAndView(modelAndView,person,"",directorateList);

        return modelAndView;
    }

    @PostMapping("person-list/by-date")
    public ModelAndView personByDate(@RequestParam(name = "date", required = false) String date,
                                      ModelAndView modelAndView){

        List<String> directorateList = personService.allDirectorateName();
        List<Person> personList = new ArrayList<>();

        LocalDateTime dateStart = LocalDateTime.parse(date + "T00:00:00.00000");
        LocalDateTime dateEnd = LocalDateTime.parse(date + "T23:59:59.99999");

        List<Person> personListDB = personService.findAll();

        if(personListCache.hashCode() != personListDB.hashCode())
            personListCache = checkChangeGeolocation(personListCache);


        for (Person person : personListCache) {
            if (person.getLast_modified().isAfter(dateStart) && person.getLast_modified().isBefore(dateEnd)) {
                personList.add(person);
            }
        }

        addInModelAndView(modelAndView,personList,date,directorateList);

        return modelAndView;
    }

    @GetMapping("person-list/directorate/{directorate}")
    public ModelAndView listAllDirectorateName(@PathVariable(value = "directorate", required = false) String directorate,
                                               ModelAndView modelAndView){

        List<Person> personListDB = personService.findAll();

        if(personListCache.hashCode() != personListDB.hashCode())
            checkChangeGeolocation(personListCache);

        List<String> directorateList = personService.allDirectorateName();
        List<Person> personList = personService.personListByDirectorate(directorate);

        addInModelAndView(modelAndView, personList, "", directorateList);
        return modelAndView;
    }


    private void addInModelAndView(ModelAndView modelAndView, List<Person> personList, String date, List<String> directorateList) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        if(date.isEmpty())
            date = LocalDate.now().format(formatter);
        else
            date = LocalDate.parse(date).format(formatter);

        for (Person person : personList) {

            String linkGoogleMap ="https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                    + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru";

            person.setGeolocation(linkGoogleMap);

        }
        if(directorateList.isEmpty()){
            modelAndView.addObject("emptyDirectorateList", true);
        } else {
            modelAndView.addObject("unitList", directorateList);
            modelAndView.addObject("emptyDirectorateList", false);
        }

        modelAndView.addObject("persons", personList);
        modelAndView.addObject("date", date);
        modelAndView.setViewName("index");


    }

    private List<Person> checkChangeGeolocation(List<Person> personList){

        try {
            if(!personList.isEmpty()) {
                for (Person person : personList) {

                    if (person.getText_location().isEmpty() ||
                            !person.getGeolocation().equalsIgnoreCase(
                                    personService.getById(person.getId()).getGeolocation())) {

                        Person personDB = personService.getById(person.getId());

                       geocoderSetUp(personDB);

                    }
                }
            } else{
                for (Person personDB : personService.findAll()) {

                        geocoderSetUp(personDB);
                    }
            }

            return personService.findAll();

        } catch (Exception e ) {
            e.getStackTrace();
        }

        return personList;
    }

    public void geocoderSetUp(Person person){

        String lat = person.getGeolocation().split(",")[0];
        String lon = person.getGeolocation().split(",")[1];

        String geo = geocoder.GeocodeSync(lat, lon);

        JSONObject jsonBody = new JSONObject(geo);

        String jsonAddressString = jsonBody.getString("display_name");

        person.setText_location(jsonAddressString);

        personService.updatePerson(person);
    }
}
