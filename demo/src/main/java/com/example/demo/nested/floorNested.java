package com.example.demo.nested;

import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class floorNested {

    private long id;
    private long building_id;
    private Integer floor_number;

    private Cluster cluster;
    private List<Room> rooms;
    private List<Node> nodes;
    private List<Sensor> sensors;

    public floorNested(Floor floor, List<Room> rooms, List<Node> nodes) {
        this.id = floor.getId();
        this.building_id = floor.getBuilding_id();
        this.floor_number = floor.getFloor_number();
        this.rooms = rooms;
        this.nodes = nodes;
    }

    public floorNested(Floor floor, Cluster cluster, List<Room> rooms, List<Node> nodes, List<Sensor> sensors) {
        this.id = floor.getId();
        this.building_id = floor.getBuilding_id();
        this.floor_number = floor.getFloor_number();
        this.cluster = cluster;
        this.rooms = rooms;
        this.nodes = nodes;
        this.sensors = sensors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public Integer getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(Integer floor_number) {
        this.floor_number = floor_number;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
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
