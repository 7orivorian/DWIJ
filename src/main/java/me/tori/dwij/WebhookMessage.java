package me.tori.dwij;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author <a href="https://github.com/7orivorian">7orivorian</a>
 * @since 2.0.0
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
     * Constructs a new {@linkplain WebhookMessage} instance.
     *
     * @param webhookUrl The webhook URL, obtained from <a
     *                   href="https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks">Discord</a>
     */
    public WebhookMessage(String webhookUrl, String userAgent) {
        this.webhookUrl = webhookUrl;
        this.userAgent = userAgent;
    }

    public WebhookMessage(String webhookUrl) {
        this(webhookUrl, "DWIJ");
    }

    public WebhookMessage avatar(String imageUrl) {
        this.avatarUrl = imageUrl;
        return this;
    }

    public WebhookMessage content(String content) {
        this.content = content;
        return this;
    }

    public WebhookMessage username(String username) {
        this.username = username;
        return this;
    }

    public WebhookMessage addEmbed(Embed embed) {
        if (embeds.size() >= 10) {
            throw new IllegalArgumentException("Too many embeds");
        }
        embeds.add(embed);
        return this;
    }

    public WebhookMessage tts(boolean tts) {
        this.tts = tts;
        return this;
    }

    public ResHandler<Void> execute() {
        if ((content == null) && embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one Embed");
        }

        JSONObject json = toJSONObject();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .header("Content-Type", "application/json")
                .header("User-Agent", userAgent)
                .build();
        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return new ResHandler<>(response);
        } catch (IOException | InterruptedException e) {
            return new ResHandler<>(e);
        }
    }

    @NotNull
    private JSONObject toJSONObject() {
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

    public static final class ResHandler<T> {
        @Nullable
        private final HttpResponse<T> response;
        @Nullable
        private final Exception exception;

        @Contract(pure = true)
        public ResHandler(@Nullable HttpResponse<T> response) {
            this(response, null);
        }

        @Contract(pure = true)
        public ResHandler(@Nullable Exception exception) {
            this(null, exception);
        }

        @Contract(pure = true)
        private ResHandler(@Nullable HttpResponse<T> response, @Nullable Exception exception) {
            this.response = response;
            this.exception = exception;
        }

        @Contract("_ -> this")
        public ResHandler<T> then(Consumer<HttpResponse<T>> action) {
            if (response != null) {
                action.accept(response);
            }
            return this;
        }

        @Contract("_ -> this")
        public ResHandler<T> except(Consumer<Exception> action) {
            if (exception != null) {
                action.accept(exception);
            }
            return this;
        }

        @Nullable
        @Contract(pure = true)
        public HttpResponse<T> response() {
            return response;
        }

        @Nullable
        @Contract(pure = true)
        public Exception exception() {
            return exception;
        }
    }
}