package api.controller;

import api.pojo.Product;
import common.APIManager;
import io.restassured.response.Response;

public class ProductApi extends APIManager {

    public Product getProduct(int id)
    {
        APIManager.resetRESTState();
        /*getRequestBuilder()
                .addHeaders(getApiConfig().read("defaultHeaders"))
                .addHeaders(getApiConfig().read("tokens"));*/
        Response response = get("https://dummyjson.com/products/1");

        return response
                .getBody()
                .jsonPath()
                .getObject("", Product.class);
    }

}
