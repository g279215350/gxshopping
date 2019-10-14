package yahaha.gxshopping.service;

import yahaha.gxshopping.domain.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author gpl
 * @since 2019-10-12
 */
public interface IProductTypeService extends IService<ProductType> {

    List<ProductType> loadTreeMenu();

    List<ProductType> loadTreeType();

    void genHomePage();

}
