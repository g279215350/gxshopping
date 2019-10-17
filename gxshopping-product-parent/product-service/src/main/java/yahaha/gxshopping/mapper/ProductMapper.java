package yahaha.gxshopping.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import yahaha.gxshopping.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yahaha.gxshopping.query.ProductQuery;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
public interface ProductMapper extends BaseMapper<Product> {

    IPage<Product> pageQuery(Page page, @Param("query")ProductQuery query);
}
