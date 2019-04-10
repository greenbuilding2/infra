package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import com.example.demo.model.*;

import java.util.*;

@Service
public class sensorService {
    @Autowired
    private SensorRepository sensorRepository;

    /**
     * final all
     */
    public List<Sensor> getAllSensor() {
        List<Sensor> sensors = new ArrayList<>();
        Iterable<Sensor> iter = sensorRepository.findAll();
        for (Sensor sensor: iter) {
            sensors.add(sensor);
        }
        return sensors;
    }

    /**
     * find sensor on floor plan
     * @param x_coordinate
     * @param y_coordinate
     * @return
     */
    public List<Sensor> searchSensorByLocation(Long building_id, Integer floor_number, String x_coordinate, String y_coordinate) {
        List<Sensor> sensors = sensorRepository.findSensorByBuildingandFloor(building_id, floor_number);
        List<Sensor> res = new LinkedList<>();
        Double xCoor = Double.valueOf(x_coordinate);
        Double yCoor = Double.valueOf(y_coordinate);
        for (Sensor sensor: sensors) {
            Double x = sensor.getX_coordinate();
            Double y = sensor.getY_coordinate();
            if (xCoor.equals(x) && yCoor.equals(y))
                res.add(sensor);
        }
        return res;
    }

    public Sensor findSensorBySensorId(long sensor_id, long building_id)
    {
        List<Sensor> sensors = sensorRepository.findSensorByBuildingId(building_id);
        Sensor res = null;
        for (Sensor sensor: sensors) {
            if (sensor.getId() == sensor_id)
                res = sensor;
        }
        return res;
    }

    public String findTypesBySensorID(long sensorID) {
        return sensorRepository.getTypeByID(sensorID);
    }

    public List<Sensor> findSensorByBuildingID(long buildingID){
        return sensorRepository.findSensorByBuildingId(buildingID);
    }

    public int countSensorByType(String type) {
        return sensorRepository.getNumOfSensorByType(type);
    }

    public int countSensorByStatus(String status, long building_id) {
        return sensorRepository.getNumOfSensorByStatus(status, building_id);
    }

    public List<Sensor> getSensorByFloorId(int floor_number, long building_id) {
        return sensorRepository.findSensorByBuildingandFloor(building_id, floor_number);
    }
    /**
     * Get latest install time of each floor
     */

    public Date latestInstallTime(long floor_id, long building_id) {
        List<Date> dates = sensorRepository.getInstallTime(floor_id, building_id);
        if(dates == null || dates.size() == 0)
            return null;
        else return dates.get(0);
    }

    public Date latestMaintenanceTime(long floor_id, long building_id) {
        List<Date> dates = sensorRepository.getMaintenanceTime(floor_id, building_id);
        if(dates == null || dates.size() == 0)
            return null;
        else return dates.get(0);
    }

    public Sensor updateSensorBySensorId(long sensor_id, long node_id, long cluster_id, Sensor sensor)
    {
        Sensor sensorFromDB = sensorRepository.findSensorByAllId(sensor_id, node_id, cluster_id);
        if (!sensor.getName().equals("")) {
            sensorFromDB.setName(sensor.getName());
        }
        if (!sensor.getType().equals("")) {
            sensorFromDB.setType(sensor.getType());
        }
        if (!sensor.getSeries_number().equals("")) {
            sensorFromDB.setSeries_number(sensor.getSeries_number());
        }
        if (!sensor.getStatus().equals("")) {
            sensorFromDB.setStatus(sensor.getStatus());
        }
        if (!sensor.getX_coordinate().equals("") && !sensor.getY_coordinate().equals("")) {
            sensorFromDB.setX_coordinate(sensor.getX_coordinate());
            sensorFromDB.setY_coordinate(sensor.getY_coordinate());
        }
        sensorRepository.save(sensorFromDB);
        return sensorFromDB;
    }

    public String addSensortoDB(Sensor sensor){
        sensorRepository.save(sensor);
        return sensor.toString();
    }

    public void deleteSensor(long sensor_id, long node_id, long cluster_id){
        List<Sensor> sensors = sensorRepository.findSensorByNodeandClusterId(node_id, cluster_id);
        for(Sensor sensor: sensors){
            if(sensor_id == sensor.getId()) {
                sensorRepository.deleteSensorByNodeandClusterId(node_id, cluster_id);
            }
        }
    }
}
