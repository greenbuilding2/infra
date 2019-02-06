package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;
import java.util.*;

@Service
public class buildingService {

    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    FloorRepository floorRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ClusterRepository clusterRepository;

    public String saveBuildingToDB (Building building) {
        buildingRepository.save(building);
        return building.toString();
    }

    public Iterable<Building> searchBuildingByLa(String latitude, String longitude, Integer radius){
        Double lat = Double.valueOf(latitude);
        Double lng = Double.valueOf(longitude);
        Integer limit = Integer.valueOf(radius);
        Double distanceRange;
        Double distanceLimit = new Double(limit);

        Iterable<Building> buildings = buildingRepository.findAll();
        List<Building> res = new LinkedList<>();

        for (Building building : buildings) {
            double pow1 = Math.pow(69.1 * (building.getLatitude() - lat), 2);
            double pow2 = Math.pow(69.1 * (lng - building.getLongitude()) * Math.cos(building.getLatitude() / 57.3), 2);
            distanceRange = pow1 + pow2;
            if( distanceRange.compareTo(distanceLimit) < 0 ) {
                res.add(building);
            }
        }
        return res;
    }

    public Iterable<Building> searchBuildingByCity(String city,String state,String zipcode){
        Iterable<Building> buildings = buildingRepository.findAll();
        List<Building> res = new LinkedList<>();

        if (zipcode == null && city != null && state != null) {
            String cityName = city.replace("+", " ");
            for(Building building : buildings) {
                if (building.getCity().equalsIgnoreCase(cityName) && building.getState().equalsIgnoreCase(state)) {
                    res.add(building);
                }
            }
        } else if (zipcode != null) {
            for ( Building building : buildings) {
                if ( building.getZipcode().equalsIgnoreCase(zipcode) ) {
                    res.add(building);
                }
            }
        }
        return res;
    }

    public void deleteBuilding(long building_id){
        Long buildingId = Long.valueOf(building_id).longValue();

        Iterable<Building> buildings = buildingRepository.findAll();

        for(Building building: buildings){
            if(buildingId == building.getId()) {
                buildingRepository.deleteById(building.getId());
                floorRepository.deleteFloorByBuildingId(building.getId());
                roomRepository.deleteRoomByBuildingId(building.getId());
            }
        }
    }

    public Building getBuildingByBuildingId(long building_id){
        Long buildingId = Long.valueOf(building_id).longValue();
        return buildingRepository.findById(buildingId).get();
    }

    public buildingNested getBuildingNestedByBuildingId(long building_id, String requirement){
        Long buildingId = Long.valueOf(building_id).longValue();
        Building building = buildingRepository.findById(buildingId).get();
        List<Cluster> clusterList = clusterRepository.findClusterByBuildingId(buildingId);
        List<Floor> floorList = floorRepository.findFloorByBuildingId(buildingId);

        buildingNested buildingNest = new buildingNested(building,floorList,clusterList);

        return buildingNest;
    }

    public Map<Integer, Boolean> getFloorCluterMatchResult(buildingNested buildingNest) {

        Map<Integer, Boolean> res = new HashMap<>();

        Iterable<Floor> floors = buildingNest.getFloors();
        for(Floor floor: floors) {
            Iterable<Cluster> clusters = buildingNest.getClusters();
            for(Cluster cluster: clusters) {
                if(cluster.getFloor_id() == floor.getId()) {
                    res.put(floor.getFloor_number(), true);
                }
            }
            if (!res.containsKey(floor.getFloor_number())) {
                res.put(floor.getFloor_number(), false);
            }
        }
        return res;
    }
}
