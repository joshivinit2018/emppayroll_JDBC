package com.bridgelabz;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONServeTest{
    @Before
    public void setup(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port=4000;
    }

    public JSONServerEmployeedata[] getEmplist(){
        Response response=RestAssured.get("/employees");
        System.out.println("Data in json is: \n"+response.asString());
        JSONServerEmployeedata[] restAssureEmpData=new Gson().fromJson(response.asString(),JSONServerEmployeedata[].class);
        return restAssureEmpData;
    }

    public Response Employee_add_To_JsonServer(JSONServerEmployeedata restAssureEmpData){
        String employee=new Gson().toJson(restAssureEmpData);
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(employee);
        return requestSpecification.post("/employees");
    }

    @Test
    public void givenEmployeeDate_Retrieve_ServerData(){
        JSONServerEmployeedata[] restAssureEmpData=getEmplist();
        System.out.println(restAssureEmpData);
        Assert.assertEquals(3,restAssureEmpData.length);
    }

    
    @Test
    public void NewEmployee_isAdded_return_Response_Code_201(){
        JSONServerEmployeedata[] jsonServerEmpData=getEmplist();

        JSONServerEmployeedata jsonServerEmpData1=new JSONServerEmployeedata(4,"Omkar",60000);

        Response response=Employee_add_To_JsonServer(jsonServerEmpData1);
        int statusCode= response.statusCode();
        Assert.assertEquals(201,statusCode);

        Assert.assertEquals(3,jsonServerEmpData.length);
    }
}