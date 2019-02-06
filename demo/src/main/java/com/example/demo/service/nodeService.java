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

    public List<Long> getSensorIDByNodeID(String nodeId)
    {
        long node_id = Long.valueOf(nodeId).longValue();
        List<Long> sensorId_List = new LinkedList<>();
        List<Sensor> sensors = sensorRepository.findSensorByNodeId(node_id);
        for(Sensor sensor: sensors) {
            sensorId_List.add(sensor.getId());
        }
        return sensorId_List;
    }

    public Node updateNodeByNodeId(String nodeId, Node node){
        long node_id = Long.valueOf(nodeId).longValue();
        Node nodeFromDB = nodeRepository.findById(node_id).get();

        if(node.getName() != null) {
            nodeFromDB.setName(node.getName());
        }

        if(node.getStatus() != null) {
            nodeFromDB.setStatus(node.getStatus());
            List<Sensor> sensors = sensorRepository.findSensorByNodeId(node_id);
            for(Sensor sensor: sensors) {
                sensor.setStatus(node.getStatus());
                sensorRepository.save(sensor);
            }
        }
        nodeRepository.save(nodeFromDB);
        return nodeFromDB;
    }

    public String addNodetoDB(Node node){
        nodeRepository.save(node);
        return node.toString();
    }

    public void deleteNode(long node_id){
        Long nodeId = Long.valueOf(node_id).longValue();
        Iterable<Node> nodes = nodeRepository.findAll();
        for(Node node: nodes){
            if(nodeId == node.getId()){
                nodeRepository.deleteById(node.getId());
                sensorRepository.deleteSensorByNodeId(node.getId());
            }
        }
    }

    public Node getNodeByNodeId(long node_id){
        Long nodeId = Long.valueOf(node_id).longValue();
        return nodeRepository.findById(nodeId).get();
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
