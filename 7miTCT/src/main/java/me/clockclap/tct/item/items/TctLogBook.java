package me.clockclap.tct.item.items;

import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TctLogBook implements CustomItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private BookMeta book;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public TctLogBook() {
        this.index = 2;
        this.isdefault = true;
        this.material = Material.WRITTEN_BOOK;
        this.name = "LOG_BOOK";
        this.displayName = "Tct Logs";
        this.title = "Tct Logs";
        this.description = ChatColor.AQUA + "TCT Item";
        this.role = GameRoles.VILLAGER;
        ItemStack item = new ItemStack(material);
        BookMeta meta = (BookMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        meta.setTitle(ChatColor.WHITE + title);
        this.book = meta;
        item.setItemMeta(book);
        this.item = item;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    public BookMeta getBook() {
        return this.book;
    }

    public void setBook(BookMeta book) {
        this.book = book;
    }

    @Override
    public ItemStack getItemStack() {
        return this.item;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public boolean isDefault() {
        return this.isdefault;
    }

    @Override
    public void setItemStack(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
