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

    public List<Long> getSensorIDByClusterID(String clusterID)
    {
        long cluster_id = Long.valueOf(clusterID).longValue();
        List<Long> sensorId_List = new LinkedList<>();
        List<Sensor> sensors = sensorRepository.findSensorByClusterId(cluster_id);
        for(Sensor sensor: sensors) {
            sensorId_List.add(sensor.getId());
        }
        return sensorId_List;
    }

    public Cluster updateClusterByClusterID(String clusterid, Cluster clusterOld) {
        long cluster_id = Long.valueOf(clusterid).longValue();
        Cluster clusterFromDB = clusterRepository.findById(cluster_id).get();
        if (clusterOld.getName() != null) {
            clusterFromDB.setName(clusterOld.getName());
        }

        if (clusterOld.getStatus() != null) {
            clusterFromDB.setStatus(clusterOld.getStatus());
            List<Node> nodes = nodeRepository.findNodeByClusterId(cluster_id);
            for(Node node: nodes) {
                node.setStatus(clusterOld.getStatus());
                nodeRepository.save(node);
                List<Sensor> sensors = sensorRepository.findSensorByNodeId(node.getId());
                for(Sensor sensor: sensors) {
                    sensor.setStatus(clusterOld.getStatus());
                    sensorRepository.save(sensor);
                }
            }
        }
        clusterRepository.save(clusterFromDB);
        return clusterFromDB;
    }

    public String addClustertoDB(Cluster cluster){
        clusterRepository.save(cluster);
        return cluster.toString();
    }

    public void deleteCluster(Long cluster_id){
        Long clusterId = Long.valueOf(cluster_id).longValue();

        Iterable<Cluster> clusters = clusterRepository.findAll();
        for(Cluster cluster: clusters){
            if(clusterId == cluster.getId()) {
                clusterRepository.deleteById(cluster.getId());
                nodeRepository.deleteNodeByClusterId(cluster.getId());
                sensorRepository.deleteSensorByClusterId(cluster.getId());
            }
        }
    }

    public String getClusterByClusterId(long cluster_id){
        Long clusterId = Long.valueOf(cluster_id).longValue();
        return clusterRepository.findById(clusterId).get().toString();
    }

    public String getClusterNestedByClusterId(long cluster_id, String requirement){
        Long clusterId = Long.valueOf(cluster_id).longValue();
        Cluster cluster = clusterRepository.findById(clusterId).get();
        List<Room> rooms = roomRepository.findRoomByFloorId(cluster.getFloor_id());
        List<Node> nodes = nodeRepository.findNodeByClusterId(clusterId);
        List<Sensor> sensors = sensorRepository.findSensorByClusterId(clusterId);
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
        return clusterRepository.findById(clusterId).get().toString();
    }

}
