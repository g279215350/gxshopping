package yahaha.gxshopping.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import yahaha.gxshopping.domain.ProductDoc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JustTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void test() throws Exception{
        template.deleteIndex(ProductDoc.class);
        template.createIndex(ProductDoc.class);
        template.putMapping(ProductDoc.class);
    }
}
