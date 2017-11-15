package fr.neutronstars.atomicbot.listener;

import fr.neutronstars.atomicbot.command.CommandManager;
import fr.neutronstars.atomicbot.reaction.ReactionManager;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AtomicListener extends ListenerAdapter
{
    public void onReady(ReadyEvent event)
    {
        System.out.println("The bot is ready.");
        event.getJDA().getPresence().setStatus(OnlineStatus.INVISIBLE);
    }

    public void onMessageReactionAdd(MessageReactionAddEvent event)
    {
        if(event.getUser().isBot() || event.getGuild() == null) return;
        ReactionManager.get().onRecatin(event.getChannel().getIdLong(), event.getReaction(), event.getReactionEmote());
    }

    public void onMessageReceived(MessageReceivedEvent event)
    {
        if(event.getAuthor().isBot() || event.getGuild() == null) return;

        if(event.getMessage().getContent().startsWith("?"))
        {
            String[] command = event.getMessage().getContent().replaceFirst("\\?", "").split(" ");
            String[] args = new String[command.length-1];
            for(int i = 0; i < args.length; i++) args[i] = command[i+1];
            CommandManager.get().executeCommand(event.getMessage(), command[0], args);
        }
    }
}
