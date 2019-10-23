package yahaha.gxshopping.controller;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yahaha.gxshopping.domain.ProductDoc;
import yahaha.gxshopping.domain.ProductParam;
import yahaha.gxshopping.repository.ProductDocESRepository;
import yahaha.gxshopping.util.PageList;

import java.util.List;

/**
 *
 * @author gpl
 */
@RestController
public class ProductDocESController {

    @Autowired
    private ProductDocESRepository repository;

    /**
     * 批量添加，上架商品
     * @param productDocs
     */
    @PostMapping("/es/saveBatch")
    public void saveBatch(@RequestBody List<ProductDoc> productDocs){
        repository.saveAll(productDocs);
    }

    /**
     * 批量删除，下架商品
     * @param ids
     */
    @PostMapping("/es/deleteBatch")
    public void deleteBatch(@RequestBody List<Long> ids){
        for (Long id : ids) {
            repository.deleteById(id);
        }
    }

    /**
     * 条件搜索商品数据
     * @param param
     * @return
     */
    @PostMapping("/es/products")
    public PageList<ProductDoc> search(@RequestBody ProductParam param){

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        //查询条件过滤
        BoolQueryBuilder bool = new BoolQueryBuilder();

        //关键字查询
        if (StringUtils.isNotEmpty(param.getKeyword())) {
            bool.must(new MatchQueryBuilder("all", param.getKeyword()));
        }
        //类型编号
        if (param.getProductTypeId() != null) {
            bool.must(new MatchQueryBuilder("productTypeId", param.getProductTypeId()));
        }
        //商品编号
        if (param.getBrandId() != null) {
            bool.must(new MatchQueryBuilder("brandId", param.getBrandId()));
        }
        /**
         * 价格区间：
         *      查询最大值大于原商品最小值
         *      查询最小值小于原商品最大值
         */
        if (param.getMinPrice() != null) {
            bool.must(new RangeQueryBuilder("maxPrice").gte(param.getMinPrice()));
        }
        if (param.getMaxPrice() != null) {
            bool.must(new RangeQueryBuilder("minPrice").lte(param.getMaxPrice()));
        }
        builder.withQuery(bool);

        //排序
        //排序列
        String sortColumn = "saleCount";
        if (StringUtils.isNotEmpty(param.getSortField())) {
            sortColumn = param.getSortField();
        }
        //排序方式
        SortOrder sortOrder = SortOrder.DESC;
        if ("asc".equalsIgnoreCase(param.getSortType())) {
            sortOrder = SortOrder.ASC;
        }
        builder.withSort(new FieldSortBuilder(sortColumn).order(sortOrder));

        //分页
        builder.withPageable(PageRequest.of(param.getPage() - 1, param.getRows()));

        //用配置好的条件去查询
        Page<ProductDoc> result = repository.search(builder.build());

        PageList<ProductDoc> pageList = new PageList<>(result.getTotalElements(),result.getContent());
        return pageList;
    }
}
