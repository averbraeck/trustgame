package org.transsonic.trustgame;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.sql.DataSource;

import org.transsonic.trustgame.data.trustgame.tables.records.GameRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameplayRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameuserRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.MissionRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrdercarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.RoundRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.SelectedcarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserorderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserroundRecord;

public class TrustGameData {

    /**
     * the SQL datasource representing the database's connection pool.<br>
     * the datasource is shared among the servlets and stored as a ServletContext attribute.
     */
    private DataSource dataSource;

    /**
     * the name of the user logged in to this session. <br>
     * if null, no user is logged in.<br>
     * filled by the UserLoginServlet.<br>
     * used by: server and in servlet.
     */
    private String username;

    /**
     * the id of the user logged in to this session.<br>
     * if null, no user is logged in.<br>
     * filled by the UserLoginServlet.<br>
     * used by: server.
     */
    private Integer userId;

    /**
     * the Users record for the logged in user.<br>
     * this record has the USERNAME to display on the screen.<br>
     * filled by the UserLoginServlet.<br>
     * used by: server and in servlet.<br>
     */
    private UserRecord user;

    /**
     * the id of the gameUser for a certain game to play.<br>
     * if null, there is no game to play.<br>
     * filled by the UserLoginServlet (if 1 game for this user) or by the SelectGameServlet (multiple games).<br>
     * used by: server.
     */
    private Integer gameUserId;

    /**
     * the Gameuser record of the user playing this session.<br>
     * this record maintains the SCORES.<br>
     * filled by the UserLoginServlet (if 1 game for this user) or by the SelectGameServlet (multiple games).<br>
     * used by: server and in servlet.
     */
    private GameuserRecord gameUser;

    /**
     * the id of the Gameplay for the currently playing user.<br>
     * if null, there is no game to play.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server.
     */
    private Integer gamePlayId;

    /**
     * the Gameplay record for the currently playing user.<br>
     * this record has the GROUP DESCRIPTION for display.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server and in servlet.
     */
    private GameplayRecord gamePlay;

    /**
     * the id of the static Game information of the currently played game.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server.
     */
    private Integer gameId;

    /**
     * the static Game information of the currently played game.<br>
     * this record has the GAME NAME for display.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server and in servlet.
     */
    private GameRecord game;

    /**
     * the static Mission record for the currently played game.<br>
     * this record contains the mission info to display for the current game.<br>
     * this record contains the target scores.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server and in servlet.
     */
    private MissionRecord mission;

    /**
     * the static Round records for the currently played game.<br>
     * Map from roundNumber to Round record; there should be one round record per round number.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server.
     */
    private SortedMap<Integer, RoundRecord> roundMapByRoundNr;

    /**
     * the static Round records for the currently played game.<br>
     * Map from roundId to Round record.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server.
     */
    private Map<Integer, RoundRecord> roundMapByRoundId;

    /**
     * the static Order records as a list PER round number.<br>
     * Maa from roundNumber to Order record; there is a list of orders per round. <br>
     * this has the order information in it.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server.
     */
    private SortedMap<Integer, List<OrderRecord>> orderMapByRoundNr;

    /**
     * the static ordercarrier records as a map from orderID to the carriers who can serve the order.<br>
     * Map from Order.ID to list of carriers serving that order.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * used by: server.
     */
    private Map<Integer, List<OrdercarrierRecord>> orderCarrierMapByOrderId;

    /**
     * the dynamic Userround records for the user playing the game.<br>
     * this has the played rounds in it, and can be used to retrieve the current round.<br>
     * Map from roundNumber to UserRound record; there should be one round record per round number.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * updated by RoundServlet.<br>
     * used by: server.
     */
    private SortedMap<Integer, UserroundRecord> userRoundMapByRoundNr;

    /**
     * the dynamic Userorder records as a list per round number<br>
     * this has the dynamic order information in it, especially whether the order has been published or not.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * updated by RoundServlet.<br>
     * used by: server.
     */
    private SortedMap<Integer, List<UserorderRecord>> userOrderMapByRoundNr;

    /**
     * the dynamic SelectedCarrier records as a map of UserOrderId to the SelectedCarrier. <br>
     * there should be zero or one SelectedCarrier per UserOrder.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * updated by RoundServlet.<br>
     * used by: server.
     */
    private Map<Integer, SelectedcarrierRecord> selectedCarrierMapByUserOrderId;

    /**
     * the right-side HTML content for the currently clicked button.<br>
     * filled by the SqlUtils.loadAttributes method.<br>
     * displayed by: servlet.
     */
    private String contentHtml = "";

    /**
     * The footer text. Contains the round number, or the indication of a practice round.<br>
     * filled by the application and updated based on the progression of the rounds.<br>
     * displayed by: servlet.
     */
    private String footerText;

    /**
     * The html selection code for multiple GameUser instances that can log in.
     */
    private String SelectUserHtml = "";
    
    /* ================================= */
    /* FULLY DYNAMIC INFO IN THE SESSION */
    /* ================================= */

    /**
     * the current round number. <br>
     * when round is larger that the highest roundNumber in the database, the game is over. <br>
     * filled by the SqlUtils.loadAttributes method. <br>
     * updated by RoundServlet.
     */
    private int roundNumber = 0;

    /**
     * which menu on the top left has been chosen, to maintain persistence after a POST. <br>
     * 0 = empty, 1 = mission, 2 = scores, 3 = orders. <br>
     * set to 0 by the SqlUtils.loadAttributes method. <br>
     * updated by RoundServlet.
     */
    private int menuChoice = 1;

    /**
     * which content is open on the right side of the screen, to maintain persistence after a POST. <br>
     * 0 = empty, 1 = order overview, 2 = carrier quotes, 3 = carrier overview. <br>
     * set to 0 by the SqlUtils.loadAttributes method. <br>
     * updated by RoundServlet.
     */
    private int contentChoice = 1;

    /**
     * which carrier details are open on the right hand side, to maintain persistence after a POST. <br>
     * 0 = empty, 1 = carrier overview, 2 = website, 3 = google, 4 = reviews, 5 = official report, 6 = buy report. <br>
     * the carrierId (database Id) is also stored. <br>
     * set to 0 by the SqlUtils.loadAttributes method. <br>
     * updated by RoundServlet.
     */
    private int carrierDetails = 0;
    private int carrierId = 0;

    /**
     * html code for the mission, scores or order.
     */
    private String orgScoresOrdersHtml = "";

    /**
     * Messages for the bottom left corner.
     */
    private String messagesHtml = "";

    /**
     * when 0, do not show popup; when 1: show popup. <br>
     * filled and updated by RoundServlet.
     */
    private int showModalWindow = 0;

    /**
     * client info (dynamic) for popup.
     */
    private String modalWindowHtml = "";

    /**
     * button for next day:active or inactive. Deafult: inactive.
     */
    public static final String dayButtonStartInactive = "<div class=\"tg-button-large tg-content-menu-button-inactive\">Start of day</div>";
    public static final String dayButtonFinishDay = "<div class=\"tg-button-large tg-content-menu-button-red\" onClick=\"clickFinishDay()\">Finish Day</div>";
    public static final String dayButtonFinishDayInactive = "<div class=\"tg-button-large tg-content-menu-button-inactive\">Finish Day</div>";
    public static final String dayButtonNextDay = "<div class=\"tg-button-large tg-content-menu-button-red\" onClick=\"clickNextDay()\">Go to next day</div>";
    public static final String dayButtonScoreOverview = "<div class=\"tg-button-large tg-content-menu-button-red\" onClick=\"clickFinalScores()\">Final Scores</div>";
    public static final String dayButtonScoreOverview4 = 
            "<div class=\"tg-button-large tg-content-menu-button-red\" onClick=\"clickFinalScores()\" style=\"color:yellow;\">Final Scores</div>";
    private String dayButton = dayButtonStartInactive;
    
    /** topMenuChoice = 1=Briefing, 2=Orders, 3=Carriers, 4=Final Scores. */ 
    private int topMenuChoice = 0;

    /* ******************* */
    /* GETTERS AND SETTERS */
    /* ******************* */

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserRecord getUser() {
        return this.user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public Integer getGameUserId() {
        return this.gameUserId;
    }

    public void setGameUserId(Integer gameUserId) {
        this.gameUserId = gameUserId;
    }

    public GameuserRecord getGameUser() {
        return this.gameUser;
    }

    public void setGameUser(GameuserRecord gameUser) {
        this.gameUser = gameUser;
    }

    public Integer getGamePlayId() {
        return this.gamePlayId;
    }

    public void setGamePlayId(Integer gamePlayId) {
        this.gamePlayId = gamePlayId;
    }

    public GameplayRecord getGamePlay() {
        return this.gamePlay;
    }

    public void setGamePlay(GameplayRecord gamePlay) {
        this.gamePlay = gamePlay;
    }

    public Integer getGameId() {
        return this.gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public GameRecord getGame() {
        return this.game;
    }

    public void setGame(GameRecord game) {
        this.game = game;
    }

    public MissionRecord getMission() {
        return this.mission;
    }

    public void setMission(MissionRecord mission) {
        this.mission = mission;
    }

    public SortedMap<Integer, RoundRecord> getRoundMapByRoundNr() {
        return this.roundMapByRoundNr;
    }

    public void setRoundMapByRoundNr(SortedMap<Integer, RoundRecord> roundMapByRoundNr) {
        this.roundMapByRoundNr = roundMapByRoundNr;
    }

    public Map<Integer, RoundRecord> getRoundMapByRoundId() {
        return this.roundMapByRoundId;
    }

    public void setRoundMapByRoundId(Map<Integer, RoundRecord> roundMapByRoundId) {
        this.roundMapByRoundId = roundMapByRoundId;
    }

    public SortedMap<Integer, List<OrderRecord>> getOrderMapByRoundNr() {
        return this.orderMapByRoundNr;
    }

    public void setOrderMapByRoundNr(SortedMap<Integer, List<OrderRecord>> orderMapByRoundNr) {
        this.orderMapByRoundNr = orderMapByRoundNr;
    }

    public Map<Integer, List<OrdercarrierRecord>> getOrderCarrierMapByOrderId() {
        return this.orderCarrierMapByOrderId;
    }

    public void setOrderCarrierMapByOrderId(Map<Integer, List<OrdercarrierRecord>> orderCarrierMapByOrderId) {
        this.orderCarrierMapByOrderId = orderCarrierMapByOrderId;
    }

    public SortedMap<Integer, UserroundRecord> getUserRoundMapByRoundNr() {
        return this.userRoundMapByRoundNr;
    }

    public void setUserRoundMapByRoundNr(SortedMap<Integer, UserroundRecord> userRoundMapByRoundNr) {
        this.userRoundMapByRoundNr = userRoundMapByRoundNr;
    }

    public SortedMap<Integer, List<UserorderRecord>> getUserOrderMapByRoundNr() {
        return this.userOrderMapByRoundNr;
    }

    public void setUserOrderMapByRoundNr(SortedMap<Integer, List<UserorderRecord>> userOrderMapByRoundNr) {
        this.userOrderMapByRoundNr = userOrderMapByRoundNr;
    }

    public Map<Integer, SelectedcarrierRecord> getSelectedCarrierMapByUserOrderId() {
        return this.selectedCarrierMapByUserOrderId;
    }

    public void setSelectedCarrierMapByUserOrderId(Map<Integer, SelectedcarrierRecord> selectedCarrierMapByUserOrderId) {
        this.selectedCarrierMapByUserOrderId = selectedCarrierMapByUserOrderId;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getShowModalWindow() {
        return this.showModalWindow;
    }

    public void setShowModalWindow(int showModalWindow) {
        this.showModalWindow = showModalWindow;
    }

    public int getMenuChoice() {
        return this.menuChoice;
    }

    public void setMenuChoice(int menuChoice) {
        this.menuChoice = menuChoice;
    }

    public String getLeftMenuButtons() {
        StringBuilder s = new StringBuilder();
        s.append("<div class=\"tg-icon\" onclick=\"clickMission()\"><img src=\"");
        if (getMenuChoice() == 1)
            s.append("images/person_selected.png");
        else
            s.append("images/person.png");
        s.append("\" width=\"48\" height=\"48\" /></div>\n");
        s.append("              <div class=\"tg-icon\" onclick=\"clickScores()\"><img src=\"");
        if (getMenuChoice() == 2)
            s.append("images/trophee_selected.png");
        else
            s.append("images/trophee.png");
        s.append("\" width=\"48\" height=\"48\" /></div>\n");
        s.append("              <div class=\"tg-icon\" onclick=\"clickOrders()\"><img src=\"");
        if (getMenuChoice() == 3)
            s.append("images/email_selected.png");
        else
            s.append("images/email.png");
        s.append("\" width=\"48\" height=\"48\" /></div>\n");
        return s.toString();
    }

    public int getContentChoice() {
        return this.contentChoice;
    }

    public void setContentChoice(int contentChoice) {
        this.contentChoice = contentChoice;
    }

    public String getModalWindowHtml() {
        return this.modalWindowHtml;
    }

    public void setModalWindowHtml(String modalClientWindowHtml) {
        this.modalWindowHtml = modalClientWindowHtml;
    }

    public String getContentHtml() {
        return this.contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public int getCarrierDetails() {
        return this.carrierDetails;
    }

    public void setCarrierDetails(int carrierDetails) {
        this.carrierDetails = carrierDetails;
    }

    public int getCarrierId() {
        return this.carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public String getOrgScoresOrdersHtml() {
        return this.orgScoresOrdersHtml;
    }

    public void setOrgScoresOrdersHtml(String orgScoresOrdersHtml) {
        this.orgScoresOrdersHtml = orgScoresOrdersHtml;
    }

    public String getMessagesHtml() {
        return this.messagesHtml;
    }

    public void setMessagesHtml(String messagesHtml) {
        this.messagesHtml = messagesHtml;
    }

    public String getTopMenuButtons() {
        StringBuilder s = new StringBuilder();
        s.append("<div class=\"tg-button-large tg-content-menu-button\" onclick=\"clickBriefing()\"");
        if (this.topMenuChoice == 1)
            s.append(" style=\" color:yellow;\"");
        s.append(">Briefing</div>\n");
        s.append("<div class=\"tg-button-large tg-content-menu-button\" onclick=\"clickPublishedOrders()\"");
        if (this.topMenuChoice == 2)
            s.append(" style=\" color:yellow;\"");
        s.append(">Order overview</div>\n");
        s.append("<div class=\"tg-button-large tg-content-menu-button\" onclick=\"clickCarrierOverview()\"");
        if (this.topMenuChoice == 3)
            s.append(" style=\" color:yellow;\"");
        s.append(">Carrier overview</div>\n");
        if (this.dayButton.equals(dayButtonScoreOverview) && this.topMenuChoice == 4)
            s.append(dayButtonScoreOverview4);
        else 
            s.append(this.dayButton);
        s.append("\n");
        return s.toString();
    }

    /** topMenuChoice = 1=Briefing, 2=Orders, 3=Carriers, 4=Final Scores. */ 
    public void setTopMenuChoice(int topMenuChoice) {
        this.topMenuChoice = topMenuChoice;
    }

    public void setDayButton(String dayButton) {
        this.dayButton = dayButton;
    }

    public String getFooterText() {
        return this.footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public String getSelectUserHtml() {
        return this.SelectUserHtml;
    }

    public void setSelectUserHtml(String selectUserHtml) {
        this.SelectUserHtml = selectUserHtml;
    }

}
