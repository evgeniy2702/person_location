package ua.ukrposhta.person_location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String middle_name;
    private String phone;
    private String geolocation;
    private LocalDateTime date_add;
    private String department;
    private LocalDateTime last_modified;
    private String directorate;
    private String text_location;
    private String link_geolocation;
    private boolean vacation;
    private boolean refugee;
    private String state;
    private boolean war_zone;
    private boolean work_by_place;
    private boolean work_remote;
    private boolean able_for_work;
    private String user_messenger_id;
    private boolean not_provided_geo_too_long;
    private boolean on_occupied_territory;


}
