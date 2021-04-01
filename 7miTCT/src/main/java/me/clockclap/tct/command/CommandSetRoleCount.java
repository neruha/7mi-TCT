package me.clockclap.tct.command;

import com.google.common.base.Charsets;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class CommandSetRoleCount implements TabExecutor {

    private NanamiTct plugin;
    private String usage = ChatColor.RED + "Usage: /setrolecount <role> <count>";

    public CommandSetRoleCount(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @Deprecated
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            boolean isAdmin = false;
            if(plugin.getTctConfig().getConfig().getStringList("admin").contains("op")) {
                if(p.isOp()) {
                    isAdmin = true;
                }
            }
            if(isAdmin == false) {
                for (String str : plugin.getTctConfig().getConfig().getStringList("admin")) {
                    String name = NanamiTct.utilities.resetColor(p.getName());
                    if (name.equalsIgnoreCase(str)) {
                        isAdmin = true;
                        break;
                    }
                }
            }
            if(isAdmin == false) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PERMISSION);
                return true;
            }
            if(args.length < 2) {
                sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
                return true;
            }
            process(sender, args);
            return true;
        }
        if(args.length < 2) {
            sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
            return true;
        }
        process(sender, args);
        return true;
    }

    private void save() {
        try {
            plugin.getTctConfig().getConfig().save(plugin.getTctConfig().getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reload() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(plugin.getTctConfig().getConfigFile());

        final InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            plugin.getTctConfig().getConfig().load(plugin.getTctConfig().getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private void process(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        list.add("villagers");
        list.add("healers");
        list.add("detectives");
        list.add("wolves");
        list.add("fanatics");
        list.add("foxes");
        for(String str : list) {
            if(args[0].equalsIgnoreCase(str)) {
                try {
                    plugin.getTctConfig().getConfig().set("roles.count." + str, Integer.parseInt(args[1]));
                    save();
                    reload();
                    sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_SET_ROLE_COUNT_SUCCESS);
                    return;
                } catch(NumberFormatException e) {
                    sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_NUM_ONLY);
                    sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_SET_ROLE_COUNT_FAIL);
                    e.printStackTrace();
                    return;
                } catch (Exception e) {
                    sender.sendMessage(Reference.TCT_CHATPREFIX  + " " + Reference.TCT_CHAT_ERROR_COMMAND);
                    sender.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_SET_ROLE_COUNT_FAIL);
                    e.printStackTrace();
                    return;
                }
            }
        }
        sender.sendMessage(Reference.TCT_CHATPREFIX + " " + usage);
        return;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            List<String> result = new ArrayList<>();
            list.add("villagers");
            list.add("healers");
            list.add("detectives");
            list.add("wolves");
            list.add("fanatics");
            list.add("foxes");
            for(String str : list) {
                if(str.startsWith(args[0])){
                    result.add(str);
                }
            }
            return result;
        }
        return null;
    }
}
