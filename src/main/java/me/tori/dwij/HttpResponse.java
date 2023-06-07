package me.tori.dwij;

import java.util.Objects;

/**
 * @author <b>7orivorian</b>
 * @since <b>June 07, 2023</b>
 */
public record HttpResponse(int code, String message) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        var that = (HttpResponse) obj;
        return (this.code == that.code) &&
               Objects.equals(this.message, that.message);
    }

    @Override
    public String toString() {
        return "HttpResponse[" +
               "code=" + code + ", " +
               "message=" + message + ']';
    }
}