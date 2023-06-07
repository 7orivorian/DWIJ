package me.tori.dwij;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>7orivorian</b>
 * @since <b>June 07, 2023</b>
 */
public class Embed {

    private String title;
    private String description;
    private String url;
    private Color color;

    private Footer footer;
    private Thumbnail thumbnail;
    private Image image;
    private Author author;
    private final java.util.List<Field> fields = new ArrayList<>();

    public static Embed from(Embed parent) {
        Embed child = new Embed()
                .withTitle(parent.title)
                .withUrl(parent.url)
                .withAuthor(parent.author.name, parent.author.url, parent.author.iconUrl)
                .withDescription(parent.description)
                .withImage(parent.image.url)
                .withThumbnail(parent.thumbnail.url)
                .withFooter(parent.footer.text, parent.footer.iconUrl)
                .withColor(parent.color);
        for (Field field : parent.getFields()) {
            child.addField(field.name, field.value, field.inline);
        }
        return child;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Author getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Footer getFooter() {
        return footer;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Image getImage() {
        return image;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Color getColor() {
        return color;
    }

    public Embed withTitle(String title) {
        this.title = title;
        return this;
    }

    public Embed withTitle(String title, String url) {
        this.title = title;
        this.url = url;
        return this;
    }

    public Embed withUrl(String url) {
        this.url = url;
        return this;
    }

    public Embed withDescription(String description) {
        this.description = description;
        return this;
    }

    public Embed withColor(Color color) {
        this.color = color;
        return this;
    }

    public Embed withFooter(String text, String icon) {
        footer = new Footer(text, icon);
        return this;
    }

    public Embed withThumbnail(String url) {
        thumbnail = new Thumbnail(url);
        return this;
    }

    public Embed withImage(String url) {
        image = new Image(url);
        return this;
    }

    public Embed withAuthor(String name, String url, String icon) {
        author = new Author(name, url, icon);
        return this;
    }

    public Embed addField(String name, String value, boolean inline) {
        fields.add(new Field(name, value, inline));
        return this;
    }

    @Override
    public String toString() {
        return "Embed{" +
               "title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", url='" + url + '\'' +
               ", color=" + color +
               ", footer=" + footer +
               ", thumbnail=" + thumbnail +
               ", image=" + image +
               ", author=" + author +
               ", fields=" + fields +
               '}';
    }

    public record Footer(String text, String iconUrl) {
    }

    public record Thumbnail(String url) {
    }

    public record Image(String url) {
    }

    public record Author(String name, String url, String iconUrl) {
    }

    public record Field(String name, String value, boolean inline) {
    }
}