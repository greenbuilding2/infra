package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;
import java.util.*;

@Service
public class nodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private RoomRepository roomRepository;

//    public List<Long> getSensorIDByNodeID(String nodeId)
//    {
//        long node_id = Long.valueOf(nodeId).longValue();
//        List<Long> sensorId_List = new LinkedList<>();
//        List<Sensor> sensors = sensorRepository.findSensorByNodeId(node_id);
//        for(Sensor sensor: sensors) {
//            sensorId_List.add(sensor.getId());
//        }
//        return sensorId_List;
//    }

    public Node updateNodeByNodeId(long node_id, long cluster_id, Node node){
        Node nodeFromDB = nodeRepository.findNodeByNodeandClusterId(node_id, cluster_id);
        if (!node.getName().equals("")) {
            nodeFromDB.setName(node.getName());
        }
        if (!node.getStatus().equals("")) {
            nodeFromDB.setStatus(node.getStatus());
        }
        if (!node.getSeries_number().equals("")) {
            nodeFromDB.setSeries_number(node.getSeries_number());
        }
        if (!node.getX_coordinate().equals("") && !node.getY_coordinate().equals("")) {
            nodeFromDB.setX_coordinate(node.getX_coordinate());
            nodeFromDB.setY_coordinate(node.getY_coordinate());
        }
        if (!node.getType().equals("")) {
            nodeFromDB.setType(node.getType());
        }
        nodeRepository.save(nodeFromDB);
        return nodeFromDB;
    }

    public String addNodetoDB(Node node){
        nodeRepository.save(node);
        return node.toString();
    }

    public void deleteNode(long node_id, long cluster_id, long building_id){
        List<Node> nodes = nodeRepository.findNodeByClusterId(cluster_id);
        for(Node node: nodes){
            if(node_id == node.getId()){
                nodeRepository.deleteNodeByClusterId(cluster_id);
                sensorRepository.deleteSensorByNodeandClusterId(node.getId(), cluster_id);
            }
        }
    }

    public Node getNodeByNodeId(long node_id, long cluster_id){
        List<Node> nodes = nodeRepository.findNodeByClusterId(cluster_id);
        Node node = null;
        for (Node n: nodes) {
            if (n.getId() == node_id) {
                node = n;
            }
        }
        return node;
    }

    public nodeNested getNodeNestedByNodeId(long node_id,String requirement){
        Long nodeId = Long.valueOf(node_id).longValue();
        Node node = nodeRepository.findById(nodeId).get();
        Iterable<Sensor> sensors = sensorRepository.findAll();
        List<Sensor> sensorList = new LinkedList<>();

        for(Sensor sensor: sensors) {
            if(sensor.getNode_id() == nodeId) {
                sensorList.add(sensor);
            }
        }

        nodeNested nodeNest = new nodeNested(node,sensorList);
        return nodeNest;
    }


}
