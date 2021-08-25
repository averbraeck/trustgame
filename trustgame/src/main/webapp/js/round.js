/* load the page with the right information (all passed in the request) */
function initPage() {

  /* logged in? */
  var rn = String("${trustGameData}");
  if (rn.length == 0 || rn == "null") {
    window.location = "/trustgame/login";
  }
}

/* click to get organization info */
function clickOrganization() {
  document.getElementById("click").setAttribute("value", "organization");
  document.getElementById("clickForm").submit();
}

/* click to get score info */
function clickScores() {
  document.getElementById("click").setAttribute("value", "scores");
  document.getElementById("clickForm").submit();
}

/* click to get order info */
function clickOrders() {
  document.getElementById("click").setAttribute("value", "orders");
  document.getElementById("clickForm").submit();
}

function clickMessage() {
  document.getElementById("click").setAttribute("value", "message");
  document.getElementById("clickForm").submit();
}

/* click to get client info for a displayed order */
function clickOpenClientInfo(orderId) {
  document.getElementById("click").setAttribute("value", "openClientInfo");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickForm").submit();
}

/* click to close client form */
function clickCloseClientInfo() {
  document.getElementById("click").setAttribute("value", "closeClientInfo");
  document.getElementById("clickForm").submit();
}

/* click to show published orders */
function clickPublishedOrders() {
  document.getElementById("click").setAttribute("value", "publishedOrders");
  document.getElementById("clickForm").submit();
}

/* click to show carriers */
function clickCarrierOverview() {
  document.getElementById("click").setAttribute("value", "carrierOverview");
  document.getElementById("clickForm").submit();
}

/* click to publish the 1st displayed order */
function clickPublishOrder(orderId) {
  document.getElementById("click").setAttribute("value", "publishOrder");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickForm").submit();
}

/* click to get carrier info for a clicked carrier */
function clickCarrierDetails(carrierId, choice) {
  document.getElementById("click").setAttribute("value", "carrierDetails");
  document.getElementById("clickedCarrierId").setAttribute("value", String(carrierId));
  document.getElementById("carrierDetailChoice").setAttribute("value", String(choice));
  document.getElementById("clickForm").submit();
}

/* click to accept a quote */
function clickAcceptQuote(orderId, orderCarrierId) {
  document.getElementById("click").setAttribute("value", "acceptQuote");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickedOrderCarrierId").setAttribute("value", String(orderCarrierId));
  document.getElementById("clickForm").submit();
}

/* click to confirm acceptance of a quote */
function clickAcceptQuoteYes(orderId, orderCarrierId) {
  document.getElementById("click").setAttribute("value", "acceptQuoteYes");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickedOrderCarrierId").setAttribute("value", String(orderCarrierId));
  document.getElementById("clickForm").submit();
}

/* click to deny acceptance of a quote */
function clickAcceptQuoteNo() {
  document.getElementById("click").setAttribute("value", "acceptQuoteNo");
  document.getElementById("clickForm").submit();
}

/* click to finish the day */
function clickFinishDay() {
  document.getElementById("click").setAttribute("value", "finishDay");
  document.getElementById("clickForm").submit();
}

/* click to go to the next day */
function clickNextDay() {
  document.getElementById("click").setAttribute("value", "nextDay");
  document.getElementById("clickForm").submit();
}

/* click to close an 'OK' modal window */
function clickModalWindowOk() {
  document.getElementById("click").setAttribute("value", "modalWindowOk");
  document.getElementById("clickForm").submit();
}

/* click to view the transport outcome for a transported order */
function clickViewTransportOutcome(orderId) {
  document.getElementById("click").setAttribute("value", "viewTransportOutcome");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickForm").submit();
}

/* click on 1 to 5 stars */
function clickGiveStars(orderId, numberStars) {
  document.getElementById("click").setAttribute("value", "giveStars");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickedNumberStars").setAttribute("value", String(6 - numberStars));
  document.getElementById("clickForm").submit();
}

/* click to confirm the review with 1 to 5 stars */
function clickConfirmStars() {
  document.getElementById("click").setAttribute("value", "confirmStars");
  document.getElementById("clickForm").submit();
}

/* click to change the review choice for 1 to 5 stars */
function clickChangeReview(orderId) {
  document.getElementById("click").setAttribute("value", "changeReview");
  document.getElementById("clickedOrderId").setAttribute("value", String(orderId));
  document.getElementById("clickForm").submit();
}

/* click to go to the debriefing screen */
function clickDebrief() {
  document.getElementById("click").setAttribute("value", "debrief");
  document.getElementById("clickForm").submit();
}

/* click to go to the final scores screen */
function clickFinalScores() {
  document.getElementById("click").setAttribute("value", "finalScores");
  document.getElementById("clickForm").submit();
}

/* click to get help / briefing screen */
function clickHelp() {
  document.getElementById("click").setAttribute("value", "briefing");
  document.getElementById("clickForm").submit();
}


/* Make the modal wiindow div element draggable: */
function dragElement(elmnt) {
  var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
  if (document.getElementById(elmnt.id + "header")) {
    // if present, the header is where you move the DIV from:
    document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
  } else {
    // otherwise, move the DIV from anywhere inside the DIV:
    elmnt.onmousedown = dragMouseDown;
  }

  function dragMouseDown(e) {
    e = e || window.event;
    e.preventDefault();
    // get the mouse cursor position at startup:
    pos3 = e.clientX;
    pos4 = e.clientY;
    document.onmouseup = closeDragElement;
    // call a function whenever the cursor moves:
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e = e || window.event;
    e.preventDefault();
    // calculate the new cursor position:
    pos1 = pos3 - e.clientX;
    pos2 = pos4 - e.clientY;
    pos3 = e.clientX;
    pos4 = e.clientY;
    // set the element's new position:
    elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
    elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
  }

  function closeDragElement() {
    // stop moving when mouse button is released:
    document.onmouseup = null;
    document.onmousemove = null;
  }
}
