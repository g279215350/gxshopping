package yahaha.gxshopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import yahaha.gxshopping.domain.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.util.PageList;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author gpl
 * @since 2019-10-12
 */
public interface IBrandService extends IService<Brand> {

    PageList<Brand> queryPage(BrandQuery brandQuery);
}
