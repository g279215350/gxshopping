package yahaha.gxshopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import yahaha.gxshopping.domain.Product;
import yahaha.gxshopping.domain.ProductExt;
import yahaha.gxshopping.domain.Sku;
import yahaha.gxshopping.domain.Specification;
import yahaha.gxshopping.mapper.ProductExtMapper;
import yahaha.gxshopping.mapper.ProductMapper;
import yahaha.gxshopping.mapper.SkuMapper;
import yahaha.gxshopping.mapper.SpecificationMapper;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yahaha.gxshopping.util.PageList;
import yahaha.gxshopping.util.StrUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public PageList<Product> queryPage(ProductQuery productQuery) {
        IPage<Product> iPage =
                baseMapper.pageQuery(new Page(productQuery.getPage(), productQuery.getRows()), productQuery);
        return new PageList<>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 获取显示属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getViewProperties(Long productId) {
        Product product = baseMapper.selectById(productId);
        String viewProperties = product.getViewProperties();
        if (StringUtils.isNotBlank(viewProperties)) {
            return JSONArray.parseArray(viewProperties, Specification.class);
        } else {
            return specificationMapper.selectList(new QueryWrapper<Specification>()
                    .eq("isSku", 0).eq("product_type_id", product.getProductTypeId()));
        }
    }

    /**
     * 更新显示属性
     * @param productId
     * @param viewProperties
     */
    @Override
    public void changeViewProperties(Long productId, List<Specification> viewProperties) {
        baseMapper.updateViewProperties(productId,JSON.toJSONString(viewProperties));
    }

    /**
     * 获取Sku属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getSkuProperties(Long productId) {
        Product product = baseMapper.selectById(productId);
        String skuProperties = product.getSkuProperties();
        if (StringUtils.isNotBlank(skuProperties)) {
            return JSONArray.parseArray(skuProperties, Specification.class);
        } else {
            return specificationMapper.selectList(new QueryWrapper<Specification>()
                    .eq("isSku", 1).eq("product_type_id", product.getProductTypeId()));
        }
    }

    /**
     * 更新Sku
     *  更新skuProperties值，更新到t_product表
     *
     *  更新sku属性，更新到t_sku表
     *      先删除原本数据，在添加当前数据
     * @param productId
     * @param skuProperties
     * @param skus
     */
    @Override
    public void changeSkuProperties(Long productId, List<Specification> skuProperties, List<Map<String, String>> skus) {
        //更新skuProperties值，更新到t_product表
        baseMapper.updateSkuProperties(productId, JSON.toJSONString(skuProperties));

        //删除原本数据
        skuMapper.delete(new QueryWrapper<Sku>().eq("product_id", productId));
        //新增数据
        Sku sku = null;
        for (Map<String, String> skuMap : skus) {
            sku = new Sku();
            sku.setCreateTime(System.currentTimeMillis());
            sku.setProductId(productId);
            sku.setAvailableStock(Integer.valueOf(skuMap.get("store")));
            sku.setMarketPrice(Integer.valueOf(skuMap.get("price")));
            sku.setIndexes(skuMap.get("indexes"));
            //设置skuName，拼接每个选项的值
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> skuEntry : skuMap.entrySet()) {
                if (!"price".equals(skuEntry.getKey())
                        &&!"store".equals(skuEntry.getKey())
                        &&!"indexes".equals(skuEntry.getKey())){
                    sb.append(skuEntry.getValue());
                }
            }
            sku.setSkuName(sb.toString());
            skuMapper.insert(sku);
        }
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
