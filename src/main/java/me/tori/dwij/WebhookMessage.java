package me.tori.dwij;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>7orivorian</b>
 * @since <b>June 07, 2023</b>
 */
public class WebhookMessage {

    private final String webhookUrl;
    private final String userAgent;
    private String avatarUrl = null;
    private String content = null;
    private String username = null;
    private boolean tts = false;
    private final List<Embed> embeds = new ArrayList<>();

    /**
     * Constructs a new {@linkplain WebhookMessage} instance
     *
     * @param webhookUrl The webhook URL, obtained from <a href="https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks">Discord</a>
     */
    public WebhookMessage(String webhookUrl, String userAgent) {
        this.webhookUrl = webhookUrl;
        this.userAgent = userAgent;
    }

    public WebhookMessage(String webhookUrl) {
        this(webhookUrl, "DWIJ");
    }

    public WebhookMessage withAvatar(String imageUrl) {
        this.avatarUrl = imageUrl;
        return this;
    }

    public WebhookMessage withContent(String content) {
        this.content = content;
        return this;
    }

    public WebhookMessage withUsername(String username) {
        this.username = username;
        return this;
    }

    public WebhookMessage withEmbed(Embed embed) {
        embeds.add(embed);
        return this;
    }

    public WebhookMessage withTts(boolean tts) {
        this.tts = tts;
        return this;
    }

    public HttpResponse execute() throws IOException {
        if ((content == null) && embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one Embed");
        }

        JSONObject json = toJSONObject();

        URL url = new URL(webhookUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", userAgent);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream out = connection.getOutputStream();
        out.write(json.toString().getBytes());
        out.flush();
        out.close();

        final HttpResponse httpResponse = new HttpResponse(connection.getResponseCode(), connection.getResponseMessage());

        connection.getInputStream().close();
        connection.disconnect();

        return httpResponse;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();

        json.put("content", content);
        json.put("username", username);
        json.put("avatar_url", avatarUrl);
        json.put("tts", tts);

        if (!embeds.isEmpty()) {
            List<JSONObject> embedObjects = new ArrayList<>();

            for (Embed embed : embeds) {
                JSONObject jsonEmbed = new JSONObject();

                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());

                if (embed.getColor() != null) {
                    Color color = embed.getColor();
                    int rgb = color.getRed();
                    rgb = (rgb << 8) + color.getGreen();
                    rgb = (rgb << 8) + color.getBlue();

                    jsonEmbed.put("color", rgb);
                }

                Embed.Footer footer = embed.getFooter();
                Embed.Image image = embed.getImage();
                Embed.Thumbnail thumbnail = embed.getThumbnail();
                Embed.Author author = embed.getAuthor();
                List<Embed.Field> fields = embed.getFields();

                if (footer != null) {
                    JSONObject jsonFooter = new JSONObject();

                    jsonFooter.put("text", footer.text());
                    jsonFooter.put("icon_url", footer.iconUrl());
                    jsonEmbed.put("footer", jsonFooter);
                }

                if (image != null) {
                    JSONObject jsonImage = new JSONObject();

                    jsonImage.put("url", image.url());
                    jsonEmbed.put("image", jsonImage);
                }

                if (thumbnail != null) {
                    JSONObject jsonThumbnail = new JSONObject();

                    jsonThumbnail.put("url", thumbnail.url());
                    jsonEmbed.put("thumbnail", jsonThumbnail);
                }

                if (author != null) {
                    JSONObject jsonAuthor = new JSONObject();

                    jsonAuthor.put("name", author.name());
                    jsonAuthor.put("url", author.url());
                    jsonAuthor.put("icon_url", author.iconUrl());
                    jsonEmbed.put("author", jsonAuthor);
                }

                List<JSONObject> jsonFields = new ArrayList<>();
                for (Embed.Field field : fields) {
                    JSONObject jsonField = new JSONObject();

                    jsonField.put("name", field.name());
                    jsonField.put("value", field.value());
                    jsonField.put("inline", field.inline());

                    jsonFields.add(jsonField);
                }

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }
        return json;
    }

    @Override
    public String toString() {
        return "WebhookMessage{" +
               "webhookURI='" + webhookUrl + '\'' +
               ", userAgent='" + userAgent + '\'' +
               ", content='" + content + '\'' +
               ", username='" + username + '\'' +
               ", avatarUrl='" + avatarUrl + '\'' +
               ", tts=" + tts +
               ", embeds=" + embeds +
               '}';
    }
}