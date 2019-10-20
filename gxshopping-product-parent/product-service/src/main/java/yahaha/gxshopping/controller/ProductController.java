package yahaha.gxshopping.controller;

import javafx.geometry.Pos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahaha.gxshopping.domain.Brand;
import yahaha.gxshopping.domain.Specification;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.service.IProductService;
import yahaha.gxshopping.domain.Product;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.service.ISkuService;
import yahaha.gxshopping.util.AjaxResult;
import yahaha.gxshopping.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yahaha.gxshopping.util.StrUtils;
import yahaha.gxshopping.vo.SkuVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ISkuService iSkuService;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.save(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Integer id){
        try {
            productService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //批量删除
    @RequestMapping(value = "/deleteBatch/{ids}", method = RequestMethod.POST)
    public AjaxResult deleteBatch(@PathVariable("ids") String ids){
        try {
            productService.removeByIds(StrUtils.splitStr2LongArr(ids));
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("操作失败！" + e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id)
    {
        return productService.getById(id);
    }


    /**
    * 查看所有
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){

        return productService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
        Page<Product> page = new Page<Product>(query.getPage(),query.getRows());
        IPage<Product> ipage = productService.page(page);
        return new PageList<>(ipage.getTotal(),ipage.getRecords());
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public PageList<Product> queryPage(@RequestBody ProductQuery productQuery){
        return productService.queryPage(productQuery);
    }

    /**
     * 获取显示属性
     * @param productId
     * @return
     */
    @GetMapping("/getViewProperties")
    public List<Specification> getViewProperties(Long productId){
        return productService.getViewProperties(productId);
    }

    /**
     * 更新显示属性
     * @param productId
     * @param viewProperties
     * @return
     */
    @PostMapping("/changeViewProperties")
    public AjaxResult changeViewProperties(@RequestParam("productId") Long productId,
                                           @RequestBody List<Specification> viewProperties){
//        System.out.println(productId);
//        viewProperties.forEach(e-> System.out.println(e));

        try {
            productService.changeViewProperties(productId,viewProperties);
            return AjaxResult.me().setMessage("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("显示属性修改失败！"+e.getMessage());
        }
    }

    /**
     * 获取Sku属性
     * @param productId
     * @return
     */
//    @GetMapping("/getSkuProperties")
//    public List<Specification> getSkuProperties(Long productId){
//        return productService.getSkuProperties(productId);
//    }
    @GetMapping("/getSkuProperties")
    public Map<String, Object> getSkuProperties(Long productId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("skuProperties", productService.getSkuProperties(productId));
        map.put("skus", iSkuService.findByProductId(productId));
        return map;
    }

    /**
     * 更新Sku属性
     * @param skuVo
     * @return
     */
    @PostMapping("/changeSkuProperties")
    public AjaxResult changeSkuProperties(@RequestParam("productId") Long productId,@RequestBody SkuVo skuVo){

        try {
            productService.changeSkuProperties(productId, skuVo.getSkuProperties(), skuVo.getSkus());
            return AjaxResult.me().setMessage("Sku属性更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("更新Sku属性失败！");
        }
    }
}
