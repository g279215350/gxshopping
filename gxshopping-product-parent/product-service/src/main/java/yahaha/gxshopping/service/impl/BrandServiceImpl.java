package yahaha.gxshopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import yahaha.gxshopping.domain.Brand;
import yahaha.gxshopping.mapper.BrandMapper;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yahaha.gxshopping.util.PageList;
import yahaha.gxshopping.vo.BrandVo;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author gpl
 * @since 2019-10-12
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {


    @Override
    public PageList<Brand> queryPage(BrandQuery brandQuery) {
        IPage<Brand> iPage =
                baseMapper.pageQuery(new Page(brandQuery.getPage(), brandQuery.getRows()), brandQuery);
        return new PageList<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public BrandVo brandsCrumb(Long productTypeId) {
        List<Brand> brands =
                baseMapper.selectList(new QueryWrapper<Brand>().eq("product_type_id", productTypeId));
        BrandVo result = new BrandVo();
        result.setBrands(brands);
        Set<String> firstLetters = new TreeSet<>();
        for (Brand brand : brands) {
            firstLetters.add(brand.getFirstLetter());
        }
        result.setFirstLetters(firstLetters);
        return result;
    }

    @Override
    public boolean save(Brand brand) {
        brand.setCreateTime(System.currentTimeMillis());
        return super.save(brand);
    }

    @Override
    public boolean updateById(Brand brand) {
        brand.setUpdateTime(System.currentTimeMillis());
        return super.updateById(brand);
    }
}
