package scripts.api;

import api.pojo.Employee;
import common.APIBaseTest;
import org.testng.annotations.Test;

import static common.factories.APIFactory.api;

public class EmployeeTest extends APIBaseTest {

    @Test
    public void testGetEmployeeDetails()
    {
        Employee employee = api().employee().getEmployee(1);
        System.out.println(employee.userId());
        System.out.println(employee.id());
        System.out.println(employee.title());
        System.out.println(employee.completed());
    }
}
