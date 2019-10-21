package yahaha.gxshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yahaha.gxshopping.domain.ProductDoc;
import yahaha.gxshopping.repository.ProductDocESRepository;

import java.util.List;

/**
 * @author gpl
 */
@RestController
public class ProductDocESController {

    @Autowired
    private ProductDocESRepository repository;

    /**
     * 批量添加
     * @param productDocs
     */
    @PostMapping("/es/saveBatch")
    public void saveBatch(@RequestBody List<ProductDoc> productDocs){
        repository.saveAll(productDocs);
    }

    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/es/deleteBatch")
    public void deleteBatch(@RequestBody List<Long> ids){
        for (Long id : ids) {
            repository.deleteById(id);
        }
    }
}
