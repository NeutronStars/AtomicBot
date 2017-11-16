package fr.neutronstars.atomicbot.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;

public class HelpRequestCommand implements Command
{
    // ?aide java spigot

    public boolean onCommand(Message message, String label, String[] args)
    {
        if(message.getChannel().getIdLong() != 380342833799888906L) return false;
        message.delete().queue();

        if(args.length < 3)
        {
            message.getAuthor().openPrivateChannel().complete().sendMessage("```?aide <Langage> <Librairies> <Problemes>```").queue();
            return true;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(message.getAuthor().getName(), null, message.getAuthor().getAvatarUrl());
        builder.addField("Language", args[0], false);
        builder.addField("Library", args[1], false);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 2; i < args.length; i++)
        {
            if(i > 2) stringBuilder.append(", ");
            stringBuilder.append(args[i]);
        }
        builder.addField("Problem", stringBuilder.toString(), false);
        builder.setColor(Color.magenta);
        builder.setFooter("✅ Accepter d'aider cette personne.", null);
        builder.setFooter("❌ Supprimer votre demande.", null);

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append(message.getAuthor().getAsMention()+" a demandé de l'aide.");
        messageBuilder.setEmbed(builder.build());

        message.getChannel().sendMessage(messageBuilder.build()).queue(msg ->{
            msg.addReaction("✅").queue();
            msg.addReaction("❌").queue();
        });
        return true;
    }
}
