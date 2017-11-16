package fr.neutronstars.atomicbot;

import fr.neutronstars.atomicbot.command.CommandManager;
import fr.neutronstars.atomicbot.json.JSONReader;
import fr.neutronstars.atomicbot.json.JSONWriter;
import fr.neutronstars.atomicbot.listener.AtomicListener;
import fr.neutronstars.atomicbot.reaction.ReactionManager;
import fr.neutronstars.atomicbot.util.ChannelManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AtomicBot
{
    private static AtomicBot atomicBot;
    private final Scanner scanner = new Scanner(System.in);
    private final long guildID = 238975753969074177L;
    private final JDA jda;

    private boolean running = true;

    private AtomicBot(String token) throws LoginException, RateLimitedException
    {
        jda = new JDABuilder(AccountType.BOT).addEventListener(new AtomicListener()).setToken(token).buildAsync();
    }

    private void run()
    {
        while (running)
        {
            if(scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if(line.equalsIgnoreCase("stop"))
                {
                    running = false;
                }
            }
        }

        jda.shutdown();
        scanner.close();

        System.out.println("Bot Stopped.");

        System.exit(0);
    }

    public JDA getJda()
    {
        return jda;
    }

    public Guild getGuild()
    {
        return jda.getGuildById(guildID);
    }

    public static void main(String... args)
    {
        System.out.println("Starting bot...");

        File file = new File("config.json");
        if(!file.exists())
        {
            try(JSONWriter writer = new JSONWriter(file)) {
                JSONObject object = new JSONObject();
                object.put("token", "Insert Your token here.");

                writer.write(object);
                writer.flush();

                System.out.println("Insert your token in the file \"config.json\".");
                return;
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try {
            CommandManager.get();
            ReactionManager.get();

            new Thread(ChannelManager.get(), "channelManager").start();

            JSONObject object = new JSONReader(file).toJSONObject();

            atomicBot = new AtomicBot(object.getString("token"));
            atomicBot.run();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static AtomicBot get()
    {
        return atomicBot;
    }
}
