package com.example.demo.nested;

import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;


public class roomNested {

    private long id;
    private long floor_id;
    private long building_id;
    private Integer room_number;
    private Node node;
    private List<Sensor> sensors;

    public roomNested(Room room, Node node, List<Sensor> sensors) {
        this.id = room.getId();
        this.floor_id = room.getFloor_id();
        this.building_id = room.getBuilding_id();
        this.room_number = room.getRoom_number();
        this.node = node;
        this.sensors = sensors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(long floor_id) {
        this.floor_id = floor_id;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public Integer getRoom_number() {
        return room_number;
    }

    public void setRoom_number(Integer room_number) {
        this.room_number = room_number;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

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
