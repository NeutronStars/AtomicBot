package fr.neutronstars.atomicbot.listener;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AtomicListener extends ListenerAdapter
{
    public void onReady(ReadyEvent event)
    {
        System.out.println("The bot is ready.");
        event.getJDA().getPresence().setStatus(OnlineStatus.INVISIBLE);
    }
}
