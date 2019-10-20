package yahaha.gxshopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import yahaha.gxshopping.domain.Sku;
import yahaha.gxshopping.mapper.SkuMapper;
import yahaha.gxshopping.service.ISkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * SKU 服务实现类
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {

    @Override
    public List<Sku> findByProductId(Long productId) {
        return baseMapper.selectList(new QueryWrapper<Sku>().eq("product_id", productId));
    }
}
