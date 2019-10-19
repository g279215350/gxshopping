package yahaha.gxshopping.service;

import yahaha.gxshopping.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import yahaha.gxshopping.domain.Specification;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.util.PageList;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
public interface IProductService extends IService<Product> {

    PageList<Product> queryPage(ProductQuery productQuery);

    List<Specification> getViewProperties(Long productId);

    void changeViewProperties(Long productId, List<Specification> viewProperties);

    List<Specification> getSkuProperties(Long productId);
}
