package com.example.demo.service;


import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class floorService {
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private FloorRepository floorRepository;



    public String saveFloortoDB(Floor floor) {
        floorRepository.save(floor);
        return floor.toString();
    }

    public void deleteFloor(long floor_id){
        Long floorId = Long.valueOf(floor_id).longValue();

        Iterable<Floor> floors = floorRepository.findAll();

        for(Floor floor: floors){
            if(floorId == floor.getId()){
                floorRepository.deleteById(floor.getId());
                roomRepository.deleteRoomByFloorId(floor.getId());
            }
        }
    }

    public Floor getFloorByFloorId(long floor_id, long building_id){
        List<Floor> floors = floorRepository.findFloorByBuildingId(building_id);
        for (Floor floor: floors) {
            if (floor.getId() == floor_id) {
                return floor;
            }
        }
        return null;
    }

    public List<Floor> getFloorByBuildingID(long building_id) {
        return floorRepository.findFloorByBuildingId(building_id);
    }

    public int countSensorsByTypeNStatus(long floor_id, long building_id, String type, String status) {
        return floorRepository.countSensorByTypeNStatus(floor_id, building_id, status, type);
    }

    public floorNested getFloorNestedByFloorId(long floor_id, long building_id, String requirement){
        List<Floor> floors = floorRepository.findFloorByBuildingId(building_id);
        Floor floor = null;
        for (Floor f: floors) {
            if (f.getId() == floor_id) {
                floor = f;
            }
        }
        List<Cluster> clusterList = clusterRepository.findClusterByFloorID(floor_id, building_id);
        List<Room> roomList = roomRepository.findRoomByFloorId(floor_id, building_id);
        List<Node> nodeList = new LinkedList<>();
        List<Sensor> sensorList = new LinkedList<>();
        for (Cluster cluster: clusterList) {
            List<Node> nodesOfOneCluster = nodeRepository.findNodeByClusterId(cluster.getId());
            for (Node node: nodesOfOneCluster) {
                nodeList.add(node);
            }
        }
        Iterable<Sensor> sensors = sensorRepository.findSensorByBuildingId(building_id);
        for(Sensor sensor: sensors) {
            if(sensor.getFloor_number() == floor.getFloor_number()){
                sensorList.add(sensor);
            }
        }

        floorNested floorNest = new floorNested(floor,clusterList,roomList,nodeList,sensorList);
        return floorNest;
    }

    public Map<Integer, Boolean> getRoomNodeMatchResult(floorNested floorNest) {

        Map<Integer, Boolean> res = new HashMap<>();

        Iterable<Room> rooms = floorNest.getRooms();
        for(Room room: rooms) {
            Iterable<Node> nodes = floorNest.getNodes();
            for(Node node: nodes) {
                if(node.getRoom_id() == node.getId()) {
                    res.put(room.getRoom_number(), true);
                }
            }
            if (!res.containsKey(room.getRoom_number())) {
                res.put(room.getRoom_number(), false);
            }
        }
        return res;
    }

    /**
     * multiple count
     * @param floor_id
     * @return
     */
    public List<Object[]> countFloorRoomNodeSensor(long floor_id) {
        return floorRepository.countFloorRoomNodeSensor(floor_id);
    }

    /**
     * count sensors by floor id
     */

    public int countSensorByFloorID(long floor_id, long building_id) {

        return floorRepository.countSensorByFloorID(floor_id, building_id);
    }

    /**
     * count sensors of different type by floor id
     */

    public int countSensorsByFlooridNType(long floor_id, long building_id, String type) {
        return floorRepository.countSensorsByFlooridNType(floor_id, building_id, type);
    }

    /**
     * count sensors of different status by floor id
     */

    public int countSensorsByFlooridNStatus(long floor_id, long building_id, String status) {
        return floorRepository.countSensorsByFlooridNStatus(floor_id, building_id, status);
    }


}
