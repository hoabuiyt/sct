package com.qti.csdlcn.sct.util;

import com.qti.csdlcn.sct.payload.ApiResponse;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "1";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;

    ApiResponse CREATE_NAME_EXIST = new ApiResponse(false, "CREATE_NAME_EXIST");
    ApiResponse CREATE_SUCCESS = new ApiResponse(true, "CREATE_SUCCESS");
    
    ApiResponse UPDATE_SUCCESS = new ApiResponse(true, "UPDATE_SUCCESS");
    ApiResponse UPDATE_FAILED = new ApiResponse(false, "UPDATE_FAILED");
    
    ApiResponse DELETE_SUCCESS = new ApiResponse(true, "DELETE_SUCCESS");
    ApiResponse DELETE_FAILED = new ApiResponse(false, "DELETE_FAILED");
    
}
