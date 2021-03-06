package yahaha.gxshopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yahaha.gxshopping.domain.ProductDoc;
import yahaha.gxshopping.domain.ProductParam;
import yahaha.gxshopping.util.PageList;

import java.util.List;

/**
 * @author gpl
 */
@FeignClient(value = "GXSHOPPING-COMMON")
public interface ProductDocESClient {

    /**
     * 批量添加
     * @param productDocs
     */
    @PostMapping("/es/saveBatch")
    void saveBatch(@RequestBody List<ProductDoc> productDocs);

    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/es/deleteBatch")
    void deleteBatch(@RequestBody List<Long> ids);

    /**
     * 条件搜索商品数据
     * @param param
     * @return
     */
    @PostMapping("/es/products")
    PageList<ProductDoc> search(@RequestBody ProductParam param);
}
