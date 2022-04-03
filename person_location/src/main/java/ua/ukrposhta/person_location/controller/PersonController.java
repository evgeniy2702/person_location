package ua.ukrposhta.person_location.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ukrposhta.person_location.service.PersonService;
import ua.ukrposhta.person_location.model.Person;
import ua.ukrposhta.person_location.utils.ConsoleLogger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    public ModelAndView filter(@RequestParam(name = "name_form", required = false) String name_form,
                               @RequestParam(name = "vacation", required = false) boolean vacation,
                               @RequestParam(name = "refugee", required = false) boolean refugee,
                               @RequestParam(name = "able_for_work", required = false) boolean able_for_work,
                               @RequestParam(name = "work_remote", required = false) boolean work_remote,
                               @RequestParam(name = "work_by_place", required = false) boolean work_by_place,
                               @RequestParam(name = "war_zone", required = false) boolean war_zone,
                               @RequestParam(name = "border", required = false) boolean border,
                               @RequestParam(name = "region", required = false) String region,
                               @RequestParam(name = "dir", required = false) String directorate,
                               ModelAndView modelAndView
                               ){

        logger.info("Start filter method in PersonController.class");

        List<Person> personList = null;

        personList = createFiltering( personList, name_form, vacation, refugee, able_for_work,
                work_remote, work_by_place, war_zone, border, region, directorate);

        addInModelAndView(modelAndView,personList,1L,20L,"",personService.allDirectorateName(),
                createListFilterParam(name_form, vacation, refugee, able_for_work,
                                      work_remote, work_by_place, war_zone, border,
                                      region, directorate));

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
        if(filterParams != null)
            modelAndView.addObject("caption", "Застосовано наступний фільтр : ");
        modelAndView.addObject("persons", personList);
        modelAndView.addObject("date", date);
        modelAndView.setViewName("index");

    }

    private List<Person> createFiltering(List<Person> personList, String name, boolean vacation, boolean refugee,
                                         boolean able_for_work, boolean work_remote, boolean work_by_place,
                                         boolean war_zone, boolean border, String region, String directorate) {

        logger.info("Start createFiltering method for filter info about persons in PersonController.class");

        if(!name.isEmpty() || !name.equalsIgnoreCase(""))
            personList = personService.findPersonByLastname(name);

        if (vacation)
            if (personList != null) {

                personList = personList.stream().map(person -> {
                    if(person.isVacation() == vacation)
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByVacation(vacation);
            }

        if (refugee)
            if (personList != null) {
                personList = personList.stream().map(person -> {
                    if(person.isRefugee() == refugee)
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());

//                personList = personList.stream().filter(person -> person.isRefugee() == refugee).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByRefugee(refugee);
            }

        if (able_for_work)
            if (personList != null) {

                personList = personList.stream().map(person -> {
                    if(person.isAble_for_work() == able_for_work)
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
                if (work_remote)
                    personList = personList.stream().map(person -> {
                        if(person.isWork_remote() == work_remote)
                            return person;
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                if (work_by_place)
                    personList = personList.stream().map(person -> {
                        if(person.isWork_by_place() == work_by_place)
                            return person;
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByAbleForWork(able_for_work);
            }

        if (work_remote)
            if (personList != null) {
                personList = personList.stream().map(person -> {
                    if(person.isWork_remote() == work_remote)
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByWorkRemote(work_remote);
            }

        if (work_by_place)
            if (personList != null) {
                personList = personList.stream().map(person -> {
                    if(person.isWork_by_place() == work_by_place)
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByWorkByPlace(work_by_place);
            }

        if (war_zone)
            if (personList != null) {
                personList = personList.stream().map(person -> {
                    if(person.isWar_zone() == war_zone)
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByWarZone(war_zone);
            }

        if(border)
            if(personList != null){
                personList = personList.stream().map(person -> {
                    if(!person.getText_location().split(",")[person.getText_location()
                            .split(",").length - 1].equalsIgnoreCase(" Україна"))
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findAll().stream()
                        .filter(person -> !person.getText_location().split(",")[person.getText_location()
                                .split(",").length - 1].equalsIgnoreCase(" Україна"))
                        .collect(Collectors.toList());
            }
        if (!region.isEmpty() || !region.equalsIgnoreCase(""))
            if (personList != null) {
                personList = personList.stream().map(person -> {
                    if(person.getState().equalsIgnoreCase(region))
                        return person;
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
            } else {
                personList = personService.findPersonByState(region);
            }

        if (!directorate.isEmpty() || !directorate.equalsIgnoreCase(""))
            if (personList != null) {
                if(directorate.equalsIgnoreCase("Не_заповнено")) {
                    personList = personList.stream().map(person -> {
                        if(person.getDirectorate().length() == 0)
                            return person;
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                } else {
                    personList = personList.stream().map(person -> {
                        if(person.getDirectorate().equalsIgnoreCase(directorate))
                            return person;
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }
            } else {
                personList = personService.personListByDirectorate(directorate);
            }
        return personList;
    }


    private List<String> createListFilterParam(String name, boolean vacation, boolean refugee,
                                               boolean able_for_work, boolean work_remote, boolean work_by_place,
                                               boolean war_zone, boolean border, String region, String directorate){

        logger.info("Start createListFilterParam method for show filter params on tje index.html page in PersonController.class");

        List<String> filterParams = new ArrayList<>();
        if(!name.isEmpty() || !name.equalsIgnoreCase(""))
            filterParams.add("Прізвище : " + name);

        if(vacation) {
            filterParams.add("Відпустка : так");
        }

        if(refugee) {
            filterParams.add("Біженець : так");
        }

        if(able_for_work) {
            filterParams.add("Може працювати : так");
        }

        if(work_remote) {
            filterParams.add("Віддаленно : так");
        }

        if(work_by_place) {
            filterParams.add("За місцем роботи : так");
        }

        if(war_zone) {
            filterParams.add("Зона бойових дій : так");
        }

        if(border) {
            filterParams.add("За кордоном : так");
        }

        if(! region.isEmpty() ||
                !region.equalsIgnoreCase("")) {
            filterParams.add("Регіон : " + region);
        }

        if(!directorate.isEmpty() ||
                !directorate.equalsIgnoreCase("")) {
            filterParams.add("Дірекція : " + directorate);
        }


        return filterParams;
    }

}
