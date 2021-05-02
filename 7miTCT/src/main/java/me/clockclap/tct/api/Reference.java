package me.clockclap.tct.api;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public final class Reference {

    //プラグイン設定
    public static final String TCT_CHATPREFIX = ChatColor.DARK_RED + "[7mi TCT]" + ChatColor.RESET;
    public static final String TCT_SIDEBAR_TITLE = ChatColor.DARK_RED + "[Trouble in Crafter Town]";

    //イベント
    public static final List<String> TCT_BLOCKED = Arrays.asList("FURNACE", "CHEST", "BEACON", "DISPENSER",
            "DROPPER", "HOPPER", "WORKBENCH", "ENCHANTMENT_TABLE", "ENDER_CHEST", "ANVIL",
            "BED_BLOCK", "FENCE_GATE", "SPRUCE_FENCE_GATE", "BIRCH_FENCE_GATE", "ACACIA_FENCE_GATE",
            "JUNGLE_FENCE_GATE", "DARK_OAK_FENCE_GATE", "IRON_DOOR_BLOCK", "WOODEN_DOOR", "SPRUCE_DOOR",
            "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR", "WOOD_BUTTON", "STONE_BUTTON",
            "TRAP_DOOR", "IRON_TRAPDOOR", "DIODE_BLOCK_OFF", "DIODE_BLOCK_ON", "REDSTONE_COMPARATOR_OFF",
            "REDSTONE_COMPARATOR_ON", "FENCE", "SPRUCE_FENCE", "BIRCH_FENCE", "JUNGLE_FENCE", "DARK_OAK_FENCE",
            "ACACIA_FENCE", "NETHER_FENCE", "BREWING_STAND", "CAULDRON", "SIGN_POST", "WALL_SIGN", "SIGN");

    //カラーコード
    public static String colorChar() {
        return "\u00a7";
    }
    public static List<String> colorChars() {
        return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "k", "l", "m", "n", "o",
            "A", "B", "C", "D", "E", "F", "K", "L", "M", "N", "O"
        );
    }

    //ボスバー
    public static final String TCT_BOSSBAR_FORMAT_WAITING = ChatColor.RED + "待機中 - 運営がゲームを開始するまでお待ちください";
    public static final String TCT_BOSSBAR_FORMAT_GAMING = ChatColor.AQUA + "ゲーム中 - 残り%SECOND%秒";
    public static final String TCT_BOSSBAR_FORMAT_STARTING = ChatColor.GOLD + "準備時間 - 残り%SECOND%秒";
    public static final String TCT_BOSSBAR_FORMAT_ENDING = ChatColor.RED + "終了しています...";

    //チャットフォーマット
    public static final String JAPANIZE_FORMAT = ChatColor.RESET + "%JAPANIZE%" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "%MESSAGE%" + ChatColor.DARK_GRAY + ")";
    public static final String TCT_CHAT_FORMAT = ChatColor.RESET + "%ROLE% %PLAYER%: " + ChatColor.RESET + "%MESSAGE%";

    //ログブック
    public static final String TCT_LOGBOOK_GAME_STARTED = ChatColor.GOLD + " " + " " + " " + "｜ ゲーム開始 ｜";
    public static final String TCT_LOGBOOK_ROLES = ChatColor.DARK_GREEN + "=-=-登場役職-=-=";
    public static final String TCT_LOGBOOK_SEPARATOR = ChatColor.DARK_GREEN + "=-=-=-=-=-=-=-=";
    public static final String TCT_LOGBOOK_FOUND_DEADBODY = ChatColor.DARK_RED + "死亡確認 ↑";
    public static final String TCT_LOGBOOK_CO_VILLAGER = ChatColor.GREEN + "村人CO ↑";
    public static final String TCT_LOGBOOK_CO_HEALER = ChatColor.LIGHT_PURPLE + "医者CO ↑";
    public static final String TCT_LOGBOOK_CO_DETECTIVE = ChatColor.BLUE + "探偵CO ↑";
    public static final String TCT_LOGBOOK_CO_WOLF = ChatColor.RED + "人狼CO ↑";
    public static final String TCT_LOGBOOK_CO_FANATIC = ChatColor.DARK_PURPLE + "狂人CO ↑";
    public static final String TCT_LOGBOOK_CO_FOX = ChatColor.GOLD + "妖狐CO ↑";
    public static final String TCT_LOGBOOK_CO_IMMORAL = ChatColor.DARK_GRAY + "背徳者CO ↑";
    public static final String TCT_LOGBOOK_CONFIRM_DETECTIVE = ChatColor.BLUE + "探偵確定 ↑";

    //クイックチャット
    public static final String TCT_QUICK_CHAT_TITLE_0 = "○○さんと一緒にいます";
    public static final String TCT_QUICK_CHAT_TITLE_1 = "○○さんは村人だと思います";
    public static final String TCT_QUICK_CHAT_TITLE_2 = "○○さんは怪しいと思います";
    public static final String TCT_QUICK_CHAT_TITLE_3 = "○○さんは人狼です!!!";
    public static final String TCT_QUICK_CHAT_0 = "%PLAYER%さんと一緒にいます";
    public static final String TCT_QUICK_CHAT_1 = "%PLAYER%さんは村人だと思います";
    public static final String TCT_QUICK_CHAT_2 = "%PLAYER%さんは怪しいと思います";
    public static final String TCT_QUICK_CHAT_3 = "%PLAYER%さんは人狼です!!!";
    public static final String TCT_QUICK_CHAT_CURRENTLY_COOLDOWN = ChatColor.RED + "クールダウン中なので使用できません";

    //チャット全般
    public static final String TCT_CHAT_SEPARATOR = ChatColor.RED + "-------------------------------------";
    public static final String TCT_CHAT_SEPARATOR_I = ChatColor.AQUA + "-------------------------------------";
    //システム
    public static final String TCT_CHAT_ERROR_PERMISSION = ChatColor.RED + "このコマンドを実行するための権限がありません";
    public static final String TCT_CHAT_ERROR_GAME_NOT_STARTED = ChatColor.RED + "ゲームがまだ開始されていません";
    public static final String TCT_CHAT_ERROR_PLAYERS_NEEDED = ChatColor.RED + "人数が揃っていないため、ゲームを開始できません";
    public static final String TCT_CHAT_NEEDED_PLAYERS = ChatColor.LIGHT_PURPLE + "ゲームを開始するには最低%COUNT_A%人必要です (あと%COUNT_B%人)";
    public static final String TCT_CHAT_ERROR_NUM_ONLY = ChatColor.RED + "引数に入力する値が間違っています。引数には数値を入力してください。";
    public static final String TCT_CHAT_ERROR_COMMAND = ChatColor.RED + "エラーが発生しました。引数に入力した値が間違っていないか確認してください。";
    public static final String TCT_CHAT_PLAYER_ONLY = ChatColor.RED + "このコマンドはプレイヤーのみ実行できます";
    public static final String TCT_CHAT_PROCESS_CHANGE_GAMEMODE = ChatColor.GREEN + "%PLAYER%のゲームモードを%GAMEMODE%に変更しました。";
    public static final String TCT_CHAT_SYSTEM_STOPPED_GAME = ChatColor.RED + "ゲームが停止されました";
    public static final String TCT_CHAT_SYSTEM_EVERYONE = "全員";
    public static final String TCT_CHAT_SYSTEM_YOU = "あなた";
    public static final String TCT_CHAT_SYSTEM_RELOAD_COMPLETE = ChatColor.YELLOW + "コンフィグをリロードしました。";
    public static final String TCT_CHAT_SYSTEM_GAVE_BARRIER = ChatColor.YELLOW + "%PLAYER%はバリアブロックを入手しました。";
    public static final String TCT_CHAT_SYSTEM_GAVE_ITEM = ChatColor.YELLOW + "あなたは%ITEM%を入手しました。";
    public static final String TCT_CHAT_SYSTEM_SET_ROLE_COUNT_SUCCESS = ChatColor.GREEN + "役職数の変更に成功しました。";
    public static final String TCT_CHAT_ERROR_SET_ROLE_COUNT_FAIL = ChatColor.RED + "役職数の変更に失敗しました。";
    //ゲームモード
    public static final String TCT_GAMEMODE_CREATIVE = "クリエイティブ";
    public static final String TCT_GAMEMODE_SURVIVAL = "サバイバル";
    public static final String TCT_GAMEMODE_ADVENTURE = "アドベンチャー";
    public static final String TCT_GAMEMODE_SPECTATOR = "スペクテイター";
    //入退室
    public static final String TCT_CHAT_JOIN_MESSAGE_0 = ChatColor.LIGHT_PURPLE + "=-=-7mi TCT v%VERSION%-=-=";
    public static final String TCT_CHAT_JOIN_MESSAGE_1 = ChatColor.RED + " " + " " + " " + "by ClockClap";
    public static final String TCT_CHAT_JOIN_MESSAGE = ChatColor.GREEN + "[+] " + ChatColor.GRAY + "%PLAYER%";
    public static final String TCT_CHAT_QUIT_MESSAGE = ChatColor.RED + "[-] " + ChatColor.GRAY + "%PLAYER%";
    //ゲーム開始
    public static final String TCT_CHAT_GAME_STARTED = ChatColor.RED + "ゲームが開始されました";
    public static final String TCT_CHAT_GAME_ALREADY_STARTED = ChatColor.RED + "既にゲームが開始されているため、観戦モードになります";
    public static final String TCT_CHAT_GAME_READY_TIME = ChatColor.RED + "既にゲームが開始されています、準備時間終了時に自分の役職が決定します";
    public static final String TCT_CHAT_PLEASE_START = ChatColor.RED + "/startまたは/startlocでゲームを開始してください";
    public static final String TCT_CHAT_PLEASE_WAIT = ChatColor.RED + "運営がゲームを開始するまで、しばらくお待ちください";
    public static final String TCT_CHAT_READY_END = ChatColor.RED + "準備時間が終了しました...";
    //ゲームルール
    public static final String TCT_CHAT_GAME_PLAYERS = ChatColor.LIGHT_PURPLE + "今回のプレイ人数は" + ChatColor.GREEN + "%COUNT%人" + ChatColor.LIGHT_PURPLE + "です";
    public static final String TCT_CHAT_GAME_ROLE_SORTING = ChatColor.LIGHT_PURPLE + "役職振り分け";
    //キル
    public static final String TCT_CHAT_YOU_ARE_KILLED_BY = ChatColor.RED + "あなたは" + ChatColor.GOLD + "%PLAYER%" + ChatColor.RED + "に殺害されました";
    public static final String TCT_CHAT_YOU_ARE_SPECTATOR_MODE = ChatColor.GREEN + "死亡してしまったため、観戦モードになります";
    //死体
    public static final String TCT_CHAT_DEADBODY_FOUND = ChatColor.RED + "" + ChatColor.BOLD + "%PLAYER%さんが無惨な姿で発見されました";
    public static final String TCT_CHAT_ALREADY_FOUND = ChatColor.GREEN + "この死体は既に発見されています";
    public static final String TCT_CHAT_DEADBODY_KILLED_PLAYERS = ChatColor.RED + "殺害したプレイヤー";
    public static final String TCT_CHAT_DEADBODY_HAS_NOT_KILLED = ChatColor.RED + "この死体は誰も殺害していないようだ...";
    public static final String TCT_CHAT_DEADBODY_DAMAGED = ChatColor.RED + "" + ChatColor.BOLD + "この死体は焼け焦げているようだ...";
    public static final String TCT_CHAT_DEADBODY_DAMAGED_SUCCESS = ChatColor.RED + "死体を焼きました";
    public static final String TCT_CHAT_DEADBODY_HAS_NOT_BEEN_FOUND = ChatColor.RED + "" + ChatColor.BOLD + "この死体はまだ発見されていません";
    public static final String TCT_CHAT_DEADBODY_IS_NOT_WOLF = ChatColor.RED + "" + ChatColor.BOLD + "この死体は人狼ではありません";
    //その他
    public static final String TCT_CHAT_LANDMINE_PLACED = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "地雷を設置しました、%SECOND%秒後から効果が適応されます";
    public static final String TCT_CHAT_ISNT_WOLF = ChatColor.GREEN + "半径%DISTANCE%ブロック以内に人狼はいません";
    public static final String TCT_CHAT_IS_WOLF = ChatColor.RED + "半径%DISTANCE%ブロック以内に人狼がいます";
    public static final String TCT_CHAT_LANDMINE_REMOVED = ChatColor.LIGHT_PURPLE + "地雷を解体しました";
    public static final String TCT_CHAT_COIN_DISTRIBUTION = ChatColor.AQUA + "コインが配布されました /shopか/sでショップが開きます";
    public static final String TCT_CHAT_YOUR_COIN = ChatColor.AQUA + "あなたの所持コイン枚数は%COUNT%枚です";
    public static final String TCT_CHAT_NOT_ENOUGH_COINS = ChatColor.RED + "コインが足りません";
    public static final String TCT_CHAT_BOUGHT_ITEM = ChatColor.AQUA + "%ITEM%を購入しました";
    public static final String TCT_CHAT_ALREADY_BOUGHT = ChatColor.RED + "既に購入されています";
    public static final String TCT_CHAT_FOX_IS = ChatColor.LIGHT_PURPLE + "妖狐は" + ChatColor.GOLD + "[%PLAYER%]" + ChatColor.LIGHT_PURPLE + "です！";
    public static final String TCT_CHAT_FOX_ATTACK_ANYONE = ChatColor.RED + "あと%SECOND%秒以内に誰かにダメージを与えないと正体がばれてしまいます";
    public static final String TCT_CHAT_HEAL_STATION_USED = ChatColor.LIGHT_PURPLE + "回復ステーションを使用しました";
    public static final String TCT_CHAT_CANNOT_USE = ChatColor.RED + "このコマンドを使えない役職です";
    public static final String TCT_CHAT_CANCELLED_EXPLOSION = ChatColor.LIGHT_PURPLE + "爆発ダメージを無効化しました";
    public static final String TCT_CHAT_DIAMOND_HELMET = ChatColor.RED + "探偵の証明はゲームが開始されてから%SECOND%秒後に購入できます";
    public static final String TCT_CHAT_EXPLODE_IN = ChatColor.GREEN + "3秒後に自爆します";
    public static final String TCT_CHAT_CANNOT_ATTACK_WOLF = ChatColor.RED + "仲間の人狼には攻撃できません。";
    public static final String TCT_CHAT_CANNOT_ATTACK_FOX = ChatColor.GOLD + "仲間の妖狐には攻撃できません。";
    //勝敗
    public static final String TCT_CHAT_VILLAGERS_VICTORY_FOR_TIMEOUT = ChatColor.GREEN + "時間切れにより、村人側の勝利です";
    public static final String TCT_CHAT_VILLAGERS_VICTORY = ChatColor.GREEN + "人狼陣営は全滅し、妖狐も死亡しました";
    public static final String TCT_CHAT_WOLVES_VICTORY = ChatColor.RED + "村人陣営は全滅し、妖狐も死亡しました";
    public static final String TCT_CHAT_FOX_VICTORY_A = ChatColor.GOLD + "人狼陣営は全滅し、妖狐が生き残りました";
    public static final String TCT_CHAT_FOX_VICTORY_B = ChatColor.GOLD + "村人陣営は全滅し、妖狐が生き残りました";
    public static final String TCT_CHAT_NO_VICTORY = ChatColor.GRAY + "運営によって強制終了されたため、引き分けになります";
    public static final String TCT_CHAT_VICTORY = "%COLOR%%TEAM%が勝利しました";
    //ゲーム終了
    public static final String TCT_CHAT_GAMEEND_ROLE_RESULT = ChatColor.GREEN + "=-=-プレイヤーの役職結果-=-=";
    public static final String TCT_CHAT_GAMEEND_YOU_ARE_KILLED_BY = ChatColor.RED + "あなたは" + ChatColor.GOLD + "%PLAYER%" + ChatColor.RED + "に殺害されました " + ChatColor.GREEN + "(%ROLE%)";

    //チャット (役職と説明)
    public static final String TCT_CHAT_ROLE_YOU_ARE_HEALER = ChatColor.LIGHT_PURPLE + "あなたの役職は医者です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_HEALER = ChatColor.LIGHT_PURPLE + "配られた鉄剣でプレイヤーを殴るとHPを回復させることができます";
    public static final String TCT_CHAT_ROLE_YOU_ARE_FANATIC = ChatColor.RED + "あなたの役職は狂人です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_FANATIC = ChatColor.LIGHT_PURPLE + "村人の邪魔をしましょう、ただし人狼はあなたの事を知っていますがあなたは誰が人狼か分かりません";
    public static final String TCT_CHAT_ROLE_YOU_ARE_WOLF = ChatColor.RED + "あなたの役職は人狼です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_WOLF = ChatColor.LIGHT_PURPLE + "人狼アイテムを的確に購入し、村人にばれないように殲滅をしていきましょう";
    public static final String TCT_CHAT_ROLE_YOU_ARE_VILLAGER = ChatColor.GREEN + "あなたの役職は村人です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_VILLAGER = ChatColor.LIGHT_PURPLE + "紛れ込んだ人狼を見つけ出し倒しましょう、自分が人狼だと思われないように行動しましょう";
    public static final String TCT_CHAT_ROLE_YOU_ARE_DETECTIVE = ChatColor.AQUA + "あなたの役職は探偵です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_DETECTIVE = ChatColor.LIGHT_PURPLE + "探偵アイテムを上手に使って村人を勝利に導きましょう";
    public static final String TCT_CHAT_ROLE_YOU_ARE_FOX = ChatColor.GOLD + "あなたの役職は妖狐です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_FOX = ChatColor.LIGHT_PURPLE + "一定時間毎に誰かにダメージを与えないと、正体がばれてしまいます";
    public static final String TCT_CHAT_ROLE_YOU_ARE_IMMORAL = ChatColor.DARK_GRAY + "あなたの役職は背徳者です";
    public static final String TCT_CHAT_ROLE_DESCRIPTION_IMMORAL = ChatColor.LIGHT_PURPLE + "妖狐の補助をしましょう、間違えて妖狐を倒さないようにしましょう";
    public static final String TCT_CHAT_ROLE_YOU_ARE = "%COLOR%あなたの役職は%ROLE%です";

    //全般
    public static final String TCT_SECOND = "秒";
    public static final String TCT_NAME = "名前";
    public static final String TCT_HP = "HP";
    public static final String TCT_TOGETHER = "一緒";
    public static final String TCT_VILLAGER = "村人";
    public static final String TCT_SUS = "怪しい";
    public static final String TCT_WOLF = "人狼";
    public static final String TCT_ROLE = "役職";
    public static final String TCT_WOLF_LIST = ChatColor.RED + "人狼一覧";
    public static final String TCT_FANATIC_LIST = ChatColor.GOLD + "狂人一覧";
    public static final String TCT_FOX_LIST = ChatColor.GOLD + "妖狐一覧";
    public static final String TCT_IMMORAL_LIST = ChatColor.DARK_GRAY + "背徳者一覧";
    public static final String TCT_TIME_AFTER_DEATH = "死後経過時間";
    public static final String TCT_CAUSE_OF_DEATH = "死因";

    //死因
    public static final String TCT_DEATHCAUSE_AIR = "不明";
    public static final String TCT_DEATHCAUSE_KILL = "殺害";
    public static final String TCT_DEATHCAUSE_TNT = "爆発";
    public static final String TCT_DEATHCAUSE_FALL = "落下";
    public static final String TCT_DEATHCAUSE_LOST_CONNECTION = "退室";

    //タイトル (UI)
    public static final String TCT_TITLE_MAIN_VILLAGERS_VICTORY = ChatColor.GREEN + "村人陣営が勝利しました";
    public static final String TCT_TITLE_SUB_VILLAGERS_VICTORY_FOR_TIMEOUT = ChatColor.GRAY + "時間切れによる村人側の勝利";
    public static final String TCT_TITLE_SUB_VILLAGERS_VICTORY = ChatColor.GRAY + "人狼陣営全滅による村人側の勝利";
    public static final String TCT_TITLE_MAIN_WOLVES_VICTORY = ChatColor.RED + "人狼陣営が勝利しました";
    public static final String TCT_TITLE_SUB_WOLVES_VICTORY = ChatColor.GRAY + "村人陣営全滅による人狼側の勝利";
    public static final String TCT_TITLE_MAIN_FOX_VICTORY = ChatColor.GOLD + "妖狐陣営が勝利しました";
    public static final String TCT_TITLE_SUB_FOX_VICTORY_A = ChatColor.GRAY + "人狼陣営全滅時に生き残っていた妖狐陣営の勝利";
    public static final String TCT_TITLE_SUB_FOX_VICTORY_B = ChatColor.GRAY + "村人陣営全滅時に生き残っていた妖狐陣営の勝利";
    public static final String TCT_TITLE_MAIN_NO_VICTORY = ChatColor.DARK_GRAY + "引き分け";
    public static final String TCT_TITLE_SUB_NO_VICTORY = ChatColor.GRAY + "ゲームが強制終了されました";
    public static final String TCT_TITLE_MAIN_VICTORY = "%COLOR%%TEAM%が勝利しました";
    public static final String TCT_TITLE_SUB_VICTORY = "";

    //インベントリメニュー (GUI)
    public static final String TCT_GUI_TITLE_GENERAL_SHOP = "村人ショップ";
    public static final String TCT_GUI_TITLE_DETECTIVE_SHOP = "探偵ショップ";
    public static final String TCT_GUI_TITLE_WOLF_SHOP = "人狼ショップ";

    //役職 CO
    public static final String TCT_CHAT_CO = ChatColor.AQUA + "%PLAYER%が%CO%" + ChatColor.AQUA + "をしました";
    public static final String TCT_CONFIRM_DETECTIVE = ChatColor.BLUE + "探偵の証明";
    public static final String TCT_CHAT_DETECTIVE = ChatColor.AQUA + "%PLAYER%がダイヤのヘルメットを買って" + TCT_CONFIRM_DETECTIVE + ChatColor.AQUA + "を行いました";
    public static final String TCT_CHAT_ROLE_SPEC_P = ChatColor.GREEN + "[観戦チャット]";
    public static final String TCT_CHAT_ROLE_CO_NONE_P = ChatColor.RESET + "[CO無し]";
    public static final String TCT_CHAT_ROLE_CO_VILLAGER_P = ChatColor.GREEN + "[村人CO]";
    public static final String TCT_CHAT_ROLE_CO_DETECTIVE_P = ChatColor.BLUE + "[探偵CO]";
    public static final String TCT_CHAT_ROLE_CO_HEALER_P = ChatColor.LIGHT_PURPLE + "[医者CO]";
    public static final String TCT_CHAT_ROLE_CO_FANATIC_P = ChatColor.DARK_PURPLE + "[狂人CO]";
    public static final String TCT_CHAT_ROLE_CO_WOLF_P = ChatColor.RED + "[人狼CO]";
    public static final String TCT_CHAT_ROLE_CO_FOX_P = ChatColor.GOLD + "[妖狐CO]";
    public static final String TCT_CHAT_ROLE_CO_IMMORAL_P = ChatColor.DARK_GRAY + "[背徳者CO]";
    public static final String TCT_CHAT_ROLE_DETECTIVE = ChatColor.BLUE + "[探偵確定]";

    public static final String TCT_CHAT_ROLE_CO_VILLAGER = ChatColor.GREEN + "村人CO";
    public static final String TCT_CHAT_ROLE_CO_DETECTIVE = ChatColor.BLUE + "探偵CO";
    public static final String TCT_CHAT_ROLE_CO_HEALER = ChatColor.LIGHT_PURPLE + "医者CO";
    public static final String TCT_CHAT_ROLE_CO_FANATIC = ChatColor.DARK_PURPLE + "狂人CO";
    public static final String TCT_CHAT_ROLE_CO_WOLF = ChatColor.RED + "人狼CO";
    public static final String TCT_CHAT_ROLE_CO_FOX = ChatColor.GOLD + "妖狐CO";
    public static final String TCT_CHAT_ROLE_CO_IMMORAL = ChatColor.DARK_GRAY + "背徳者CO";
    //(おまけ)
    public static final String TCT_CHAT_ROLE_CO_MEDIUM_P = ChatColor.DARK_PURPLE + "[霊媒師CO]";
    public static final String TCT_CHAT_ROLE_CO_HUNTER_P = ChatColor.DARK_GREEN + "[狩人CO]";

    public static final String TCT_CHAT_ROLE_CO_MEDIUM = ChatColor.DARK_PURPLE + "霊媒師CO";
    public static final String TCT_CHAT_ROLE_CO_HUNTER = ChatColor.DARK_GREEN + "狩人CO";

    //役職名
    public static final String TCT_ROLE_SPEC = "観戦モード";
    public static final String TCT_ROLE_VILLAGER = "村人";
    public static final String TCT_ROLE_DETECTIVE = "探偵";
    public static final String TCT_ROLE_HEALER = "医者";
    public static final String TCT_ROLE_FANATIC = "狂人";
    public static final String TCT_ROLE_WOLF = "人狼";
    public static final String TCT_ROLE_FOX = "妖狐";
    public static final String TCT_ROLE_IMMORAL = "背徳者";
    //(おまけ)
    public static final String TCT_ROLE_MEDIUM = "霊媒師";
    public static final String TCT_ROLE_HUNTER = "狩人";

    //陣営名
    public static final String TCT_TEAM_SPEC = "スペクテイター";
    public static final String TCT_TEAM_VILLAGERS = "村人陣営";
    public static final String TCT_TEAM_WOLVES = "人狼陣営";
    public static final String TCT_TEAM_FOXES = "妖狐陣営";

    //サイドバー (UI)
    public static final String TCT_UI_GAME_STATUS = ChatColor.GREEN + "ステータス";
    public static final String TCT_UI_REMAINING_TIME = ChatColor.AQUA + "推定残り時間";
    public static final String TCT_UI_REMAINING_PLAYER = ChatColor.AQUA + "残りの生存者";
    public static final String TCT_UI_REAL_REMAINING_TIME = ChatColor.GOLD + "残り時間実数";
    public static final String TCT_UI_REAL_REMAINING_PLAYER = ChatColor.GOLD + "残りの生存者実数";
    public static final String TCT_UI_YOUR_ROLE = ChatColor.RED + "あなたの役職";
    public static final String TCT_UI_COIN_COUNT = ChatColor.AQUA + "所持コイン枚数";
    public static final String TCT_UI_SEPARATOR = ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + " " + " " + " " + " " + " " + " " + " " + " " + " " + " "
            + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " ";
    public static final String TCT_UI_PLEASE_WAIT = ChatColor.GREEN + "ゲーム開始までお待ちください";
    public static final String TCT_UI_GAME_STARTING_IN = ChatColor.GREEN + "ゲーム開始まで... %SECOND%秒";

    //ゲームステータス
    public static final String TCT_STATE_PLAYING = "ゲーム実行中";
    public static final String TCT_STATE_END_PROCESSING = "終了処理中";
    public static final String TCT_STATE_WAITING = "待機中";
    public static final String TCT_STATE_READY = "準備時間";
    public static final String TCT_GAME_STATE = ChatColor.GRAY + "GameState >>> %STATE%";
    public static final String TCT_CHAT_STATE_PLAYING = "Gaming";
    public static final String TCT_CHAT_STATE_WAITING = "Waiting";
    public static final String TCT_CHAT_STATE_PREGAMING = "PreGaming";

}
