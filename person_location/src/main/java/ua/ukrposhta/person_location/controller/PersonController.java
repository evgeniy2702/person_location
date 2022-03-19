package ua.ukrposhta.person_location.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukrposhta.person_location.Service.PersonService;
import ua.ukrposhta.person_location.model.Person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping({"/","/person-location-data/","/person-location-data"})
public class PersonController {

    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

//    private Geocoder geocoder;
//
//    @Autowired
//    public void setGeocoder(Geocoder geocoder) {
//        this.geocoder = geocoder;
//    }


    @GetMapping({"","current-date"})
    public ModelAndView startPage(ModelAndView modelAndView){

        addInModelAndView(modelAndView,
                personService.findAll(),
                "",
                personService.allDirectorateName());

        return modelAndView;
    }

    @PostMapping("person-list/by-lastname")
    public ModelAndView listPersonByLastName(@RequestParam(name = "name", required = false) String partLastname,
                                             ModelAndView modelAndView){

        addInModelAndView(modelAndView,personService.findPersonByLastname(partLastname),"",personService.allDirectorateName());
        return modelAndView;
    }

    @PostMapping("person-list/by-phone")
    public ModelAndView personByPhone(@RequestParam(name = "phone", required = false) String phone,
                                ModelAndView modelAndView){

        List<Person> person = personService.personByPhone(phone);

        addInModelAndView(modelAndView,person,"",personService.allDirectorateName());

        return modelAndView;
    }

    @PostMapping("person-list/by-date")
    public ModelAndView personByDate(@RequestParam(name = "date", required = false) String date,
                                      ModelAndView modelAndView){

        LocalDateTime dateStart = LocalDateTime.parse(date + "T00:00:00.00000");
        LocalDateTime dateEnd = LocalDateTime.parse(date + "T23:59:59.99999");

        addInModelAndView(modelAndView,personService.findPersonByLastModified(dateStart, dateEnd),date,personService.allDirectorateName());

        return modelAndView;
    }

    @GetMapping("person-list/directorate/{directorate}")
    public ModelAndView listAllDirectorateName(@PathVariable(value = "directorate", required = false) String directorate,
                                               ModelAndView modelAndView){

        addInModelAndView(modelAndView,
                personService.personListByDirectorate(directorate),
                "",
                personService.allDirectorateName());

        return modelAndView;
    }


    private void addInModelAndView(ModelAndView modelAndView, List<Person> personList, String date, List<String> directorateList) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy");

        if(date.isEmpty())
            date = LocalDate.now().format(formatter);
        else
            date = LocalDate.parse(date).format(formatter);

        for (Person person : personList) {

            String YOUR_API_KEY = "AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s";
            String linkGoogleMap ="https://www.google.com/maps/embed/v1/place?key=" + YOUR_API_KEY + "&q=" + person.getGeolocation()
                    + "&center=" + person.getGeolocation() + "&zoom=10&maptype=roadmap&language=ru";

            person.setLink_geolocation(linkGoogleMap);

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

}
