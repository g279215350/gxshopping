package yahaha.gxshopping.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yahaha.gxshopping.domain.Brand;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.service.IBrandService;
import yahaha.gxshopping.util.PageList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandServiceImplTest {

    @Autowired
    private IBrandService brandService;

    @Test
    public void queryPage() {
        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setPage(1);
        brandQuery.setRows(10);
        brandQuery.setKeyword("七");
        System.out.println(brandQuery);
        PageList<Brand> brandPageList = brandService.queryPage(brandQuery);
        System.out.println(brandPageList.getTotal());
        brandPageList.getRows().forEach(e-> System.out.println(e));
    }
}