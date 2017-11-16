package fr.neutronstars.atomicbot.reaction;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;

public interface IReaction
{
    boolean onReaction(User user, Message message, MessageReaction.ReactionEmote emote, MessageReaction messageReaction);
}
