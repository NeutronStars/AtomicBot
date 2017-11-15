package fr.neutronstars.atomicbot.reaction;

import net.dv8tion.jda.core.entities.MessageReaction;

import java.util.HashMap;
import java.util.Map;

public class ReactionManager
{
    private final static ReactionManager REACTION_MANAGER = new ReactionManager();

    private final Map<Long, IReaction> reactionMap = new HashMap<>();

    static {

    }

    private ReactionManager(){}

    public static ReactionManager get()
    {
        return REACTION_MANAGER;
    }

    public void onRecatin(Long channelId, MessageReaction message, MessageReaction.ReactionEmote emote)
    {
        if(reactionMap.containsKey(channelId))
            reactionMap.get(channelId).onReaction(emote, message);
    }

    private static void registerReaction(Long channel, IReaction reaction)
    {
        get().reactionMap.put(channel, reaction);
    }
}
