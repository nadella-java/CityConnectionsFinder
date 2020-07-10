package com.javapropjects.ccf.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.javapropjects.ccf.model.PathStatus;
import com.javapropjects.ccf.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;


@Service
public class ConnectionCheckService {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    CommonUtil util;

    private Map<String, LinkedHashSet<String>> citiesConnectionMap;

    @PostConstruct
    public void loadCitiesFromFile() throws IOException { // Read the data from file and set it to Map

        citiesConnectionMap = new HashMap<String, LinkedHashSet<String>>();

        Resource resource = resourceLoader.getResource("classpath:cities.txt"); //new ClassPathResource("classpath:data.txt");
        InputStream inputStream = resource.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
			while((line = reader.readLine()) != null) {
				String[] cities = line.split("\\,");
				addBidirectionalConnection(util.lrtrim(cities[0]), util.lrtrim(cities[1]));
			}
			reader.close();
    }

    public String checkIfCitiesAreConnected(String origin, String destination) throws Exception {

       /*if(origin.equals("LA")) 
            return PathStatus.FOUND.toString();
        else if(origin.equals("CL"))
            return PathStatus.NOT_FOUND.toString();
        else
            return PathStatus.INVALID_PATH.toString();
        */

        // Considered the LinkedList to maintain the connected cities list for the origin
        LinkedList<String> connections = new LinkedList<String>();
        connections.add(origin); // Stating to lookup from the origin city
        
        boolean isConnected = searchConnection(connections, destination);

        return isConnected ? PathStatus.FOUND.name() : PathStatus.NOT_FOUND.name();
    }

    private boolean searchConnection(LinkedList<String> connections, String destination) {
    	
    	boolean isConnected = false;
    	
    	LinkedList<String> connectedCities = getConnectedCities(connections.getLast());
 
        for (String destcity : connectedCities) {
            if (connections.contains(destcity)) {
                continue;
            }
            if (destcity.equals(destination)) {
            	connections.add(destcity);
                isConnected = true;
                break;
            } else {
            	 connections.addLast(destcity);
 	            isConnected = searchConnection(connections, destination);
 	            connections.removeLast();
            }
        }
 
       return isConnected;
 
    }

    

    private void addBidirectionalConnection(String origin, String destination) {
    	addConnection(origin, destination);
    	addConnection(destination, origin);
    }

    private void addConnection(String srcCity, String destCity) {
        LinkedHashSet<String> route = citiesConnectionMap.get(srcCity);
        if (route == null) {
        	route = new LinkedHashSet<String>();
            citiesConnectionMap.put(srcCity, route);
        }
        route.add(destCity);
        
    }


    private LinkedList<String> getConnectedCities(String lastCity) {
        LinkedHashSet<String> connectedCities = citiesConnectionMap.get(lastCity);
        if (connectedCities == null) {
            return new LinkedList<String>();
        }
        return new LinkedList<String>(connectedCities);
    }


}