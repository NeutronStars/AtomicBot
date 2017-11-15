package fr.neutronstars.atomicbot.command;

import net.dv8tion.jda.core.entities.Message;

import java.util.HashMap;
import java.util.Map;

public class CommandManager
{
    private final static CommandManager COMMAND_MANAGER = new CommandManager();

    private final Map<String, Command> commandMap = new HashMap<>();

    static {

    }

    private CommandManager(){}

    public boolean hasCommand(String name)
    {
        return commandMap.containsKey(name);
    }

    public void executeCommand(Message message, String name, String[] args)
    {
        if(!hasCommand(name)) return;
        commandMap.get(name).onCommand(message, name, args);
    }

    public static CommandManager get()
    {
        return COMMAND_MANAGER;
    }

    public static void registerCommand(String name, Command command)
    {
        if(name == null || command == null) return;
        get().commandMap.put(name, command);
    }
}
