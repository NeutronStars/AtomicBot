package fr.neutronstars.atomicbot.command;

import fr.neutronstars.atomicbot.util.ChannelManager;
import net.dv8tion.jda.core.entities.Message;

public class EndCommand implements Command
{
    public boolean onCommand(Message message, String label, String[] args)
    {
        if(!ChannelManager.get().hasChannel(message.getChannel().getIdLong())
                || !ChannelManager.get().isCaller(message.getChannel().getIdLong(), message.getAuthor().getIdLong())) return false;

        message.delete().queue();

        if(args.length < 2)
        {
            message.getChannel().sendMessage("?end <Note> <Commentaire>").queue();
            return true;
        }

        int note = 0;

        try{
            note = Integer.parseInt(args[0]);
        }catch (NumberFormatException nfe) {
            message.getChannel().sendMessage("La note doit-être un chiffre.").queue();
            return true;
        }

        note = note < 0 ? 0 : note > 10 ? 10 : note;

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < args.length; i++)
        {
            if(i > 1) builder.append(" ");
            builder.append(args[i]);
        }

        ChannelManager.get().getHelper(message.getChannel().getIdLong()).openPrivateChannel().complete()
                .sendMessage(message.getAuthor().getAsMention()+" vous as noté "+note+" sur votre intervention."
                        +"```"+builder.toString()+"```").queue();

        ChannelManager.get().delete(message.getChannel().getIdLong());
        return true;
    }
}
