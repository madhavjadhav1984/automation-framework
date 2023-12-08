package api.controller;

import api.pojo.Employee;
import api.pojo.Product;
import common.APIManager;
import io.restassured.response.Response;

public class EmployeeApi extends APIManager{

    public Employee getEmployee(int id)
    {
        APIManager.resetRESTState();
        /*getRequestBuilder()
                .addHeaders(getApiConfig().read("defaultHeaders"))
                .addHeaders(getApiConfig().read("tokens"));*/
        Response response = get("https://jsonplaceholder.typicode.com/todos/1");

        return response
                .getBody()
                .jsonPath()
                .getObject("", Employee.class);
    }
}
