package com.example.demo.nested;

import com.example.demo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

public class clusterNested {
    private long id;
    private String name;
    private String type;
    private String series_number;
    private Date install_time;
    private Date last_maintenance;
    private String status;
    private long building_id;
    private long floor_id;
    private Double x_coordinate;
    private Double y_coordinate;

    private List<Room> rooms;
    private List<Node> nodes;
    private List<Sensor> sensors;

    public clusterNested(Cluster cluster, List<Room> rooms, List<Node> nodes, List<Sensor> sensors) {
        this.id = cluster.getId();
        this.building_id = cluster.getBuilding_id();
        this.floor_id = cluster.getFloor_id();
        this.name = cluster.getName();
        this.type = cluster.getType();
        this.series_number = cluster.getSeries_number();
        this.install_time = cluster.getInstall_time();
        this.last_maintenance = cluster.getLast_maintenance();
        this.status = cluster.getStatus();
        this.x_coordinate = cluster.getX_coordinate();
        this.y_coordinate = cluster.getY_coordinate();
        this.rooms = rooms;
        this.nodes = nodes;
        this.sensors = sensors;
    }

    public clusterNested(Cluster cluster, List<Room> rooms) {
        this.id = cluster.getId();
        this.building_id = cluster.getBuilding_id();
        this.floor_id = cluster.getFloor_id();
        this.name = cluster.getName();
        this.type = cluster.getType();
        this.series_number = cluster.getSeries_number();
        this.install_time = cluster.getInstall_time();
        this.last_maintenance = cluster.getLast_maintenance();
        this.status = cluster.getStatus();
        this.rooms = rooms;
        this.x_coordinate = cluster.getX_coordinate();
        this.y_coordinate = cluster.getY_coordinate();
    }

    public clusterNested(Cluster cluster, List<Node> nodes, List<Sensor> sensors) {
        this.id = cluster.getId();
        this.building_id = cluster.getBuilding_id();
        this.floor_id = cluster.getFloor_id();
        this.name = cluster.getName();
        this.type = cluster.getType();
        this.series_number = cluster.getSeries_number();
        this.install_time = cluster.getInstall_time();
        this.last_maintenance = cluster.getLast_maintenance();
        this.status = cluster.getStatus();
        this.x_coordinate = cluster.getX_coordinate();
        this.y_coordinate = cluster.getY_coordinate();
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

    public long getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(long floor_id) {
        this.floor_id = floor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeries_number() {
        return series_number;
    }

    public void setSeries_number(String series_number) {
        this.series_number = series_number;
    }

    public Date getInstall_time() {
        return install_time;
    }

    public void setInstall_time(Date install_time) {
        this.install_time = install_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Double getX_coordinate() { return x_coordinate; }

    public void setX_coordinate(Double x_coordinate) { this.x_coordinate = x_coordinate; }

    public Double getY_coordinate() { return y_coordinate; }

    public void setY_coordinate(Double y_coordinate) { this.y_coordinate = y_coordinate; }

    public Date getLast_maintenance() { return last_maintenance; }

    public void setLast_maintenance(Date last_maintenance) { this.last_maintenance = last_maintenance; }

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