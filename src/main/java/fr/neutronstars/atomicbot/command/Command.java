package fr.neutronstars.atomicbot.command;

import net.dv8tion.jda.core.entities.Message;

public interface Command
{
    boolean onCommand(Message message, String label, String[] args);
}
