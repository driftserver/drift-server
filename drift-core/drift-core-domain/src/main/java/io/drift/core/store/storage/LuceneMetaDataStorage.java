package io.drift.core.store.storage;

import io.drift.core.store.MetaData;
import io.drift.core.store.ModelStoreException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.drift.core.store.storage.StoragePath.fromExternal;

public class LuceneMetaDataStorage implements MetaDataStorage{

    public static final Path DEFAULT_LUCENE_INDEX_PATH = Paths.get("store", "index");

    public static final String PATH_FIELD_NAME = "path";
    public static final String TIME_STAMP_FIELD_NAME = "timeStamp";
    public static final String DESCRIPTION_FIELD_NAME = "description";
    public static final String ID_FIELD_NAME = "id";

    private IndexWriter writer;
    private Directory index;
    private Path indexPath;

    public LuceneMetaDataStorage(Path indexPath) {
        this.indexPath = indexPath;
        setup();
    }

    public LuceneMetaDataStorage() {
        this(DEFAULT_LUCENE_INDEX_PATH);
    }

    public void setup() {
        try {
            index = FSDirectory.open(indexPath);
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            writer = new IndexWriter(index, indexWriterConfig);
            writer.commit();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void writeMetaData(MetaData metaData) throws ModelStoreException {
        try {
            Document document = new Document();

            String id = metaData.getStorageId().getId();

            document.add(new StringField(ID_FIELD_NAME, id , Field.Store.YES));
            if (metaData.getDescription() != null)
                document.add(new TextField(DESCRIPTION_FIELD_NAME, metaData.getDescription(), Field.Store.YES));
            if (metaData.getTimeStamp() != null)
                document.add(new TextField(TIME_STAMP_FIELD_NAME, DateTools.dateToString(Timestamp.valueOf(metaData.getTimeStamp()), DateTools.Resolution.SECOND), Field.Store.YES));
            document.add(new TextField(PATH_FIELD_NAME, metaData.getPath().toExternal(), Field.Store.YES));

            writer.updateDocument(withId(id), document);
            writer.commit();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Term withId(String id) {
        return new Term(ID_FIELD_NAME, id);
    }

    @Override
    public MetaData readMetaData(StorageId storageId) throws ModelStoreException {
        try (IndexReader indexReader  = DirectoryReader.open(index)){

            IndexSearcher searcher = new IndexSearcher(indexReader);
            Query query = new TermQuery(withId(storageId.getId()));

            TopDocs topDocs = searcher.search(query, 1);
            Document document = toLuceneDocument(topDocs.scoreDocs[0], searcher);

            return toMetaData(document);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public MetaData toMetaData(Document document) {
        return MetaData.builder()
                .withStorageId(new StorageId(document.get(ID_FIELD_NAME)))
                .withDescription(document.get(DESCRIPTION_FIELD_NAME))
                .withPath(fromExternal(document.get(PATH_FIELD_NAME)))
                .build();
    }

    @Override
    public List<MetaData> forParentPath(StoragePath parentPath) throws ModelStoreException {
        try {
            IndexReader indexReader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(indexReader);

            Term term = new Term(PATH_FIELD_NAME, parentPath.toExternal());
            Query query = new TermQuery(term);
            TopDocs topDocs = searcher.search(query, 1024);

            return Stream.of(topDocs.scoreDocs)
                    .map(scoreDoc -> toLuceneDocument(scoreDoc, searcher))
                    .map(this::toMetaData)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Document toLuceneDocument(ScoreDoc scoreDoc, IndexSearcher searcher) {
        try {
           return searcher.doc(scoreDoc.doc);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void shutDown() {
        try {
            writer.close();
            index.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
