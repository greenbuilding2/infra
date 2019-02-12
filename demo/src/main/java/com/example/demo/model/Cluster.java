package com.example.demo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;

@JsonPropertyOrder({
        "cluster_id",
        "name",
        "type",
        "series_number",
        "install_time",
        "status",
        "building_id",
        "floor_id",
        "x_coordinate",
        "y_coordinate"
})

@Entity
@Table(name = "cluster")
public class Cluster {
    @Id
    private long id;
    private String name;
    private String type;
    private String series_number;
    private Date install_time;
    private String status;
    private long building_id;
    private long floor_id;
    private Double x_coordinate;
    private Double y_coordinate;

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

    public Double getX_coordinate() { return x_coordinate; }

    public void setX_coordinate(Double x_coordinate) { this.x_coordinate = x_coordinate; }

    public Double getY_coordinate() { return y_coordinate; }

    public void setY_coordinate(Double y_coordinate) { this.y_coordinate = y_coordinate; }



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
