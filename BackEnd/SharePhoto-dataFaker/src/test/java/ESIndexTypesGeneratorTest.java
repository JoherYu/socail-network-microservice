import com.sharePhoto.dataFaker.ES.type.PhotoES;
import com.sharePhoto.dataFaker.ES.type.TagES;
import com.sharePhoto.dataFaker.ES.type.UserES;
import com.sharePhoto.dataFaker.dataFakerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Joher
 * @data 2019/6/6
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = dataFakerApplication.class)
public class ESIndexTypesGeneratorTest {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void generateIndexType(){
        elasticsearchTemplate.createIndex(PhotoES.class);
        elasticsearchTemplate.putMapping(PhotoES.class);
        elasticsearchTemplate.createIndex(TagES.class);
        elasticsearchTemplate.putMapping(TagES.class);
        elasticsearchTemplate.createIndex(UserES.class);
        elasticsearchTemplate.putMapping(UserES.class);
    }

    @Test
    public void deleteIndex(){
        elasticsearchTemplate.deleteIndex(PhotoES.class);
        elasticsearchTemplate.deleteIndex(TagES.class);
        elasticsearchTemplate.deleteIndex(UserES.class);
    }
}
