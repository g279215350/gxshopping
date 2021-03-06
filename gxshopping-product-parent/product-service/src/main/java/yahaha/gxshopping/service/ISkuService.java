package yahaha.gxshopping.service;

import yahaha.gxshopping.domain.Sku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SKU 服务类
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
public interface ISkuService extends IService<Sku> {

    List<Sku> findByProductId(Long productId);
}
