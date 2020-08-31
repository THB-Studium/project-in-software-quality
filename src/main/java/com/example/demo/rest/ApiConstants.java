package com.example.demo.rest;

public class ApiConstants {
    public static final String API_ROOT = "/api";

    public static final String PARTICIPANT_COLLECTION = ApiConstants.API_ROOT + "/participants";
    public static final String PARTICIPANT_ITEM = ApiConstants.PARTICIPANT_COLLECTION + "/{participantId}";
    
    public static final String BOOKING_COLLECTION = ApiConstants.API_ROOT + "/bookings";
    public static final String BOOKING_ITEM = ApiConstants.BOOKING_COLLECTION + "/{bookingId}";
    
    public static final String SEMINAR_COLLECTION = ApiConstants.API_ROOT + "/seminars";
    public static final String SEMINAR_ITEM = ApiConstants.SEMINAR_COLLECTION + "/{seminarId}";
}
