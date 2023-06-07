import me.tori.dwij.Embed;
import me.tori.dwij.WebhookMessage;

import java.awt.*;
import java.io.IOException;

/**
 * @author <b>7orivorian</b>
 * @since <b>June 07, 2023</b>
 */
public class Main {

    public static void main(String[] args) {
        // Define urls to keep your code pretty ;P
        String webhookUrl = "<your_webhook_url_here>";
        String avatarUrl = "<your_avatar_url_here>";

        // Construct your fantastic message!
        WebhookMessage message = new WebhookMessage(webhookUrl)
                .withAvatar(avatarUrl)
                .withUsername("Happy boi")
                .withContent("Hello world!")
                .withTts(false)
                .withEmbed(
                        new Embed()
                                .withTitle("My embed title!")
                                .withUrl("https://github.com/7orivorian")
                                .withDescription("cute lil guyyy")
                                .withImage("https://i.pinimg.com/originals/1b/34/7c/1b347cf538cf2099ed59d88a68c312b9.jpg")
                                .withFooter("Powered by 7orivorian <3", "https://avatars.githubusercontent.com/u/61598620?v=4")
                                .withColor(new Color(0x00EEFF))
                );

        // Send your message to all your friends :D
        try {
            message.execute();
        } catch (IOException e) {
            // Catch any exceptions for bug-squashing ease :>
            e.printStackTrace();
        }
    }
}