package me.tori.dwij;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/7orivorian">7orivorian</a>
 * @since 2.0.0
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
    private final List<Field> fields = new ArrayList<>();

    public static Embed from(@NotNull Embed parent) {
        Embed child = new Embed()
                .title(parent.title)
                .url(parent.url)
                .author(parent.author.name, parent.author.url, parent.author.iconUrl)
                .description(parent.description)
                .image(parent.image.url)
                .thumbnail(parent.thumbnail.url)
                .footer(parent.footer.text, parent.footer.iconUrl)
                .color(parent.color);
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

    public Embed title(String title) {
        this.title = title;
        return this;
    }

    public Embed title(String title, String url) {
        this.title = title;
        this.url = url;
        return this;
    }

    public Embed url(String url) {
        this.url = url;
        return this;
    }

    public Embed description(String description) {
        this.description = description;
        return this;
    }

    public Embed color(Color color) {
        this.color = color;
        return this;
    }

    public Embed footer(String text, String icon) {
        footer = new Footer(text, icon);
        return this;
    }

    public Embed thumbnail(String url) {
        thumbnail = new Thumbnail(url);
        return this;
    }

    public Embed image(String url) {
        image = new Image(url);
        return this;
    }

    public Embed author(String name, String url, String icon) {
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