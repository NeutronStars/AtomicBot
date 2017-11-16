package fr.neutronstars.atomicbot.util;

import fr.neutronstars.atomicbot.AtomicBot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelManager implements Runnable
{
    private static final ChannelManager CHANNEL_MANAGER = new ChannelManager();

    private final Map<Long, ChannelInfo> channelMap = new HashMap<>();

    private ChannelManager(){}

    public static ChannelManager get()
    {
        return CHANNEL_MANAGER;
    }

    public void updateChannel(long id)
    {
        if(channelMap.containsKey(id))
        {
            channelMap.get(id).lastTime = System.currentTimeMillis();
            channelMap.get(id).info = false;
        }
    }

    public List<ChannelInfo> getChannelsInfo()
    {
        return new ArrayList<>(channelMap.values());
    }

    public void reset()
    {
        for(ChannelInfo channelInfo : getChannelsInfo()) delete(channelInfo);
        channelMap.clear();
    }

    public void delete(long id)
    {
        if(channelMap.containsKey(id))
        {
            delete(channelMap.get(id));
            channelMap.remove(id);
        }
    }

    public void addChannel(long textChannel, long voiceChannelId, long roleId, long userId, long helperId)
    {
        channelMap.put(textChannel, new ChannelInfo(textChannel, voiceChannelId, roleId, userId, helperId));
    }

    public boolean hasChannel(long channelId)
    {
        return channelMap.containsKey(channelId);
    }

    private void delete(ChannelInfo channelInfo)
    {
        JDA jda = AtomicBot.get().getJda();
        jda.getTextChannelById(channelInfo.textChannelId).delete().queue();
        jda.getVoiceChannelById(channelInfo.voiceChannelId).delete().queue();
        jda.getRoleById(channelInfo.roleId).delete();
    }

    public void run()
    {
        while(true)
        {
            long time = System.currentTimeMillis();

            for(ChannelInfo channelInfo : getChannelsInfo()){
                long currentTime = (time - channelInfo.lastTime)/1000;

                if(currentTime >= 1740 && !channelInfo.info)
                {
                    AtomicBot.get().getJda().getTextChannelById(channelInfo.textChannelId).sendMessage("Le channel se supprimera dans 1 minute pour inactivité.").queue();
                    channelInfo.info = true;
                }
                else if(currentTime >= 1800)
                {
                    delete(channelInfo);
                    channelMap.remove(channelInfo.textChannelId);
                }
            }

            try {
                Thread.sleep(10000);
            }catch (Exception e){}
        }
    }

    public boolean isCaller(long channelId, long callerId)
    {
        return channelMap.containsKey(channelId) && channelMap.get(channelId).userid == callerId;
    }

    public boolean isHelper(long channelId, long helperId)
    {
        return channelMap.containsKey(channelId) && channelMap.get(channelId).helperId == helperId;
    }

    public User getHelper(long channelId)
    {
        return channelMap.containsKey(channelId) ? AtomicBot.get().getJda().getUserById(channelMap.get(channelId).helperId) : null;
    }

    private static class ChannelInfo
    {
        private final long textChannelId, voiceChannelId, roleId, userid, helperId;
        private long lastTime;

        private boolean info;

        private ChannelInfo(long textChannelId, long voiceChannelId, long roleId, long userid, long helperId)
        {
            this.textChannelId = textChannelId;
            this.voiceChannelId = voiceChannelId;
            this.roleId = roleId;
            this.userid = userid;
            this.helperId = helperId;

            lastTime = System.currentTimeMillis();
        }
    }
}
