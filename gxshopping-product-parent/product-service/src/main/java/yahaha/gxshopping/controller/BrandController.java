package yahaha.gxshopping.controller;

import yahaha.gxshopping.service.IBrandService;
import yahaha.gxshopping.domain.Brand;
import yahaha.gxshopping.query.BrandQuery;
import yahaha.gxshopping.util.AjaxResult;
import yahaha.gxshopping.util.LetterUtil;
import yahaha.gxshopping.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yahaha.gxshopping.util.StrUtils;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    public IBrandService brandService;

    /**
    * 保存和修改公用的
    * @param brand  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Brand brand){
        brand.setFirstLetter(LetterUtil.getFirstLetter(brand.getName()).toUpperCase());
        System.out.println(brand);
        try {
            if(brand.getId()!=null){
                brandService.updateById(brand);
            }else{
                brandService.save(brand);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("操作失败！"+e.getMessage());
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
            brandService.removeById(id);
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
            brandService.removeByIds(StrUtils.splitStr2LongArr(ids));
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("操作失败！" + e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Brand get(@PathVariable("id") Long id)
    {
        return brandService.getById(id);
    }


    /**
    * 查看所有
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Brand> list(){

        return brandService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Brand> json(@RequestBody BrandQuery query)
    {
        Page<Brand> page = new Page<Brand>(query.getPage(),query.getRows());
        IPage<Brand> ipage = brandService.page(page);
        return new PageList<Brand>(ipage.getTotal(),ipage.getRecords());
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public PageList<Brand> queryPage(@RequestBody BrandQuery brandQuery){
        return brandService.queryPage(brandQuery);
    }
}
