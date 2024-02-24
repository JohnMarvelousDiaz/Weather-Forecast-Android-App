package com.example.finalproject;

import com.example.finalproject.Models.APIResponse;

public interface OnFetchDataListener {

    void onFetchData(APIResponse apiResponse, String message);
    void OnError(String message);

}
