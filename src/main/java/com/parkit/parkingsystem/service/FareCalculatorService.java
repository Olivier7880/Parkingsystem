package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, int recurring){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inDate = ticket.getInTime().getTime();
        long outDate = ticket.getOutTime().getTime();
        
        //TODO: Some tests are failing here. Need to check if this logic is correct
        float durationDate = outDate - inDate;
        durationDate = durationDate / (1000 * 3600);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
            	if(durationDate <= 0.5) {
            		ticket.setPrice(0);            		
            	}else if(recurring >= 2) {
            		ticket.setPrice((durationDate * Fare.CAR_RATE_PER_HOUR) * 0.95);
            	}else {
            		ticket.setPrice(durationDate * Fare.CAR_RATE_PER_HOUR);
            	}
            	break;
            }
            case BIKE: {
            	if(durationDate <= 0.5) {
            		ticket.setPrice(0);            		            		
            	}else if(recurring >= 2) {
            		ticket.setPrice((durationDate * Fare.BIKE_RATE_PER_HOUR) * 0.95);
            	}else {
            		ticket.setPrice(durationDate * Fare.BIKE_RATE_PER_HOUR);
            	}
            	break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }       
    }
}