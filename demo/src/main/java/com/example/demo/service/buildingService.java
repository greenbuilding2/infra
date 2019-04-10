package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.nested.*;

import javax.persistence.criteria.CriteriaBuilder;
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

    public List<Building> getAllBuilding() {
        List<Building> buildings = new ArrayList<>();
        Iterable<Building> iter = buildingRepository.findAll();
        for (Building building: iter) {
            buildings.add(building);
        }
        return buildings;
    }

    public String saveBuildingToDB (Building building) {
        buildingRepository.save(building);
        return building.toString();
    }

    public Building updateBuildingByBuildingId(long building_id, Building building) {
        Building buildingFromDB = buildingRepository.findById(building_id).get();
        if (!building.getImage_url().equals("")) {
            buildingFromDB.setImage_url(building.getImage_url());
        }
        if (!building.getAddress().equals("")) {
            buildingFromDB.setAddress(building.getAddress());
        }
        if (!building.getCountry().equals("")) {
            buildingFromDB.setCountry(building.getCountry());
        }
        if (!building.getState().equals("")) {
            buildingFromDB.setState(building.getState());
        }
        if (!building.getCity().equals("")) {
            buildingFromDB.setCity(building.getCity());
        }
        if (!building.getZipcode().equals("")) {
            buildingFromDB.setZipcode(building.getZipcode());
        }
        if (!building.getNum_of_floors().equals("")) {
            buildingFromDB.setNum_of_floors(building.getNum_of_floors());
        }
        buildingRepository.save(buildingFromDB);
        return buildingFromDB;
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
        return buildingRepository.findById(building_id).get();
    }

    public buildingNested getBuildingNestedByBuildingId(long building_id, String requirement){

        Building building = buildingRepository.findById(building_id).get();
        List<Cluster> clusterList = clusterRepository.findClusterByBuildingId(building_id);
        List<Floor> floorList = floorRepository.findFloorByBuildingId(building_id);

        buildingNested buildingNest = new buildingNested(building,floorList,clusterList);

        return buildingNest;
    }

    public Map<Integer, Boolean> getFloorClusterMatchResult(buildingNested buildingNest) {
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

    public List<Object[]> countBuildingClustersAndNodes(long building_id) {
        return buildingRepository.countBuildingClustersAndNodes(building_id);
    }
}
