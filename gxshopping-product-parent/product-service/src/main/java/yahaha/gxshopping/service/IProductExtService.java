package yahaha.gxshopping.service;

import yahaha.gxshopping.domain.ProductExt;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品扩展 服务类
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
public interface IProductExtService extends IService<ProductExt> {

    ProductExt findByProductId(Long id);
}
