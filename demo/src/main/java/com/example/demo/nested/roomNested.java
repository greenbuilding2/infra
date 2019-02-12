package com.example.demo.nested;

import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;


public class roomNested {

    private long id;
    private String roomImage_url;
    private Integer room_number;
    private long floor_id;
    private long building_id;
    private Double x_coordinate_start;
    private Double x_coordinate_end;
    private Double y_coordinate_start;
    private Double y_coordiante_end;
    private List<Node> nodes;
    private List<Sensor> sensors;

    public roomNested(Room room, List<Node> nodes, List<Sensor> sensors) {
        this.id = room.getId();
        this.floor_id = room.getFloor_id();
        this.building_id = room.getBuilding_id();
        this.room_number = room.getRoom_number();
        this.nodes = nodes;
        this.sensors = sensors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomImage_url() { return roomImage_url; }

    public void setRoomImage_url(String roomImage_url) { this.roomImage_url = roomImage_url; }

    public Integer getRoom_number() {
        return room_number;
    }

    public void setRoom_number(Integer room_number) {
        this.room_number = room_number;
    }

    public Double getX_coordinate_start() { return x_coordinate_start; }

    public void setX_coordinate_start(Double x_coordinate_start) { this.x_coordinate_start = x_coordinate_start; }

    public Double getX_coordinate_end() { return x_coordinate_end; }

    public void setX_coordinate_end(Double x_coordinate_end) { this.x_coordinate_end = x_coordinate_end; }

    public Double getY_coordinate_start() { return y_coordinate_start; }

    public void setY_coordinate_start(Double y_coordinate_start) { this.y_coordinate_start = y_coordinate_start; }

    public Double getY_coordiante_end() { return y_coordiante_end; }

    public void setY_coordiante_end(Double y_coordiante_end) { this.y_coordiante_end = y_coordiante_end; }

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

    public List<Node> getNode() {
        return nodes;
    }

    public void setNode(List<Node> nodes) {
        this.nodes = nodes;
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
