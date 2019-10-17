package yahaha.gxshopping.service;

import yahaha.gxshopping.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.util.PageList;

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
}
