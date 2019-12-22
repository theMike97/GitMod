import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    String commandMark = "!";
    String embeddedMessage = "";
    EmbeddedMessageBuilder embeddedBuilder = new EmbeddedMessageBuilder();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String msgText = event.getMessage().getContentRaw().trim();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        // ignore messages by other bots
        if (author.isBot())
            return;

        /*
        Commands the bot should listen for:
        -----------------------------------
        link git <*.git url>
        link user <profile url>

        notify commits
        notify merges
        notify all

        mute commits
        mute merges
        mute all

        help
         */

        // if message doesnt start with command mark, ignore message
        if (!msgText.startsWith(commandMark))
            return;
        msgText = msgText.substring(1);

        //clear the message builder before sending out a message
        if (!embeddedBuilder.isEmpty())
            embeddedBuilder.clear();

        String[] commandArgs = msgText.split("\\s+");
        System.out.println(Arrays.toString(commandArgs));

        // link commands
        if (commandArgs[0].equals("link")) {
            if (commandArgs.length != 3)
                return;

            // git
            if (commandArgs[1].equals("git")) {
                String url = commandArgs[2];
                try {
                    Document doc = Jsoup.connect(url).get();
                    System.out.println("Connection successful.\nCreating categories and roles.");
                    embeddedBuilder.createMessage("Connection successful.", "Linked **" + doc.title() + "** to this server!\n" +
                            "Creating categories and roles for _X_ branches...");
                    embeddedBuilder.sendMessage(channel);
                } catch (IOException exception) {
                    System.out.println("Connection to " + url + " failed.");
                    embeddedBuilder.createMessage("Connection failed", "IOException: Connection to " + url + " failed.  Perhaps you copied the URL incorrectly?");
                    embeddedBuilder.sendMessage(channel);
                }
            }

            // user
            else if (commandArgs[1].equals("user")) {

            }
        }
    }
}
