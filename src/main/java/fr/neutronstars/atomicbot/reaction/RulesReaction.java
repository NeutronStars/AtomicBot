package fr.neutronstars.atomicbot.reaction;

import fr.neutronstars.atomicbot.AtomicBot;
import fr.neutronstars.atomicbot.util.RoleManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;

public class RulesReaction implements IReaction
{
    public boolean onReaction(User user, Message message, MessageReaction.ReactionEmote emote, MessageReaction messageReaction)
    {
        switch (emote.getName())
        {
            case "✅":
                RoleManager.give(AtomicBot.get().getGuild().getMember(user), RoleManager.MEMBER);
                break;
            case "❌":
                System.out.println(AtomicBot.get());
                System.out.println(AtomicBot.get().getGuild());
                AtomicBot.get().getGuild().getController().kick(user.getId(), "N'accepte pas les règles.").queue();
                break;
            default:
                messageReaction.removeReaction(user).queue();
        }
        return true;
    }
}
