package pl.snobka.arrived1.snobka;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Entry {
    int id;
    String title;
    String link;
    String updated;
    String name;
    String author;
    String newsId;
    String summary;
    byte[] image;
    Bitmap bitmap;
    ByteArrayOutputStream bos;

    public static final String ID = "id";
    public static final String ENTRY = "entry";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String UPDATED = "updated";
    public static final String AUTHOR = "author";
    public static final String NEWSID = "id";
    public static final String SUMMARY = "summary";
    public static final String IMAGE = "image";

    Entry() {
        this.id = 0;
        this.title = "";
        this.link = "";
        this.updated = "";
        this.author = "";
        this.newsId = "";
        this.summary = "";
        this.image = null;
        this.bitmap = null;
        this.bos = new ByteArrayOutputStream();
    }

    Entry(String title, String link, String updated, String author, String newsId, String summary, byte[] image) {
        this.id = 0;
        this.title = title;
        this.link = link;
        this.updated = updated;
        this.author = author;
        this.newsId = newsId;
        this.summary = summary;
        this.image = image;
        this.bitmap = BitmapFactory.decodeByteArray(this.image, 0, this.image.length);
        this.bos = new ByteArrayOutputStream();
    }

    public Bitmap getBitmap() {
        if(bitmap == null)
            return BitmapFactory.decodeByteArray(this.image , 0, this.image.length);
        return bitmap;
    }

    public byte[] getImage() { return image; }

    public void setImage(Bitmap image) {
        this.bitmap = image;

        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        this.image = bos.toByteArray();
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdated() {
        return updated;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getNewsId() {
        return newsId;
    }

    public String getSummary() {
        return summary;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

}
