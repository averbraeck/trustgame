<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TrustGame Login</title>

<style>
html, body {
  font-family: Arial, Helvetica, sans-serif;
}

.tg-login-page {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  position: absolute;
  top: 20px;
  bottom: 20px;
  left: 20px;
  right: 20px;
  min-height: 0px;
}

.tg-header, .tg-body, .tg-footer {
  flex-shrink: 0;
}

.tg-login-header {
  background: navy;
  padding: 5px;
  height: 50px;
  text-align: left;
  line-height: 50px;
  color: white;
  font-weight: bold;
  font-size: 2em;
  border-radius: 10px;
  margin-bottom: 20px;
}

.tg-login-header-right {
  position: absolute;
  background: white;
  margin: 2px;
  height: 46px;
  width: 300px;
  right: 20px;
  border-radius: 20px;
  flex-direction: row;
}

.tg-login-header-right > img {
  margin-left: 20px;
  margin-top: 3px;
  height: 40px;
  width: auto;
}

.tg-login-body {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  min-height: 0;
  height: calc(100vh - 200px);
  width: 600px;
  margin-left: calc(50vw - 300px);
  padding-right: 20px;
  overflow-y: auto;
  overflow-x: hidden;
}

.tg-login-footer {
  background: navy;
  padding: 5px;
  height: 50px;
  line-height: 50px;
  color: white;
  font-weight: bold;
  font-size: 1em;
  text-align: right;
  border-radius: 10px;
  margin-top: 20px;
}

.tg-login-top-message {
  font-style: normal;
  font-size: 1em;
  margin-bottom: 20px;
}

.tg-login-top-message > h1 {
  font-style: normal;
  font-weight: bold;
  font-size: 1.8em;
  text-align: center;
  color: orange;
}

.tg-login-top-message > p {
  text-align: justify;
  line-height: 1.2;
}

.tg-login-bottom-message {
  font-style: normal;
  font-size: 0.8em;
  margin-top: 20px;
}

.tg-login-bottom-message > p {
  text-align: justify;
  line-height: 1.2;
}

.tg-login {
  width: 595px;
  border: 3px solid orange;
  border-radius: 10px;
  padding-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.tg-login-button {
  display: block;
  width: 595px;
  border: none;
  background-color: orange;
  padding: 14px 28px;
  cursor: pointer;
  text-align: center;
  font-size: 1.3em;
  font-weight: bold;
}

</style>
</head>
<body>
  <div class="tg-login-page">
    <div class="tg-login-header">
      <div class="tg-login-header-right">
        <img src="images/nwo.png" />
        <img src="images/tudelft.png" />
        <span style="font-size: 12px; padding-left: 20px; position:relative; top:-4px; color:black;">v2.4.3</span>
      </div>
    </div>
  
    <div class="tg-login-body">
    
      <div class="tg-login-top-message">
        <h1>Welcome to the FreightBooking Game</h1> 
        <p>
          In this serious game, you are a representative of a shipping company. 
          Through a platform, you work together with different carriers to transport 
          your goods to the hinterland. Based on the different customer orders, 
          you have to find a suitable carrier to transport the goods. However, does the 
          carrier meet your expectations? And does the carrier not take advantage of the situation?
        </p> 
        <p>
          In 7 rounds, you as a player receive different orders from different 
          types of customers. But, first, you have to make sure that your company fulfils its goal: 
          transporting goods cheaply and sustainably that meet the customer's requirements.
        </p>
      </div>

      <div class="tg-login">
        <form action="/trustgame/login" method="post">
         <table>
           <tr>
             <td width="60px">&nbsp;</td>
             <td>User name &nbsp; </td>
             <td><input type="text" name="username" /></td>
           </tr>
           <tr>
             <td width="150px">&nbsp;</td>
             <td>Password &nbsp; </td>
             <td><input type="password" name="password" /></td>
           </tr>
           <tr>
             <td width="150px">&nbsp;</td>
             <td colspan="2"><br><i>Or use your 5-character code:</i></td>
           </tr>
           <tr>
             <td width="150px">&nbsp;</td>
             <td>Login code&nbsp; </td>
             <td><input type="text" name="usercode" /></td>
           </tr>
         </table>
         <br/>
         <span>
           <input type="submit" value="START THE GAME" class="tg-login-button" />
         </span>
        </form>
      </div>
  
      <div class="tg-login-bottom-message">
        <p>This game is part of the research project Trans-SONIC 
          (<a href="https://transsonic.nl" target="_blank">https://transsonic.nl</a>) in which we 
          aim to explore the influence of trust on technology-mediated collaborations. 
          You can stop the game session at any time you wish. We will treat all data collected anonymously and safely 
          and use them for research purposes only.
        </p>
        <p> 
          The game has been tested with the latest versions of <b>Chrome</b>, <b>Firefox</b> and <b>Edge</b>. 
          The game works best on a HD screen with a resolution of 1920 x 1080 pixels, and has <b>not</b> been
          designed for a mobile phone.
        </p>
        <p> 
          If you have any questions about the game or the research, feel free to contact 
          Anique Kuijpers at TU Delft (<a href="mailto:a.g.j.kuijpers@tudelft.nl">a.g.j.kuijpers@tudelft.nl</a>).
        </p>
      </div>
  
    </div>
    
    <div class="tg-login-footer">
      <!-- logo's at top right -->
    </div>
    
  </div>      
</body>
</html>