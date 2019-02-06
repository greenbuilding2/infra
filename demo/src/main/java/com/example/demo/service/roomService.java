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

    public Room getRoomByRoomId(long room_id){
        Long roomId = Long.valueOf(room_id).longValue();
        return roomRepository.findById(roomId).get();
    }

    public roomNested getRoomNestedByRoomId(long room_id, String requirement){
        Long roomId = Long.valueOf(room_id).longValue();
        Room room = roomRepository.findById(roomId).get();
        Node node = nodeRepository.findNodeByRoomId(room.getId());

        List<Sensor> sensors = sensorRepository.findSensorByNodeId(node.getId());
        roomNested roomNest = new roomNested(room,node,sensors);

        return roomNest;
    }

}
