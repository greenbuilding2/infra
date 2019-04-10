package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@JsonPropertyOrder({
        "node_id",
        "cluster_id",
        "room_id",
        "name",
        "type",
        "series_number",
        "install_time",
        "last_maintenance",
        "status",
        "x_coordinate",
        "y_coordinate"
})
@Entity
@Table(name = "node")
public class Node {
    @Id
    private long id;
    private long cluster_id;
    private long room_id;
    private String name;
    private String type;
    private String series_number;
    private Date install_time;
    private Date last_maintenance;
    private String status;
    private Double x_coordinate;
    private Double y_coordinate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(long cluster_id) {
        this.cluster_id = cluster_id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
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
