package fr.neutronstars.atomicbot.util;

import fr.neutronstars.atomicbot.AtomicBot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class RoleManager
{
    public static final long MEMBER = 380337877843836929L;

    public static void give(Member member, long roleId)
    {
        Guild guild = AtomicBot.get().getGuild();
        guild.getController().addRolesToMember(member, guild.getRoleById(roleId)).queue();
    }
}
