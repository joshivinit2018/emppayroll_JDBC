package com.bridgelabz;

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

    public Response addEmployeeToJsonServer(JSONServerEmployeedata restAssureEmpData){
        String employee=new Gson().toJson(restAssureEmpData);
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(employee);
        return requestSpecification.post("/employees");
    }

    @Test
    public void givenEmployeeDate_shouldRetrieve_ServerData(){
        JSONServerEmployeedata[] restAssureEmpData=getEmplist();
        System.out.println(restAssureEmpData);
        Assert.assertEquals(3,restAssureEmpData.length);
    }
}