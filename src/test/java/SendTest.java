import me.tori.dwij.Embed;
import me.tori.dwij.WebhookMessage;

import java.awt.*;

/**
 * @author <b>7orivorian</b>
 * @since 2.0.0
 */
public class SendTest {

    public static void main(String[] args) {
        // Define urls to keep your code pretty ;P
        String webhookUrl = args[0];
        String avatarUrl = args[1];

        // Construct your fantastic message!
        WebhookMessage message = new WebhookMessage(webhookUrl)
                .avatar(avatarUrl)
                .username("Happy boi")
                .content("Hello world!")
                .tts(false)
                .addEmbed(
                        new Embed()
                                .title("My embed title!")
                                .url("https://github.com/7orivorian")
                                .description("cute lil guyyy")
                                .image("https://i.pinimg.com/originals/1b/34/7c/1b347cf538cf2099ed59d88a68c312b9.jpg")
                                .footer("Powered by 7orivorian <3", "https://avatars.githubusercontent.com/u/61598620?v=4")
                                .color(new Color(0x00EEFF))
                );

        // Send your message to all your friends :D
        message.execute()
                .then((code, msg) -> System.out.println("Message sent to webhook!"))
                .except(e -> e.printStackTrace());
    }
}