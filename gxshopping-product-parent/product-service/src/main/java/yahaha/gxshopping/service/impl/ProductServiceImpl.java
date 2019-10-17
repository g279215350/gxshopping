package yahaha.gxshopping.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import yahaha.gxshopping.domain.Product;
import yahaha.gxshopping.domain.ProductExt;
import yahaha.gxshopping.mapper.ProductExtMapper;
import yahaha.gxshopping.mapper.ProductMapper;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yahaha.gxshopping.util.PageList;

import java.io.Serializable;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductExtMapper productExtMapper;

    @Override
    public PageList<Product> queryPage(ProductQuery productQuery) {
        IPage<Product> iPage =
                baseMapper.pageQuery(new Page(productQuery.getPage(), productQuery.getRows()), productQuery);
        return new PageList<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public boolean save(Product product) {
        product.setCreateTime(System.currentTimeMillis());
        baseMapper.insert(product); //MyBatis-Plus自动返回主键
        ProductExt ext = product.getExt();
        ext.setProductId(product.getId());
        productExtMapper.insert(ext);
        return true;
    }

    @Override
    public boolean updateById(Product product) {
        product.setUpdateTime(System.currentTimeMillis());
        Boolean result =  super.updateById(product);
        ProductExt ext = product.getExt();
        productExtMapper.updateById(ext);
        return result;
    }

    @Override
    public boolean removeById(Serializable id) {
        productExtMapper.deleteByProductId(id);
        return super.removeById(id);
    }
}
