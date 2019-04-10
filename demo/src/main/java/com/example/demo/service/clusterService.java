package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;
import java.util.*;

@Service
public class clusterService {
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private FloorRepository floorRepository;

    /**
    public List<Sensor> getSensorIDByClusterID(long cluster_id)
    {
        List<Sensor> sensorId_List = new LinkedList<>();
        List<Sensor> sensors = sensorRepository.findSensorByClusterId(cluster_id);
        for(Sensor sensor: sensors) {
            sensorId_List.add(sensor.getId());
        }
        return sensorId_List;
    }*/

    public Cluster updateClusterByClusterID(long cluster_id, Cluster cluster) {
        Cluster clusterFromDB = clusterRepository.findById(cluster_id).get();
        if (!cluster.getName().equals("")) {
            clusterFromDB.setName(cluster.getName());
        }
        if (!cluster.getX_coordinate().equals("") && !cluster.getY_coordinate().equals("")) {
            clusterFromDB.setX_coordinate(cluster.getX_coordinate());
            clusterFromDB.setY_coordinate(cluster.getX_coordinate());
        }
        if (!cluster.getStatus().equals("")) {
            clusterFromDB.setStatus(cluster.getStatus());
        }
        if (!cluster.getSeries_number().equals("")) {
            clusterFromDB.setSeries_number(cluster.getSeries_number());
        }
        clusterRepository.save(clusterFromDB);
        return clusterFromDB;
    }

    public String addClustertoDB(Cluster cluster){
        clusterRepository.save(cluster);
        return cluster.toString();
    }

    public void deleteCluster(long cluster_id, long building_id){
        Iterable<Cluster> clusters = clusterRepository.findClusterByBuildingId(building_id);
        for(Cluster cluster: clusters){
            if(cluster_id == cluster.getId()) {
                clusterRepository.deleteById(cluster.getId());
                nodeRepository.deleteNodeByClusterId(cluster.getId());
                sensorRepository.deleteSensorByClusterId(cluster.getId());
            }
        }
    }

    public String getClusterByClusterId(long cluster_id){
        return clusterRepository.findById(cluster_id).get().toString();
    }

    public String getClusterNestedByClusterId(long cluster_id, String requirement){
        Cluster cluster = clusterRepository.findById(cluster_id).get();
        List<Room> rooms = roomRepository.findRoomByFloorId(cluster.getFloor_id(), cluster.getBuilding_id());
        List<Node> nodes = nodeRepository.findNodeByClusterId(cluster_id);
        List<Sensor> sensors = sensorRepository.findSensorByClusterId(cluster_id);
        clusterNested clusterNest;
        switch(requirement) {
            case "room, node, sensor":
                clusterNest = new clusterNested(cluster,rooms,nodes,sensors);
                clusterNest.toString();
            case "node,sensor":
                clusterNest = new clusterNested(cluster,nodes,sensors);
                return clusterNest.toString();
            case "room":
                clusterNest = new clusterNested(cluster,rooms);
                return clusterNest.toString();
        }
        return clusterRepository.findById(cluster_id).get().toString();
    }

}
