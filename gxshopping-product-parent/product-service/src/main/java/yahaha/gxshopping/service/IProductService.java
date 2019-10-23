package yahaha.gxshopping.service;

import yahaha.gxshopping.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import yahaha.gxshopping.domain.ProductDoc;
import yahaha.gxshopping.domain.ProductParam;
import yahaha.gxshopping.domain.Specification;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.util.PageList;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取显示属性
     * @param productId
     * @return
     */
    List<Specification> getViewProperties(Long productId);

    /**
     * 更新显示属性
     * @param productId
     * @param viewProperties
     */
    void changeViewProperties(Long productId, List<Specification> viewProperties);

    /**
     * 获取Sku属性
     * @param productId
     * @return
     */
    List<Specification> getSkuProperties(Long productId);

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
    void changeSkuProperties(Long productId, List<Specification> skuProperties, List<Map<String, String>> skus);

    /**
     * 批量上架
     * @param idsList
     */
    void onSaleBatch(List<Long> idsList);

    /**
     * 批量下架
     * @param idsList
     */
    void offSaleBatch(List<Long> idsList);

    /**
     * 条件搜索结果获取
     * @param param
     * @return
     */
    PageList<ProductDoc> queryOnSale(ProductParam param);
}
