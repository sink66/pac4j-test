package controllers;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.protectedIndex;

import javax.inject.Inject;
import java.util.List;

public class LoginController extends Controller {

	@Inject
	protected PlaySessionStore playSessionStore;

	private List<CommonProfile> getProfiles() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager<>(context);
		return profileManager.getAll(true);
	}

	@Secure(clients = "TwitterClient")
	public Result twitterLogin() {
		return ok(protectedIndex.render(getProfiles()));
	}

}
