package yahaha.gxshopping.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import yahaha.gxshopping.domain.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yahaha.gxshopping.query.BrandQuery;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author gpl
 * @since 2019-10-12
 */
@Component
public interface BrandMapper extends BaseMapper<Brand> {

    IPage<Brand> pageQuery(Page page, @Param("query") BrandQuery query);

}
