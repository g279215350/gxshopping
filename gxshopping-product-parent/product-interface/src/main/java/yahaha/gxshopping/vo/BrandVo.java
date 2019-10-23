package yahaha.gxshopping.vo;

import lombok.Data;
import yahaha.gxshopping.domain.Brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
public class BrandVo {

    /**
     * 获取到的所有品牌
     */
    private List<Brand> brands = new ArrayList<>();

    /**
     * 获取到的品牌首字母，用Set去重排序
     */
    private Set<String> firstLetters = new TreeSet<>();
}
