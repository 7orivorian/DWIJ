package me.tori.dwij;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author <a href="https://github.com/7orivorian">7orivorian</a>
 * @since 2.0.0
 */
public final class HttpResponse {

    private final int code;
    @Nullable
    private final String message;
    @Nullable
    private final Exception exception;

    public HttpResponse(int code, String message) {
        this(code, message, null);
    }

    public HttpResponse(@Nullable Exception exception) {
        this.code = -1;
        this.message = "Exception thrown";
        this.exception = exception;
    }

    private HttpResponse(int code, @Nullable String message, @Nullable Exception exception) {
        this.code = code;
        this.message = message;
        this.exception = exception;
    }

    @NotNull
    public static HttpResponse of(@NotNull HttpsURLConnection connection) throws IOException {
        return new HttpResponse(connection.getResponseCode(), connection.getResponseMessage());
    }

    @NotNull
    public static HttpResponse of(@NotNull Exception e) {
        return new HttpResponse(e);
    }

    public HttpResponse then(BiConsumer<Integer, String> action) {
        if (message != null) {
            action.accept(code, message);
        }
        return this;
    }

    public HttpResponse except(Consumer<Exception> action) {
        if (exception != null) {
            action.accept(exception);
        }
        return this;
    }

    public int code() {
        return code;
    }

    @Nullable
    public String message() {
        return message;
    }

    @Nullable
    public Exception exception() {
        return exception;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ((object == null) || (getClass() != object.getClass())) {
            return false;
        }

        HttpResponse that = (HttpResponse) object;
        return (code == that.code)
               && Objects.equals(message, that.message)
               && Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {
        int result = code;
        result = (31 * result) + Objects.hashCode(message);
        result = (31 * result) + Objects.hashCode(exception);
        return result;
    }
}