package yahaha.gxshopping.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import yahaha.gxshopping.domain.Brand;
import yahaha.gxshopping.mapper.BrandMapper;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yahaha.gxshopping.util.PageList;

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
}
