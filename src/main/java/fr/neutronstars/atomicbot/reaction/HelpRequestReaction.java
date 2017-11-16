package fr.neutronstars.atomicbot.reaction;

import fr.neutronstars.atomicbot.util.ChannelManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.Arrays;
import java.util.Collection;

public class HelpRequestReaction implements IReaction
{
    private static final Collection<Permission> TEXT_PERMISSIONS = Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);
    private static final Collection<Permission> VOICE_PERMISSIONS = Arrays.asList(Permission.VOICE_CONNECT, Permission.VIEW_CHANNEL);

    public boolean onReaction(User user, Message message, MessageReaction.ReactionEmote emote, MessageReaction messageReaction)
    {
        switch (emote.getName())
        {
            case "✅":
                if(message.getEmbeds().size() > 0 && message.getMentionedUsers().size() > 0 && !message.getMentionedUsers().get(0).equals(user))
                {
                    MessageEmbed embed = message.getEmbeds().get(0);
                    User target = message.getMentionedUsers().get(0);
                    GuildController controller = message.getGuild().getController();

                    Role role = controller.createRole().setName(embed.getFields().get(0).getValue()).setMentionable(false).complete();

                    Channel textChannel = controller.createTextChannel(embed.getFields().get(0).getValue()+"-"+embed.getFields().get(1).getValue())
                            .setParent(message.getCategory()).addPermissionOverride(message.getGuild().getPublicRole(), null, TEXT_PERMISSIONS)
                            .addPermissionOverride(role, TEXT_PERMISSIONS, null).complete();

                    Channel voiceChannel = controller.createVoiceChannel(embed.getFields().get(0).getValue()+"-"+embed.getFields().get(1).getValue())
                            .setParent(message.getCategory()).addPermissionOverride(message.getGuild().getPublicRole(), null, VOICE_PERMISSIONS)
                            .addPermissionOverride(role, VOICE_PERMISSIONS, null).complete();

                    controller.addRolesToMember(message.getGuild().getMember(user), role).queue();
                    controller.addRolesToMember(message.getGuild().getMember(target), role).queue();

                    ChannelManager.get().addChannel(textChannel.getIdLong(), voiceChannel.getIdLong(), role.getIdLong(), target.getIdLong(), user.getIdLong());

                    message.delete().queue();

                    ((TextChannel)textChannel).sendMessage(target.getAsMention()+" ce channel est temporaire, "+user.getName()+" va vous aider à resoudre votre probleme."
                            +" Faites `?end <Note> <Commentaire>` quand vous aurez terminé. Un channel vocal est également à votre disposition.").queue();
                }
                break;
            case "❌":
                if(message.getEmbeds().size() > 0 && message.getMentionedUsers().size() > 0 && message.getMentionedUsers().get(0).equals(user))
                {
                    message.delete().queue();
                    break;
                }
            default:
                messageReaction.removeReaction(user).queue();
        }
        return true;
    }
}
