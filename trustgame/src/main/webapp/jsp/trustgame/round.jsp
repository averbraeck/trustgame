<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

  <head>
    <meta charset="ISO-8859-1">
    <title>TrustGame</title>
    <link rel="stylesheet" type="text/css" href="/trustgame/css/trustgame.css" />
    <script src="/trustgame/js/round.js"></script>
  </head>
  
  <body onload="initPage()">
    <div class="tg-page">
      <div class="tg-header">
        <span class="tg-freightbooking">FreightBooking.com</span>
        <span class="tg-slogan">Do you want to transport goods?</span>
      </div>
      <div class="tg-header-right">
        <img src="images/nwo.png" />
        <img src="images/tudelft.png" />
        <p><a href="/trustgame">LOGOUT</a></p>
      </div>
      <div class="tg-header-game-user">
        <p>Game: ${trustGameData.getGame().getName()}</p>
        <p>Group: ${trustGameData.getGamePlay().getGroupdescription()}</p>
        <p>User:&nbsp;&nbsp;&nbsp; ${trustGameData.getUser().getName()}</p>
      </div>

      <div class="tg-body">
      
        <!-- left side of the screen with the company / score / order box and the message box -->
      
        <div class="tg-col1">
          <div class="tg-menu">
          
            <!-- menu with icons -->
          
            <div class="tg-icon-col-menu">
              <div class="tg-icon" onclick="clickOrganization()"><img src="images/person.png" width="48" height="48" /></div>
              <div class="tg-icon" onclick="clickScores()"><img src="images/trophee.png" width="48" height="48" /></div>
              <div class="tg-icon" onclick="clickOrders()"><img src="images/email.png" width="48" height="48" /></div>
            </div>

            <!-- organization, scores, unconfirmed orders -->

            <div class="tg-org-scores-orders">
              ${trustGameData.getOrgScoresOrdersHtml()}
            </div>
          </div>
             
          <div class="tg-bar"></div>
          
          <!-- message box -->
          
          <div class="tg-message-box">
            <div class="tg-icon-col-message">
              <div class="tg-icon" onclick="clickMessage()"><img src="images/message.png" width="48" height="48" /></div>
            </div>
            <div class="tg-message-col2" id="tg-message-col2">
              ${trustGameData.getMessagesHtml()}
            </div>
          </div>
        </div>
        
        <!-- right side of the screen with the order overview, client quotes and carrier overview -->
        
        <div class="tg-col2">
          <div class="tg-content-menu">
            <div class="tg-button-large tg-content-menu-button" onclick="clickPublishedOrders()">Order overview</div>
            <div class="tg-button-large tg-content-menu-button" onclick="clickCarrierOverview()">Carrier overview</div>
            ${trustGameData.getDayButton()}
          </div>
          <div class="tg-bar"></div>
          <div class="tg-content" id="tg-content">${trustGameData.getContentHtml()}</div>
        </div>
      </div>
      
      <div class="tg-footer">
        <table><tr>
          <td width="60px"><div class="tg-help-icon-left"><img src="images/support2.png" onclick="clickHelp()" /></div></td>
          <td class="tg-help-col">
            <p><b>Help for the steps to take in each round</b></p>
            <p>STEP 1. Publish transport orders (left menu) and preview carrier quotes</p>
            <p>STEP 2. Check out the carriers' reputation and accept quotes</p>
          </td>
          <td class="tg-help-col">
            <p>STEP 3. Finish the day to see the transport outcomes</p>
            <p>STEP 4. Score the carriers' performance, based on the outcomes</p>
            <p>STEP 5. Go to the next day to receive new orders in the left menu</p>
          </td>
        </tr></table>
        <div class="tg-round">${trustGameData.getFooterText()}</div>
      </div>
      
    </div> <!-- tg-page -->
    
    <!-- modal window for the client information within an order -->
    
    ${trustGameData.getModalWindowHtml()}

    <form id="clickForm" action="/trustgame/round" method="POST" style="display:none;">
      <input id="click" type="hidden" name="click" value="tobefilled" />
      <input id="clickedOrderId" type="hidden" name="clickedOrderId" value="0" />
      <input id="clickedCarrierId" type="hidden" name="clickedCarrierId" value="0" />
      <input id="carrierDetailChoice" type="hidden" name="carrierDetailChoice" value="0" />
      <input id="clickedOrderCarrierId" type="hidden" name="clickedOrderCarrierId" value="0" />
      <input id="clickedNumberStars" type="hidden" name="clickedNumberStars" value="0" />
    </form>

  </body>
</html>