package yahaha.gxshopping.service.impl;

import yahaha.gxshopping.domain.ProductType;
import yahaha.gxshopping.mapper.ProductTypeMapper;
import yahaha.gxshopping.service.IProductTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author gpl
 * @since 2019-10-12
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Override
    public List<ProductType> loadTreeMenu() {
        List<ProductType> productTypes = baseMapper.selectList(null);

        ArrayList<ProductType> OneType = new ArrayList<>();

        Map<Long, ProductType> map = new HashMap<>();
        for (ProductType productType : productTypes) {
            map.put(productType.getId(), productType);
            productType.setChildren(null);
        }

        for (ProductType productType : productTypes) {
            if (productType.getPid() == 0) {
                OneType.add(productType);
            } else {
                //获取到该类型的父级类型
                ProductType parent = map.get(productType.getPid());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                //将该类型放到其父级类型的子级类型中
                parent.getChildren().add(productType);
            }
        }
        return OneType;
    }
}
