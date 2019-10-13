package yahaha.gxshopping.service.impl;

import yahaha.gxshopping.domain.Product;
import yahaha.gxshopping.mapper.ProductMapper;
import yahaha.gxshopping.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author gpl
 * @since 2019-10-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
