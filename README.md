Instagram Test made by Franck Guillaouic

The authentication progress start inside AuthenticationFragment.class.

 - AuthenticationDialog is launch when user click login button.
   When the code is received, the POSTing process start to get access token.
  - The function Login_RequestAccessToken() inside InstaInteractorImpl.class, launched inside AuthenticationFragment.class,  will request access token.

  - The function:
     getAccessToken(login_url.getCLIENT_ID(),
                  login_url.getCLIENT_SECRET(),
                  login_url.getGRANT_TYPE(),login_url.getREDIRECT_URI(),
                  code);

  To be able to test the authentication, the CLIENT_ID, SECRET CLIENT AND REDIRECT_URI need be changed by yours.
  Theses strings are in strings.xml:

   <string name="auth_client_id">Your_client_id</string> <!-- Change value to test -->
      <string name="auth_client_secret">Your_secret_id</string> <!-- Change value to test -->
      <string name="auth_redirect_uri">https://guillalab.wixsite.com/lifesafe</string> <!-- Change value to your test -->