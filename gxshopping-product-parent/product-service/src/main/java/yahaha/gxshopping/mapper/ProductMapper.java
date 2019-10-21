package yahaha.gxshopping.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import yahaha.gxshopping.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yahaha.gxshopping.domain.Specification;
import yahaha.gxshopping.query.ProductQuery;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页获取数据+高级查询
     * @param page
     * @param query
     * @return
     */
    IPage<Product> pageQuery(Page page, @Param("query")ProductQuery query);

    /**
     * 更新显示属性
     * @param productId
     * @param viewProperties
     */
    void updateViewProperties(@Param("productId") Long productId,
                              @Param("viewProperties") String viewProperties);

    /**
     * 更新sku数据
     * @param productId
     * @param skuProperties
     */
    void updateSkuProperties(@Param("productId") Long productId,
                             @Param("skuProperties") String skuProperties);

    /**
     * 批量修改上架状态和时间
     * @param idsList
     * @param onSaleTime
     */
    void updateOnSaleAllState(@Param("idList") List<Long> idsList,
                              @Param("onSaleTime") long onSaleTime);

    /**
     * 批量修改下架时间和状态
     * @param idsList
     * @param currentTimeMillis
     */
    void updateOffSaleAllState(@Param("idList") List<Long> idsList,
                               @Param("offSaleTime") long currentTimeMillis);
}
