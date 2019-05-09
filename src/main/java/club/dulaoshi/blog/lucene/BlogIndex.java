package club.dulaoshi.blog.lucene;

import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author djg
 * @date 2019/5/6 17:31
 * @des 博客索引类
 */
@Component
public class BlogIndex {
    private Directory dir;

    private final LucenePath lucenePath;

    public BlogIndex(LucenePath lucenePath) {
        this.lucenePath = lucenePath;
    }

    /**
     * 获取indexWriter实例
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter()throws Exception{
//		dir = FSDirectory.open(Paths.get("H://lucene"));
        dir = FSDirectory.open(Paths.get(lucenePath.getPath()));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加博客索引
     * @param blog
     * @throws Exception
     */
    public void addIndex(Blog blog) throws Exception{
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(),Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    public void updateIndex(Blog blog)throws Exception{
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(),Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.close();
    }

    /**
     * 删除博客索引
     * @param blogId
     * @throws Exception
     */
    public void deleteIndex(String blogId) throws Exception{
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id",blogId));
        writer.forceMergeDeletes();//强制删除
        writer.commit();
        writer.close();
    }

    /**
     * 查询博客信息
     * @param searchContent
     * @return
     * @throws Exception
     */
    public List<Blog> blogSearch(String searchContent) throws Exception{
        dir = FSDirectory.open(Paths.get(lucenePath.getPath()));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(searchContent);

        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(searchContent);

        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);

        List<Blog> blogList = new LinkedList<Blog>();
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc = is.doc(scoreDoc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(doc.get("id")));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            String title = doc.get("title");
            String content = StringEscapeUtils.escapeHtml(doc.get("content"));
            if(title != null){
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if(StringUtil.isEmpty(hTitle)){
                    blog.setTitle(title);
                }else{
                    blog.setTitle(hTitle);
                }
            }

            if(content != null){
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if(StringUtil.isEmpty(hContent)){
                    if(content.length() <= 200){
                        blog.setContent(content);
                    }else{
                        blog.setContent(content.substring(0, 200));
                    }
                }else{
                    blog.setContent(hContent);
                }
            }
            blogList.add(blog);
        }
        return blogList;

    }
}
