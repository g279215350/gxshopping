package yahaha.gxshopping.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author gpl
 */
@Data
@Document(indexName = "gxshopping")
public class ProductDoc {

    @Id
    private Long id;

    /**
     *	关键字
     *	分词+索引
     *  all："商品标题 商品副标题 类型名称 品牌名称"中间拼接空格做分词用
     */
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String all;

    private Long productTypeId;

    private Long brandId;

    /**
     *	最高价格
     */
    private Integer maxPrice;

    /**
     *	最低价格
     */
    private Integer minPrice;

    /**
     *	销售数量
     */
    private Integer saleCount;

    /**
     *	上架时间
     */
    private Long onSaleTime;

    /**
     *	评论数量
     */
    private Integer commentCount;

    /**
     *	浏览数量
     */
    private Integer viewCount;

    /**
     *	商品名称
     */
    @Field(store = true, type = FieldType.Text)
    private String name;

    /**
     *	商品副标题
     */
    @Field(store = true, type = FieldType.Text)
    private String subName;

    /**
     *	商品媒体属性
     */
    @Field(store=true, type = FieldType.Text)
    private String medias;

    /**
     *	显示属性
     */
    @Field(store=true, type = FieldType.Text)
    private String viewProperties;

    /**
     *	sku属性
     */
    @Field(store=true, type = FieldType.Text)
    private String skuProperties;

}
