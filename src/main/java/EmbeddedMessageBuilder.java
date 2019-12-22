import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbeddedMessageBuilder extends EmbedBuilder {

    Color defaultColor;

    public EmbeddedMessageBuilder() {
        super();
        defaultColor = new Color(228, 180, 0);
        init();
    }

    public EmbedBuilder clear() {
        super.clear();
        init();

        return this;
    }

    private void init() {
        setColor(defaultColor);
        setFooter("\nBot created by Ants on Seadoos", null);
    }

    public void createMessage(String title, String content) {
        this.addField("**" + title + "**", content, false);
    }

    public void sendMessage(MessageChannel channel) {
        channel.sendMessage(this.build()).queue();
    }
}
