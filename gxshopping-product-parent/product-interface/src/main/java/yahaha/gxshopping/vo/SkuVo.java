package yahaha.gxshopping.vo;

import lombok.Data;
import lombok.ToString;
import yahaha.gxshopping.domain.Specification;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class SkuVo {

    private List<Specification> skuProperties;

    private List<Map<String,String>> skus;
}
