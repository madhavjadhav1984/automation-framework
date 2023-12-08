package common.factories;

import api.controller.CommonApi;
import api.controller.EmployeeApi;
import api.controller.ProductApi;

public class APIFactory {
    private APIFactory()
    {

    }
    private static final APIFactory API = new APIFactory();

    public static APIFactory api() {
        return API;
    }

    public CommonApi common(){
        return new CommonApi();
    }
    public ProductApi products(){
        return new ProductApi();
    }
    public EmployeeApi employee(){
        return new EmployeeApi();
    }
}
