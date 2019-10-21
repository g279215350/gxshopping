package yahaha.gxshopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.transaction.annotation.Transactional;
import yahaha.gxshopping.client.ProductDocESClient;
import yahaha.gxshopping.domain.*;
import yahaha.gxshopping.mapper.*;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yahaha.gxshopping.util.PageList;
import yahaha.gxshopping.util.StrUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
    @Autowired
    private ProductDocESClient client;
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Autowired
    private BrandMapper brandMapper;

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
    @Transactional
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
    @Transactional
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

    /**
     * 批量上架
     * @param idsList
     */
    @Override
    @Transactional
    public void onSaleBatch(List<Long> idsList) {
        //修改数据库中数据的状态
        baseMapper.updateOnSaleAllState(idsList, System.currentTimeMillis());
        //获取上架的商品信息
        List<Product> products = baseMapper.selectBatchIds(idsList);
        //将上架的商品封装到ProductDoc以便添加到ES中
        List<ProductDoc> productDocs = trans2Docs(products);
        client.saveBatch(productDocs);
    }

    @Override
    @Transactional
    public void offSaleBatch(List<Long> idsList) {
        //修改数据库中的状态
        baseMapper.updateOffSaleAllState(idsList, System.currentTimeMillis());
        //从ES中删除该数据
        client.deleteBatch(idsList);
    }

    private List<ProductDoc> trans2Docs(List<Product> products) {
        List<ProductDoc> productDocs = new ArrayList<>();
        for (Product product : products) {
            productDocs.add(trans2Doc(product));
        }
        return productDocs;
    }

    private ProductDoc trans2Doc(Product product) {
        ProductDoc productDoc = new ProductDoc();
        productDoc.setId(product.getId());

        //all："商品标题 商品副标题 类型名称 品牌名称"中间拼接空格做分词用
        StringBuilder all = new StringBuilder();
        ProductType productType = productTypeMapper.selectById(product.getProductTypeId());
        Brand brand = brandMapper.selectById(product.getBrandId());
        all.append(product.getName())
                .append(" ")
                .append(product.getSubName())
                .append(" ")
                .append(productType.getName())
                .append(" ")
                .append(brand.getName());
        productDoc.setAll(all.toString());

        productDoc.setProductTypeId(product.getProductTypeId());
        productDoc.setBrandId(product.getBrandId());

        List<Sku> skus = skuMapper.selectList(new QueryWrapper<Sku>().eq("product_id", product.getId()));
        Integer max = 0;
        Integer min = 0;
        if (skus != null && skus.size() > 0) {
            min = skus.get(0).getMarketPrice();
        }
        for (Sku sku : skus) {
            if (sku.getMarketPrice() > max) {
                max = sku.getMarketPrice();
            }
            if (min > sku.getMarketPrice()) {
                min = sku.getMarketPrice();
            }
        }
        productDoc.setMaxPrice(max);
        productDoc.setMinPrice(min);

        productDoc.setSaleCount(product.getSaleCount());
        productDoc.setOnSaleTime(product.getOnSaleTime());
        productDoc.setCommentCount(product.getCommentCount());
        productDoc.setViewCount(product.getViewCount());
        productDoc.setName(product.getName());
        productDoc.setSubName(product.getSubName());
        productDoc.setMedias(product.getMedias());
        productDoc.setViewProperties(product.getViewProperties());
        productDoc.setSkuProperties(product.getSkuProperties());
        return productDoc;
    }

    @Override
    @Transactional
    public boolean save(Product product) {
        product.setCreateTime(System.currentTimeMillis());
        //MyBatis-Plus自动返回主键
        baseMapper.insert(product);
        ProductExt ext = product.getExt();
        ext.setProductId(product.getId());
        productExtMapper.insert(ext);
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(Product product) {
        product.setUpdateTime(System.currentTimeMillis());
        boolean result =  super.updateById(product);
        ProductExt ext = product.getExt();
        productExtMapper.updateById(ext);
        return result;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        productExtMapper.deleteByProductId(id);
        return super.removeById(id);
    }
}
