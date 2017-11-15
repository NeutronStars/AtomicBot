package fr.neutronstars.atomicbot.reaction;

import net.dv8tion.jda.core.entities.MessageReaction;

public interface IReaction
{
    boolean onReaction(MessageReaction.ReactionEmote emote, MessageReaction message);
}
