package corelin.plugins.library.plugin.command.type;

public enum CommandType {

    /**
     * 这事控制台
     */
    Sender,
    /**
     * 玩家
     */
    Player;

    private CommandType() {
    }

    public static String getName(CommandType commandType) {
        switch(commandType) {
            case Player:
                return "玩家";
            default:
                return "控制台";
        }
    }
}
