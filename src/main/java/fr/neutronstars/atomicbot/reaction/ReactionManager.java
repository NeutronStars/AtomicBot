package fr.neutronstars.atomicbot.reaction;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.Map;

public class ReactionManager
{
    private final static ReactionManager REACTION_MANAGER = new ReactionManager();

    private final Map<Long, IReaction> reactionMap = new HashMap<>();

    static {
        registerReaction(380343604155252739L, new RulesReaction());
        registerReaction(380342833799888906L, new HelpRequestReaction());
    }

    private ReactionManager(){}

    public static ReactionManager get()
    {
        return REACTION_MANAGER;
    }

    public void onReaction(Long channelId, User user, Message message, MessageReaction messageReaction, MessageReaction.ReactionEmote emote)
    {
        if(reactionMap.containsKey(channelId))
            reactionMap.get(channelId).onReaction(user, message, emote, messageReaction);
    }

    private static void registerReaction(Long channel, IReaction reaction)
    {
        get().reactionMap.put(channel, reaction);
    }
}
