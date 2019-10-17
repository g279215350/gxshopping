package yahaha.gxshopping.mapper;

import org.springframework.stereotype.Component;
import yahaha.gxshopping.domain.ProductExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 * 商品扩展 Mapper 接口
 * </p>
 *
 * @author gpl
 * @since 2019-10-17
 */
@Component
public interface ProductExtMapper extends BaseMapper<ProductExt> {

    ProductExt findByProductId(Long id);

    void deleteByProductId(Serializable id);
}
