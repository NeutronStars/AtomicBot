package fr.neutronstars.atomicbot.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager
{
    private final static CommandManager COMMAND_MANAGER = new CommandManager();

    private final Map<String, Command> commandMap = new HashMap<>();

    private CommandManager(){}

    static {

    }

    public static void registerCommand(String name, Command command)
    {
        if(name == null || command == null) return;
        get().commandMap.put(name, command);
    }

    public static CommandManager get()
    {
        return COMMAND_MANAGER;
    }
}
