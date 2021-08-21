/*
 * This file is generated by jOOQ.
 */
package org.transsonic.trustgame.data.trustgame;


import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.transsonic.trustgame.data.trustgame.tables.Briefing;
import org.transsonic.trustgame.data.trustgame.tables.Carrier;
import org.transsonic.trustgame.data.trustgame.tables.Carrierreview;
import org.transsonic.trustgame.data.trustgame.tables.Client;
import org.transsonic.trustgame.data.trustgame.tables.Fbreport;
import org.transsonic.trustgame.data.trustgame.tables.Game;
import org.transsonic.trustgame.data.trustgame.tables.Gameplay;
import org.transsonic.trustgame.data.trustgame.tables.Gameuser;
import org.transsonic.trustgame.data.trustgame.tables.Order;
import org.transsonic.trustgame.data.trustgame.tables.Ordercarrier;
import org.transsonic.trustgame.data.trustgame.tables.Playerorganization;
import org.transsonic.trustgame.data.trustgame.tables.Review;
import org.transsonic.trustgame.data.trustgame.tables.Round;
import org.transsonic.trustgame.data.trustgame.tables.Selectedcarrier;
import org.transsonic.trustgame.data.trustgame.tables.User;
import org.transsonic.trustgame.data.trustgame.tables.Usercarrier;
import org.transsonic.trustgame.data.trustgame.tables.Userclick;
import org.transsonic.trustgame.data.trustgame.tables.Usergroup;
import org.transsonic.trustgame.data.trustgame.tables.Userorder;
import org.transsonic.trustgame.data.trustgame.tables.Userround;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Trustgame extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>trustgame</code>
     */
    public static final Trustgame TRUSTGAME = new Trustgame();

    /**
     * The table <code>trustgame.briefing</code>.
     */
    public final Briefing BRIEFING = Briefing.BRIEFING;

    /**
     * The table <code>trustgame.carrier</code>.
     */
    public final Carrier CARRIER = Carrier.CARRIER;

    /**
     * The table <code>trustgame.carrierreview</code>.
     */
    public final Carrierreview CARRIERREVIEW = Carrierreview.CARRIERREVIEW;

    /**
     * The table <code>trustgame.client</code>.
     */
    public final Client CLIENT = Client.CLIENT;

    /**
     * The table <code>trustgame.fbreport</code>.
     */
    public final Fbreport FBREPORT = Fbreport.FBREPORT;

    /**
     * The table <code>trustgame.game</code>.
     */
    public final Game GAME = Game.GAME;

    /**
     * The table <code>trustgame.gameplay</code>.
     */
    public final Gameplay GAMEPLAY = Gameplay.GAMEPLAY;

    /**
     * The table <code>trustgame.gameuser</code>.
     */
    public final Gameuser GAMEUSER = Gameuser.GAMEUSER;

    /**
     * The table <code>trustgame.order</code>.
     */
    public final Order ORDER = Order.ORDER;

    /**
     * The table <code>trustgame.ordercarrier</code>.
     */
    public final Ordercarrier ORDERCARRIER = Ordercarrier.ORDERCARRIER;

    /**
     * The table <code>trustgame.playerorganization</code>.
     */
    public final Playerorganization PLAYERORGANIZATION = Playerorganization.PLAYERORGANIZATION;

    /**
     * The table <code>trustgame.review</code>.
     */
    public final Review REVIEW = Review.REVIEW;

    /**
     * The table <code>trustgame.round</code>.
     */
    public final Round ROUND = Round.ROUND;

    /**
     * The table <code>trustgame.selectedcarrier</code>.
     */
    public final Selectedcarrier SELECTEDCARRIER = Selectedcarrier.SELECTEDCARRIER;

    /**
     * The table <code>trustgame.user</code>.
     */
    public final User USER = User.USER;

    /**
     * The table <code>trustgame.usercarrier</code>.
     */
    public final Usercarrier USERCARRIER = Usercarrier.USERCARRIER;

    /**
     * The table <code>trustgame.userclick</code>.
     */
    public final Userclick USERCLICK = Userclick.USERCLICK;

    /**
     * The table <code>trustgame.usergroup</code>.
     */
    public final Usergroup USERGROUP = Usergroup.USERGROUP;

    /**
     * The table <code>trustgame.userorder</code>.
     */
    public final Userorder USERORDER = Userorder.USERORDER;

    /**
     * The table <code>trustgame.userround</code>.
     */
    public final Userround USERROUND = Userround.USERROUND;

    /**
     * No further instances allowed
     */
    private Trustgame() {
        super("trustgame", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Briefing.BRIEFING,
            Carrier.CARRIER,
            Carrierreview.CARRIERREVIEW,
            Client.CLIENT,
            Fbreport.FBREPORT,
            Game.GAME,
            Gameplay.GAMEPLAY,
            Gameuser.GAMEUSER,
            Order.ORDER,
            Ordercarrier.ORDERCARRIER,
            Playerorganization.PLAYERORGANIZATION,
            Review.REVIEW,
            Round.ROUND,
            Selectedcarrier.SELECTEDCARRIER,
            User.USER,
            Usercarrier.USERCARRIER,
            Userclick.USERCLICK,
            Usergroup.USERGROUP,
            Userorder.USERORDER,
            Userround.USERROUND);
    }
}
