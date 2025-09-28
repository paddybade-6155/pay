package com.riders.pay.model;

import lombok.Data;

@Data
public class ResponseModel {

    private String status;
    private String errorMsg;
    private Object Data;

}
