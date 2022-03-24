package ua.ukrposhta.person_location.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukrposhta.person_location.Service.PersonService;
import ua.ukrposhta.person_location.model.Person;
import ua.ukrposhta.person_location.utils.ConsoleLogger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/","/person-location-data-filter/","/person-location-data-filter"})
public class PersonController {

    private ConsoleLogger logger = ConsoleLogger.getInstance();
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping({"","current-date"})
    public ModelAndView startPage(ModelAndView modelAndView){

        logger.info("Start startPage method in PersonController.class");

        addInModelAndView(modelAndView,
                personService.findAll(),
                1L,20L,"",
                personService.allDirectorateName(), null);

        return modelAndView;
    }

    @PostMapping("person-list/by-lastname")
    public ModelAndView listPersonByLastName(@RequestParam(name = "name", required = false) String partLastname,
                                             ModelAndView modelAndView){

        logger.info("Start listPersonByLastName method in PersonController.class");

        addInModelAndView(modelAndView,personService.findPersonByLastname(partLastname),
                1L,20L,"",
                personService.allDirectorateName(), Collections.singletonList("за прізвищем"));
        return modelAndView;
    }

    @PostMapping("person-list/by-phone")
    public ModelAndView personByPhone(@RequestParam(name = "phone", required = false) String phone,
                                ModelAndView modelAndView){

        logger.info("Start personByPhone method in PersonController.class");


        List<Person> person = personService.personByPhone(phone);

        addInModelAndView(modelAndView,person,1L,20L,"",
                personService.allDirectorateName(),
                Collections.singletonList("за номером телефону"));

        return modelAndView;
    }

    @PostMapping("person-list/by-date")
    public ModelAndView personByDate(@RequestParam(name = "date", required = false) String date,
                                      ModelAndView modelAndView){

        logger.info("Start personByDate method in PersonController.class");

        LocalDateTime dateStart = LocalDateTime.parse(date + "T00:00:00.00000");
        LocalDateTime dateEnd = LocalDateTime.parse(date + "T23:59:59.99999");

        addInModelAndView(modelAndView,personService.findPersonByLastModified(dateStart, dateEnd),1L,20L,
                date,personService.allDirectorateName(), Collections.singletonList(" за датою " + date));

        return modelAndView;
    }

    @GetMapping("person-list/directorate/{directorate}")
    public ModelAndView listAllDirectorateName(@PathVariable(value = "directorate", required = false) String directorate,
                                               ModelAndView modelAndView){

        logger.info("Start listAllDirectorateName method in PersonController.class");

        addInModelAndView(modelAndView,
                personService.personListByDirectorate(directorate),
                1L,20L,"",
                personService.allDirectorateName(), Collections.singletonList(" за департаментом " + directorate));

        return modelAndView;
    }

    @PostMapping("refugee-vacation-dangerous-region")
    public ModelAndView filter(@RequestParam(name = "vacation", required = false) boolean vacation,
                               @RequestParam(name = "refugee", required = false) boolean refugee,
                               @RequestParam(name = "able_for_work", required = false) boolean able_for_work,
                               @RequestParam(name = "work_remote", required = false) boolean work_remote,
                               @RequestParam(name = "work_by_place", required = false) boolean work_by_place,
                               @RequestParam(name = "war_zone", required = false) boolean war_zone,
                               @RequestParam(name = "region", required = false) String region,
                               ModelAndView modelAndView
                               ){

        logger.info("Start filter method in PersonController.class");

        List<Person> personList = null;

        personList = createFiltering( personList, vacation, refugee, able_for_work,
                work_remote, work_by_place, war_zone, region);

        addInModelAndView(modelAndView,personList,1L,20L,"",personService.allDirectorateName(),
                createListFilterParam(vacation, refugee, able_for_work, work_remote, work_by_place, war_zone, region));

        return modelAndView;
    }

    private void addInModelAndView(ModelAndView modelAndView, List<Person> personList, Long start, Long end,
                                   String date, List<String> directorateList, List<String> filterParams) {

        logger.info("Start addInModelAndView method in PersonController.class");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy");

        if(date.isEmpty())
            date = LocalDate.now().format(formatter);
        else
            date = LocalDate.parse(date).format(formatter);

        if(directorateList.isEmpty()){
            modelAndView.addObject("emptyDirectorateList", true);
        } else {
            modelAndView.addObject("unitList", directorateList);
            modelAndView.addObject("emptyDirectorateList", false);
        }

        modelAndView.addObject("start" , start);
        modelAndView.addObject("end", end);
        modelAndView.addObject("state_list",personService.allStatesList());
        modelAndView.addObject("filter_params", filterParams);
        modelAndView.addObject("persons", personList);
        modelAndView.addObject("date", date);
        modelAndView.setViewName("index");

    }

    private List<Person> createFiltering(List<Person> personList, boolean vacation, boolean refugee,
                                         boolean able_for_work, boolean work_remote, boolean work_by_place,
                                         boolean war_zone, String region) {

        logger.info("Start createFiltering method for filter info about persons in PersonController.class");

        if (vacation)
            personList = personService.findPersonByVacation(vacation);

        if (refugee)
            if (personList != null) {
                personList = personList.stream().filter(person -> person.isRefugee() == refugee).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByRefugee(refugee);
            }

        if (able_for_work)
            if (personList != null) {
                personList = personList.stream().filter(person -> person.isAble_for_work() == able_for_work).collect(Collectors.toList());
                if (work_remote)
                    personList = personList.stream().filter(person -> person.isWork_remote() == work_remote).collect(Collectors.toList());

                if (work_by_place)
                    personList = personList.stream().filter(person -> person.isWork_by_place() == work_by_place).collect(Collectors.toList());

            } else {
                personList = personService.findPersonByAbleForWork(able_for_work);
            }

        if (work_remote)
            if (personList != null) {
                personList = personList.stream().filter(person -> person.isWork_remote() == work_remote).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByWorkRemote(work_remote);
            }

        if (work_by_place)
            if (personList != null) {
                personList = personList.stream().filter(person -> person.isWork_by_place() == work_by_place).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByWorkByPlace(work_by_place);
            }

        if (war_zone)
            if (personList != null) {
                personList = personList.stream().filter(person -> person.isWar_zone() == war_zone).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByWarZone(war_zone);
            }

        if (!region.isEmpty() ||
                !region.equalsIgnoreCase(""))

            if (personList != null) {
                personList = personList.stream().filter(person -> person.getState().equalsIgnoreCase(region)).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByState(region);
            }

        return personList;
    }


    private List<String> createListFilterParam(boolean vacation, boolean refugee,
                                               boolean able_for_work, boolean work_remote, boolean work_by_place,
                                               boolean war_zone, String region){

        logger.info("Start createListFilterParam method for show filter params on tje index.html page in PersonController.class");

        List<String> filterParams = new ArrayList<>();
        if(vacation) {
            filterParams.add("Відпустка : так;");
        } else {
            filterParams.add("Відпустка : ні;");
        }

        if(refugee) {
            filterParams.add("Біженець : так;");
        } else {
            filterParams.add("Біженець : ні;");
        }

        if(able_for_work) {
            filterParams.add("Може працювати : так;");
        } else {
            filterParams.add("Може працювати : ні;");
        }

        if(work_remote) {
            filterParams.add("Віддаленно : так;");
        } else {
            filterParams.add("Віддаленно : ні;");
        }

        if(work_by_place) {
            filterParams.add("За місцем роботи : так;");
        } else {
            filterParams.add("За місцем роботи : ні;");
        }

        if(war_zone) {
            filterParams.add("Зона бойових дій : так;");
        } else {
            filterParams.add("Зона бойвих дій : ні;");
        }

        if(! region.isEmpty() ||
                !region.equalsIgnoreCase("")) {

            filterParams.add("Регіон : " + region + ";");
        }


        return filterParams;
    }

}
