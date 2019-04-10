package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;
import java.util.*;

@Service
public class roomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;

    public String saveRoomtoDB(Room room) {
        roomRepository.save(room);
        return room.toString();
    }

    public void deleteRoom(long room_id){
        Long roomId = Long.valueOf(room_id).longValue();
        Iterable<Room> rooms = roomRepository.findAll();
        for(Room room: rooms){
            if(roomId == room.getId())
                roomRepository.deleteById(room.getId());
        }
    }

    public Room getRoomByRoomId(long room_id, long floor_id, long building_id){
        List<Room> rooms = roomRepository.findRoomByFloorId(floor_id, building_id);
        Room res = null;
        for (Room room : rooms) {
            if (room.getId() == room_id) {
                res = room;
            }
        }
        return res;
    }

    public roomNested getRoomNestedByRoomId(long room_id, String requirement){

        Room room = roomRepository.findById(room_id).get();
        List<Node> nodeList = nodeRepository.findNodeByRoomId(room.getId());
        List<Sensor> sensorList = new ArrayList<>();

        for (Node nodes: nodeList) {
            List<Sensor> sensors = sensorRepository.findSensorByNodeandClusterId(nodes.getId(), nodes.getCluster_id());
            for (Sensor sensor: sensors)
            sensorList.add(sensor);
        }

        roomNested roomNest = new roomNested(room,nodeList,sensorList);

        return roomNest;
    }

    public List<Object[]> countNodeSensor(long room_id) {
        return roomRepository.countNodeSensor(room_id);
    }

}
