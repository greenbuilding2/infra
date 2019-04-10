package com.example.demo.model;

import javax.persistence.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String floorplan_url;
    private Integer floor_number;
    private long building_id;
   // private ArrayList<String> sensor_type;

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuilding_id() { return building_id; }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public Integer getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(Integer floor_number) {
        this.floor_number = floor_number;
    }

    public String getFloorplan_url() { return floorplan_url; }

    public void setFloorplan_url(String floorplan_url) { this.floorplan_url = floorplan_url; }

//    public ArrayList<String> getSensor_type() { return sensor_type; }
//
//    public void setSensor_type(ArrayList<String> sensor_type) { this.sensor_type = sensor_type; }


    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }



}
