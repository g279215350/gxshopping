package yahaha.gxshopping.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import yahaha.gxshopping.domain.ProductDoc;

/**
 * @author gpl
 */
public interface ProductDocESRepository extends ElasticsearchRepository<ProductDoc, Long> {
}
