package com.example.employeemanagement.retrofit;

public class APIUtils {

    private static String baseURL = "http://10.42.0.1/connect/";

    public static APIService getServer() {
        return APIClient.getClient(baseURL).create(APIService.class);
    }
}
