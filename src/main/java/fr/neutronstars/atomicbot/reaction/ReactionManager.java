package fr.neutronstars.atomicbot.reaction;

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

    private static void registerReaction(Long channel, IReaction reaction)
    {
        get().reactionMap.put(channel, reaction);
    }
}
