package me.clockclap.tct.item;

import me.clockclap.tct.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TctLog {

    private Game game;
    private List<String> log = new ArrayList<>();
    private List<List<String>> bookTexts = new ArrayList<>();
    private ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

    public TctLog(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public List<String> getLog() {
        return this.log;
    }

    public void initialize() {
        this.log = new ArrayList<>();
        this.bookTexts = new ArrayList<>();
        this.book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) this.book.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + CustomItems.logItem.getDisplayName());
        List<String> lore = new ArrayList<>();
        lore.add(CustomItems.logItem.getDescription());
        meta.setLore(lore);
        meta.setTitle(CustomItems.logItem.getTitle());
        meta.addPage("");
        this.book.setItemMeta(meta);
        for(Player p : Bukkit.getOnlinePlayers()) {
            for(int i = 0; i < p.getInventory().getSize(); i++) {
                ItemStack i_ = p.getInventory().getItem(i);
                if(i_ == null) {
                    continue;
                }
                if(i_.hasItemMeta()) {
                    if(i_.getType() == CustomItems.logItem.getItemStack().getType() &&
                            i_.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.logItem.getItemStack().getItemMeta().getDisplayName())) {
                        p.getInventory().setItem(i, this.book);
                    }
                }
            }
        }
    }

    public void addLine(String line) {
        List<String> list = log;
        Collections.reverse(list);
        list.add(ChatColor.DARK_GREEN + "" + (list.size() + 1) + ": " + ChatColor.BLACK + line);
        Collections.reverse(list);
        log = list;
    }

    public void splitLog() {
        int size = 12;
        List<List<String>> list = new ArrayList<>();
        if(log.size() > 0) {
            for (int i = 0; i < log.size(); i += size) {
                list.add(new ArrayList<>(log.subList(i, Math.min(i + size, log.size()))));
            }
        }
        bookTexts = list;
    }

    public void updateLogBook() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + CustomItems.logItem.getDisplayName());
        List<String> lore = new ArrayList<>();
        lore.add(CustomItems.logItem.getDescription());
        meta.setLore(lore);
        meta.setTitle(ChatColor.WHITE + CustomItems.logItem.getTitle());
        if(this.bookTexts.size() > 0) {
            for (List<String> list : this.bookTexts) {
                String str = String.join("\n", list);
                meta.addPage(str);
            }
        } else {
            meta.addPage();
        }
        item.setItemMeta(meta);
        this.book = item;

        // Replace item
        for(Player p : Bukkit.getOnlinePlayers()) {
            for(int i = 0; i < p.getInventory().getSize(); i++) {
                ItemStack i_ = p.getInventory().getItem(i);
                if(i_ == null) {
                    continue;
                }
                if(i_.hasItemMeta()) {
                    if(i_.getType() == CustomItems.logItem.getItemStack().getType() &&
                            i_.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.logItem.getItemStack().getItemMeta().getDisplayName())) {
                        p.getInventory().setItem(i, item);
                    }
                }
            }
        }
    }

    public void update() {
        splitLog();
        updateLogBook();
    }

    public ItemStack getItem() {
        return this.book;
    }

}
