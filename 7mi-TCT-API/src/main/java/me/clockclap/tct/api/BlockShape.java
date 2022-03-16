package me.clockclap.tct.api;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public final class BlockShape {

    public static boolean isSlab(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch(material) {
            case STEP:
            case WOOD_STEP:
            case STONE_SLAB2:
            case PURPUR_SLAB:
                return true;
            default:
                return false;
        }
    }

    public static boolean isStairs(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch(material) {
            case SANDSTONE_STAIRS:
            case SMOOTH_STAIRS:
            case ACACIA_STAIRS:
            case SPRUCE_WOOD_STAIRS:
            case BRICK_STAIRS:
            case COBBLESTONE_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case DARK_OAK_STAIRS:
            case PURPUR_STAIRS:
            case QUARTZ_STAIRS:
            case WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case NETHER_BRICK_STAIRS:
            case RED_SANDSTONE_STAIRS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTransparent(Material material) {
        if(material == null || !material.isBlock()) return false;
        return material != Material.SKULL && (material.isTransparent() ||
                material == Material.SIGN_POST || material == Material.WALL_SIGN ||
                material == Material.WALL_BANNER || material == Material.STANDING_BANNER);
    }

    public static boolean isFence(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case FENCE:
            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case IRON_FENCE:
            case DARK_OAK_FENCE:
            case JUNGLE_FENCE:
            case NETHER_FENCE:
            case STAINED_GLASS_PANE:
            case END_ROD:
            case THIN_GLASS:
            case SPRUCE_FENCE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isFenceGate(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch(material) {
            case FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isDoor(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case DARK_OAK_DOOR:
            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case IRON_DOOR_BLOCK:
            case JUNGLE_DOOR:
            case SPRUCE_DOOR:
            case WOOD_DOOR:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTrapdoor(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case IRON_TRAPDOOR:
            case TRAP_DOOR:
                return true;
            default:
                return false;
        }
    }

    public static boolean isGlass(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case GLASS:
            case THIN_GLASS:
            case STAINED_GLASS:
            case STAINED_GLASS_PANE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTransparentBlock(Material material) {
        if(material == null || !material.isBlock()) return false;
        if(material.isTransparent() || isGlass(material) || material == Material.IRON_FENCE) return true;
        switch (material) {
            case BARRIER:
            case FLOWER_POT:
            case SIGN_POST:
            case WALL_SIGN:
            case WALL_BANNER:
            case STANDING_BANNER:
                return true;
            default:
                return false;
        }
    }

    public static boolean isInBlock(Block block, double x, double y, double z) {
        boolean result = true;
        if(block != null && block.getType() != null) {
            Material type = block.getType();
            int xa = block.getLocation().getBlockX();
            int ya = block.getLocation().getBlockY();
            int za = block.getLocation().getBlockZ();

            double xb = x - xa;
            double yb = y - ya;
            double zb = z - za;

            if(!isTransparent(type)) {
                if (isSlab(type)) {
                    if (block.getData() >= 8 && yb >= 0.5) {
                        result = false;
                    } else if (block.getData() < 8 && yb <= 0.5) {
                        result = false;
                    }
                } else if (isStairs(type)) {
                    if (block.getData() == 0 || block.getData() == 8) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(xb >= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 1 || block.getData() == 9) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(xb <= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 2 || block.getData() == 10) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(zb >= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 3 || block.getData() == 11) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(zb <= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 4 || block.getData() == 12) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(xb >= 0.5) {
                            result = false;
                        }
                    } else if ((block.getData() == 5 || block.getData() == 13)) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(xb <= 0.5) {
                            result = false;
                        }
                    } else if ((block.getData() == 6 || block.getData() == 14)) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(zb >= 0.5) {
                            result = false;
                        }
                    } else if ((block.getData() == 7 || block.getData() == 15)) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(zb <= 0.5) {
                            result = false;
                        }
                    }
                } else if(isDoor(type)) {
                    World w = block.getLocation().getWorld();
                    Block bottom = w.getBlockAt(xa, ya - 1, za);
                    Block b = block;
                    if(bottom != null && bottom.getType() != null && BlockShape.isDoor(bottom.getType())) b = bottom;
                    if((b.getData() == 0 || b.getData() == 7) && xb <= 0.1875) {
                        result = false;
                    } else if((b.getData() == 1 || b.getData() == 4) && zb <= 0.1875) {
                        result = false;
                    } else if((b.getData() == 2 || b.getData() == 5) && xb >= 0.8125) {
                        result = false;
                    } else if((b.getData() == 3 || b.getData() == 6) && zb >= 0.8125) {
                        result = false;
                    }
                } else if(isFence(type)) {
                    World w = block.getLocation().getWorld();
                    Block x0 = w.getBlockAt(xa + 1, ya, za);
                    Block x1 = w.getBlockAt(xa - 1, ya, za);
                    Block z0 = w.getBlockAt(xa, ya, za + 1);
                    Block z1 = w.getBlockAt(xa, ya, za - 1);
                    boolean b = true;
                    if(x0 != null && x0.getType() == block.getType()) {
                        if(xb >= 0.375 && zb >= 0.375 && zb <= 0.625) {
                            b = false;
                        }
                    }
                    if(x1 != null && x1.getType() == block.getType()) {
                        if(xb <= 0.625 && zb >= 0.375 && zb <= 0.625) {
                            b = false;
                        }
                    }
                    if(z0 != null && z0.getType() == block.getType()) {
                        if(zb >= 0.375 && xb >= 0.375 && xb <= 0.625) {
                            b = false;
                        }
                    }
                    if(z1 != null && z1.getType() == block.getType()) {
                        if(zb <= 0.625 && xb >= 0.375 && xb <= 0.625) {
                            b = false;
                        }
                    }
                    result = b;
                } else if(type == Material.COBBLE_WALL) {
                    World w = block.getLocation().getWorld();
                    Block x0 = w.getBlockAt(xa + 1, ya, za);
                    Block x1 = w.getBlockAt(xa - 1, ya, za);
                    Block z0 = w.getBlockAt(xa, ya, za + 1);
                    Block z1 = w.getBlockAt(xa, ya, za - 1);
                    boolean b = true;
                    if(x0 != null && x0.getType() == block.getType()) {
                        if(xb >= 0.3125 && zb >= 0.3125 && zb <= 0.6875) {
                            b = false;
                        }
                    }
                    if(x1 != null && x1.getType() == block.getType()) {
                        if(xb <= 0.6875 && zb >= 0.3125 && zb <= 0.6875) {
                            b = false;
                        }
                    }
                    if(z0 != null && z0.getType() == block.getType()) {
                        if(zb >= 0.3125 && xb >= 0.3125 && xb <= 0.6875) {
                            b = false;
                        }
                    }
                    if(z1 != null && z1.getType() == block.getType()) {
                        if(zb <= 0.6875 && xb >= 0.3125 && xb <= 0.6875) {
                            b = false;
                        }
                    }
                    result = b;
                } else if(isFenceGate(type)) {
                    if((block.getData() == 0 || block.getData() == 2 || block.getData() == 8 || block.getData() == 10) && zb >= 0.375 && zb <= 0.625) {
                        result = false;
                    } else if((block.getData() == 1 || block.getData() == 3 || block.getData() == 9 || block.getData() == 11) && xb >= 0.375 && xb <= 0.625) {
                        result = false;
                    }
                } else if(isTrapdoor(type)) {
                    if((block.getData() >= 0 && block.getData() <= 3) && yb <= 0.1875) {
                        result = false;
                    } else if((block.getData() == 4 || block.getData() == 12) && zb >= 0.8125) {
                        result = false;
                    } else if((block.getData() == 5 || block.getData() == 13) && zb <= 0.1875) {
                        result = false;
                    } else if((block.getData() == 6 || block.getData() == 14) && xb >= 0.8125) {
                        result = false;
                    } else if((block.getData() == 7 || block.getData() == 15) && xb <= 0.1825) {
                        result = false;
                    } else if((block.getData() >= 8 && block.getData() <= 11) && yb >= 0.8125) {
                        result = false;
                    }
                } else if(type == Material.END_ROD) {
                    if(xb >= 0.375 && xb <= 0.625 && zb >= 0.375 && zb <= 0.625) {
                        result = false;
                    }
                } else if(type == Material.ENCHANTMENT_TABLE || type == Material.ENDER_PORTAL_FRAME) {
                    if(yb <= 0.8125) result = false;
                } else {
                    result = false;
                }
            }
        }
        return !result;
    }
}