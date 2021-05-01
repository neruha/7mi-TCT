package me.clockclap.tct.item.items.quickchat;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class QuickChatC implements CustomSpecialItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private boolean attackable;
    private boolean quickchat;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public QuickChatC() {
        this.index = ItemIndex.DEFAULT_ITEM_SLOT_7;
        this.isdefault = true;
        this.material = Material.IRON_HOE;
        this.name = "QUICKCHAT_C";
        this.displayName = Reference.TCT_QUICK_CHAT_TITLE_2;
        this.title = Reference.TCT_QUICK_CHAT_TITLE_2;
        this.description = ChatColor.GREEN + "Quick Chat";
        this.role = GameRoles.NONE;
        this.attackable = false;
        this.quickchat = true;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + title);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        this.item = item;
    }

    @Override
    public boolean isQuickChatItem() {
        return this.quickchat;
    }

    @Override
    public void onAttackPlayerWithCooldown(Player source, Player target) {
        PlayerData data = NanamiTct.plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(target.getName()));
        data.setSuspicious(data.getSuspicious() + 1);
        source.chat(Reference.TCT_QUICK_CHAT_2.replaceAll("%PLAYER%", target.getDisplayName()));
    }

    @Override
    public int getIndex() {
        return this.index;
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
    public boolean isAttackable() {
        return this.attackable;
    }

    @Override
    public void setAttackable(boolean value) {
        this.attackable = value;
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
