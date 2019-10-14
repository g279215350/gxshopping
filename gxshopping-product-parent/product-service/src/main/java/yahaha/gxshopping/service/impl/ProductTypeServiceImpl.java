package yahaha.gxshopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import yahaha.gxshopping.client.RedisClient;
import yahaha.gxshopping.client.StaticPageClient;
import yahaha.gxshopping.domain.ProductType;
import yahaha.gxshopping.mapper.ProductTypeMapper;
import yahaha.gxshopping.service.IProductTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StaticPageClient staticPageClient;

    /**
     * 静态化商城首页
     */
    @Override
    public void genHomePage() {
        //先根据product.type.vm模板生成product.type.vm.html
        String templatePath = "D:\\java\\IdeaProjects\\gxshopping\\gxshopping-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm";//模板路径
        String targetPath = "D:\\java\\IdeaProjects\\gxshopping\\gxshopping-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm.html";
        List<ProductType> productTypes = loadTreeType();
        staticPageClient.creatStaticPage(templatePath,targetPath,productTypes);

        //再根据home.vm生成html.html
        templatePath = "D:\\java\\IdeaProjects\\gxshopping\\gxshopping-product-parent\\product-service\\src\\main\\resources\\template\\home.vm";
        targetPath = "D:\\java\\IdeaProjects\\gxshopping-web-parent\\ecommerce\\home.html";
        Map<String,Object> model = new HashMap<>();
        model.put("staticRoot","D:\\java\\IdeaProjects\\gxshopping\\gxshopping-product-parent\\product-service\\src\\main\\resources\\");
        staticPageClient.creatStaticPage(templatePath,targetPath,model);
    }

    /**
     * 加载类型树
     * @return
     */
    @Override
    public List<ProductType> loadTreeType() {
        //先去Redis中查询是否有数据
        String productType = redisClient.get("productTypes");
        if (productType == null) {
            synchronized (this) {
                productType = redisClient.get("productTypes");
                if (productType == null) {
                    //去数据库里面查询数据返回
                    List<ProductType> productTypes = loadTreeMenu();
                    //将查询到的数据存入到Redis中
                    redisClient.set("productTypes", JSON.toJSONString(productTypes));
                    return productTypes;
                }
            }
        }
        //有数据直接获取返回
        return JSONArray.parseArray(productType, ProductType.class);
    }

    /**
     * 增删改同步操作
     * @return
     */
    public void synchronizedOption(){
        List<ProductType> productTypes = loadTreeMenu();
        redisClient.set("productTypes", JSON.toJSONString(productTypes));
        genHomePage();
    }



    @Override
    public boolean save(ProductType entity) {
        boolean status =  super.save(entity);
        synchronizedOption();
        return status;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean status =  super.removeById(id);
        synchronizedOption();
        return status;
    }

    @Override
    public boolean updateById(ProductType entity) {
        boolean status =  super.updateById(entity);
        synchronizedOption();
        return status;
    }

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
