package instagallery.app.com.gallery;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import instagallery.app.com.gallery.activity.AuthenticationDialog;
import instagallery.app.com.gallery.activity.AuthentificationActivity;
import instagallery.app.com.gallery.fragment.AuthenticationFragment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class AuthenticationTest {
    private AuthentificationActivity activity;
    private AuthenticationFragment fragment;

    Button login_button;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(AuthentificationActivity.class);
        fragment = new AuthenticationFragment().newInstance();
        SupportFragmentTestUtil.startVisibleFragment(fragment);

    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void checkFragmentNotNull() throws Exception {
        assertNotNull(fragment);
    }

    @Test
    public void buttonClickShouldStartNewActivity() throws Exception
    {
        Button button = (Button) fragment.getView().findViewById( R.id.login_button );
        button.performClick();
        Intent intent = Shadows.shadowOf(fragment.getActivity()).peekNextStartedActivity();
        assertEquals(AuthenticationDialog.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void validateTextViewContent() {
        View view = fragment.getView();
        assertNotNull(view);
        login_button=(Button)view.findViewById(R.id.login_button);
        assertNotNull(fragment.getView().getResources().getString(R.string.textview_notfound), login_button);
        assertEquals(fragment.getView().getResources().getString(R.string.access_auth),login_button.getText().toString());
    }
}