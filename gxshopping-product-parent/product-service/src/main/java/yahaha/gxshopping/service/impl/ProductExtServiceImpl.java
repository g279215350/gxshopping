package yahaha.gxshopping.service.impl;

import yahaha.gxshopping.domain.ProductExt;
import yahaha.gxshopping.mapper.ProductExtMapper;
import yahaha.gxshopping.service.IProductExtService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品扩展 服务实现类
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
@Service
public class ProductExtServiceImpl extends ServiceImpl<ProductExtMapper, ProductExt> implements IProductExtService {

    @Override
    public ProductExt findByProductId(Long id) {
        return baseMapper.findByProductId(id);
    }
}
