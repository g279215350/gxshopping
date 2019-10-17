package yahaha.gxshopping.controller;

import yahaha.gxshopping.domain.Brand;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.service.IProductService;
import yahaha.gxshopping.domain.Product;
import yahaha.gxshopping.query.ProductQuery;
import yahaha.gxshopping.util.AjaxResult;
import yahaha.gxshopping.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yahaha.gxshopping.util.StrUtils;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

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
}
