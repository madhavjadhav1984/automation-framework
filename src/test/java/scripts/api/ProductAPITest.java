package scripts.api;

import api.pojo.Product;
import common.APIBaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static common.factories.APIFactory.api;

public class ProductAPITest extends APIBaseTest {

    protected static final Logger LOG = LoggerFactory.getLogger(ProductAPITest.class);

    @Test
    public void getProductById()
    {
        Product product = api().products().getProduct(1);
        System.out.println(product.brand());
        System.out.println(product.description());
        System.out.println(product.title());
        LOG.info(product.toPrettyJsonString());
    }
}
