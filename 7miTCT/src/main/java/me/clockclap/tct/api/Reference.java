package me.clockclap.tct.api;

import org.bukkit.ChatColor;

public class Reference {

    //プラグイン設定
    public static final String TCT_CHATPREFIX = ChatColor.DARK_RED + "[7mi TCT]" + ChatColor.RESET;
    public static final String TCT_SIDEBAR_TITLE = ChatColor.DARK_RED + "[Trouble in Crafter Town]";


    //チャットフォーマット
    public static final String JAPANIZE_FORMAT = ChatColor.RESET + "%JAPANIZE%" + ChatColor.RESET + " " + ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "%MESSAGE%" + ChatColor.DARK_GRAY + ")";
    public static final String TCT_CHAT_FORMAT = ChatColor.RESET + "%ROLE% %PLAYER%: " + ChatColor.RESET + "%MESSAGE%";

    //クイックチャット
    public static final String TCT_QUICK_CHAT_TITLE_0 = "○○さんと一緒にいます";
    public static final String TCT_QUICK_CHAT_TITLE_1 = "○○さんは村人だと思います";
    public static final String TCT_QUICK_CHAT_TITLE_2 = "○○さんは怪しいと思います";
    public static final String TCT_QUICK_CHAT_TITLE_3 = "○○さんは人狼です!!!";
    public static final String TCT_QUICK_CHAT_0 = "%PLAYER%さんと一緒にいます";
    public static final String TCT_QUICK_CHAT_1 = "%PLAYER%さんは村人だと思います";
    public static final String TCT_QUICK_CHAT_2 = "%PLAYER%さんは怪しいと思います";
    public static final String TCT_QUICK_CHAT_3 = "%PLAYER%さんは人狼です!!!";

    //チャット全般
    public static final String TCT_CHAT_SEPARATOR = ChatColor.RED + "-------------------------------------";
    public static final String TCT_CHAT_PLEASE_WAIT = ChatColor.RED + "運営がゲームを開始するまで、しばらくお待ちください";
    //入退室
    public static final String TCT_CHAT_JOIN_MESSAGE_0 = ChatColor.LIGHT_PURPLE + "=-=-7mi TCT-=-=";
    public static final String TCT_CHAT_JOIN_MESSAGE_1 = ChatColor.RED + " " + " " + " " + "by ClockClap";
    public static final String TCT_CHAT_JOIN_MESSAGE = ChatColor.GREEN + "[+] " + ChatColor.GRAY + "%PLAYER%";
    public static final String TCT_CHAT_QUIT_MESSAGE = ChatColor.RED + "[-] " + ChatColor.GRAY + "%PLAYER%";
    //ゲーム開始
    public static final String TCT_CHAT_GAME_STARTED = ChatColor.RED + "ゲームが開始されました";
    public static final String TCT_CHAT_GAME_ALREADY_STARTED = ChatColor.RED + "既にゲームが開始されているため、観戦モードになります";
    public static final String TCT_CHAT_PLEASE_START = ChatColor.RED + "ゲームを開始するには、/start または /startloc と入力します";
    public static final String TCT_CHAT_READY_END = ChatColor.RED + "準備時間が終了しました...";
    //ゲームルール
    public static final String TCT_CHAT_GAME_PLAYERS = ChatColor.LIGHT_PURPLE + "今回のプレイ人数は" + ChatColor.GREEN + "%COUNT%人" + ChatColor.LIGHT_PURPLE + "です";
    public static final String TCT_CHAT_GAME_ROLE_SORTING = ChatColor.LIGHT_PURPLE + "役職振り分け";
    //キル
    public static final String TCT_CHAT_YOU_ARE_KILLED_BY = ChatColor.RED + "あなたは" + ChatColor.GOLD + "%PLAYER%" + ChatColor.RED + "に殺害されました";
    public static final String TCT_CHAT_AUTO_RESPAWING = ChatColor.RESET + "自動リスポーンしています...";
    public static final String TCT_CHAT_YOU_ARE_SPECTATOR_MODE = ChatColor.GREEN + "死亡してしまったため、観戦モードになります";
    //発見
    public static final String TCT_CHAT_CADAVER_FOUND = ChatColor.RED + "" + ChatColor.BOLD + "%PLAYER%さんが無惨な姿で発見されました";
    public static final String TCT_CHAT_ALREADY_FOUND = ChatColor.GREEN + "この死体は既に発見されています";
    //その他
    public static final String TCT_CHAT_LANDMINE_PLACED = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "地雷を設置しました、5秒後から効果が適応されます";
    public static final String TCT_CHAT_COIN_DISTRIBUTION = ChatColor.AQUA + "コインが配布されました /shopか/sでショップが開きます";
    public static final String TCT_CHAT_FOX_IS = ChatColor.LIGHT_PURPLE + "妖狐は" + ChatColor.GOLD + "[%PLAYER%]" + ChatColor.LIGHT_PURPLE + "です！";
    //勝敗
    public static final String TCT_CHAT_VILLAGERS_VICTORY_FOR_TIMEOUT = ChatColor.GREEN + "時間切れにより、村人側の勝利です";
    public static final String TCT_CHAT_WOLVES_VICTORY = ChatColor.RED + "村人陣営は全滅し、妖狐も死亡しました";
    public static final String TCT_CHAT_FOX_VICTORY = ChatColor.GOLD + "人狼陣営は全滅し、妖狐が生き残りました";
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

    //全般
    public static final String TCT_SECOND = "秒";
    public static final String TCT_NAME = "名前";
    public static final String TCT_ROLE = "役職";
    public static final String TCT_WOLF_LIST = ChatColor.RED + "人狼一覧";
    public static final String TCT_FANATIC_LIST = ChatColor.GOLD + "狂人一覧";
    public static final String TCT_TIME_AFTER_DEATH = "死後経過時間";
    public static final String TCT_CAUSE_OF_DEATH = "死因";

    //タイトル (UI)
    public static final String TCT_TITLE_MAIN_VILLAGERS_VICTORY = ChatColor.GREEN + "村人陣営が勝利しました";
    public static final String TCT_TITLE_SUB_VILLAGERS_VICTORY_FOR_TIMEOUT = ChatColor.GRAY + "時間切れによる村人側の勝利";
    public static final String TCT_TITLE_MAIN_WOLVES_VICTORY = ChatColor.RED + "人狼陣営が勝利しました";
    public static final String TCT_TITLE_SUB_WOLVES_VICTORY = ChatColor.GRAY + "村人陣営全滅による人狼側の勝利";
    public static final String TCT_TITLE_MAIN_FOX_VICTORY = ChatColor.GOLD + "妖狐が勝利しました";
    public static final String TCT_TITLE_SUB_FOX_VICTORY = ChatColor.GREEN + "人狼陣営全滅時に生き残っていた妖狐の勝利";

    //役職 CO
    public static final String TCT_CHAT_CO = ChatColor.AQUA + "%PLAYER%が%CO%をしました";
    public static final String TCT_CHAT_ROLE_SPEC_P = ChatColor.GREEN + "[観戦チャット]";
    public static final String TCT_CHAT_ROLE_CO_NONE_P = ChatColor.RESET + "[CO無し]";
    public static final String TCT_CHAT_ROLE_CO_VILLAGER_P = ChatColor.GREEN + "[村人CO]";
    public static final String TCT_CHAT_ROLE_CO_DETECTIVE_P = ChatColor.BLUE + "[探偵CO]";
    public static final String TCT_CHAT_ROLE_CO_HEALER_P = ChatColor.LIGHT_PURPLE + "[医者CO]";
    public static final String TCT_CHAT_ROLE_CO_FANATIC_P = ChatColor.DARK_PURPLE + "[狂人CO]";
    public static final String TCT_CHAT_ROLE_CO_WOLF_P = ChatColor.RED + "[人狼CO]";
    public static final String TCT_CHAT_ROLE_CO_FOX_P = ChatColor.GOLD + "[妖狐CO]";

    public static final String TCT_CHAT_ROLE_CO_VILLAGER = ChatColor.GREEN + "村人CO";
    public static final String TCT_CHAT_ROLE_CO_DETECTIVE = ChatColor.BLUE + "探偵CO";
    public static final String TCT_CHAT_ROLE_CO_HEALER = ChatColor.LIGHT_PURPLE + "医者CO";
    public static final String TCT_CHAT_ROLE_CO_FANATIC = ChatColor.DARK_PURPLE + "狂人CO";
    public static final String TCT_CHAT_ROLE_CO_WOLF = ChatColor.RED + "人狼CO";
    public static final String TCT_CHAT_ROLE_CO_FOX = ChatColor.GOLD + "妖狐CO";
    //(おまけ)
    public static final String TCT_CHAT_ROLE_CO_IMMORALIST_P = ChatColor.GRAY + "[背徳者CO]";
    public static final String TCT_CHAT_ROLE_CO_MEDIUM_P = ChatColor.DARK_GRAY + "[霊媒師CO]";
    public static final String TCT_CHAT_ROLE_CO_HUNTER_P = ChatColor.DARK_GREEN + "[狩人CO]";

    public static final String TCT_CHAT_ROLE_CO_IMMORALIST = ChatColor.GRAY + "背徳者CO";
    public static final String TCT_CHAT_ROLE_CO_MEDIUM = ChatColor.DARK_GRAY + "霊媒師CO";
    public static final String TCT_CHAT_ROLE_CO_HUNTER = ChatColor.DARK_GREEN + "狩人CO";

    //役職名
    public static final String TCT_ROLE_SPEC = "観戦モード";
    public static final String TCT_ROLE_VILLAGER = "村人";
    public static final String TCT_ROLE_DETECTIVE = "探偵";
    public static final String TCT_ROLE_HEALER = "医者";
    public static final String TCT_ROLE_FANATIC = "狂人";
    public static final String TCT_ROLE_WOLF = "人狼";
    public static final String TCT_ROLE_FOX = "妖狐";
    //(おまけ)
    public static final String TCT_ROLE_IMMORALIST = "背徳者";
    public static final String TCT_ROLE_MEDIUM = "霊媒師";
    public static final String TCT_ROLE_HUNTER = "狩人";

    //サイドバー (UI)
    public static final String TCT_UI_GAME_STATUS = "ステータス";
    public static final String TCT_UI_REMAINING_TIME = "推定残り時間";
    public static final String TCT_UI_REMAINING_PLAYER = "残りの生存者";
    public static final String TCT_UI_REAL_REMAINING_TIME = "残り時間実数";
    public static final String TCT_UI_REAL_REMAINING_PLAYER = "残りの生存者実数";
    public static final String TCT_UI_YOUR_ROLE = "あなたの役職";
    public static final String TCT_UI_COIN_COUNT = "所持コイン枚数";
    public static final String TCT_UI_SEPARATOR = ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + " " + " " + " " + " " + " " + " " + " " + " " + " " + " "
            + " " + " " + " " + " " + " " + " " + " " + " " + " " + " ";
    public static final String TCT_UI_PLEASE_WAIT = "ゲーム開始までお待ちください";
    public static final String TCT_UI_GAME_STARTING_IN = "ゲーム開始まで... %SECOND%秒";

    //ゲームステータス
    public static final String TCT_STATE_PLAYING = "ゲーム実行中";
    public static final String TCT_STATE_END_PROCESSING = "終了処理中";
    public static final String TCT_STATE_WAITING = "待機中";
    public static final String TCT_STATE_READY = "準備時間";
    public static final String TCT_GAME_STATE = ChatColor.GRAY + "Game State >>> %STATE%";
    public static final String TCT_CHAT_STATE_PLAYING = "Gaming";
    public static final String TCT_CHAT_STATE_WAITING = "Waiting";

}
