package com.test;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.wh.Application;
import com.wh.es.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 不建议使用ElasticsearchTemplate 对索引进行管理（创建索引，更新映射，删除索引）
     * 索引就像是数据库或者数据库中的表，我们平时是不会通过java代码频繁去创建修改删除数据库或者表的
     * 我们只会针对数据进行数据进行curd的操作
     * 在es中也是同理，我们尽量使用 ElasticsearchTemplate 对数据文档做curd的操作
     * 1.属性（FieldType）类型不灵活
     * 2.主分片和副本数无法设置
     */
    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1002L);
        stu.setName("rose");
        stu.setAge(22);
        stu.setMoney(18.8f);
        stu.setSign("风城玫瑰");
        stu.setDescription("风城玫瑰");

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    @Test
    public void deleteIndexStu() {
        esTemplate.deleteIndex(Stu.class);
    }


//  ---------------------------------------- 分割线-----------------------------


    @Test
    public void updateStuDoc() {

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("sign", "it's mvp");
        sourceMap.put("money", 22.2f);
        sourceMap.put("age", 25);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(sourceMap);

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1002")
                .withIndexRequest(indexRequest)
                .build();
        esTemplate.update(updateQuery);

    }

    @Test
    public void getStuDoc() {
        GetQuery getQuery = new GetQuery();
        getQuery.setId("1002");
        Stu stu = esTemplate.queryForObject(getQuery, Stu.class);
        System.out.println(stu);
    }

    @Test
    public void deleteStuDoc() {
        esTemplate.delete(Stu.class, "1002");
    }


//  ---------------------------------------- 分割线-----------------------------

    @Test
    public void searchStuDoc() {

        Pageable pageable = PageRequest.of(0, 10);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "总冠军"))
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> stus = esTemplate.queryForPage(searchQuery, Stu.class);
        System.out.println("总分页数为：" + stus.getTotalPages());
        List<Stu> stuList = stus.getContent();
        stuList.forEach(System.out::println);
    }

    @Test
    public void highlightStuDoc() {

        String preTag = "<font color = 'red'>";
        String postTag = "</font >";
        Pageable pageable = PageRequest.of(0, 10);

        SortBuilder sortBuilder = new FieldSortBuilder("money").order(SortOrder.ASC);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "总冠军"))
                .withHighlightFields(new HighlightBuilder.Field("description").preTags(preTag).postTags(postTag))
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();
        AggregatedPage<Stu> stus = esTemplate.queryForPage(searchQuery, Stu.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Stu> stuList = new ArrayList<>();

                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    //获取高亮数据
                    HighlightField description = hit.getHighlightFields().get("description");
                    String desc = description.getFragments()[0].toString();

                    //重新构造数据
                    Stu stu = new Stu();
                    stu.setDescription(desc);
                    stu.setStuId(Long.valueOf(hit.getSourceAsMap().get("stuId").toString()));
                    stu.setAge((Integer) hit.getSourceAsMap().get("age"));
                    stu.setName((String) hit.getSourceAsMap().get("name"));
                    stu.setSign((String) hit.getSourceAsMap().get("sign"));
                    stu.setMoney(Float.valueOf(hit.getSourceAsMap().get("money").toString()));

                    stuList.add(stu);
                }

                if (stuList.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) stuList);
                }
                return null;
            }
        });
        System.out.println("总分页数为：" + stus.getTotalPages());
        List<Stu> stuList = stus.getContent();
        stuList.forEach(System.out::println);
    }

}
