package controllers;

import models.entity.User;
import models.service.user.UserService;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.play.store.PlaySessionStore;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.protectedIndex;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class LoginController extends Controller {

	@Inject
	UserService userService;

	@Inject
	protected PlayCacheStore playCacheStore;

	@Inject
	protected PlaySessionStore playSessionStore;

	private List<CommonProfile> getProfiles() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager<>(context);
		return profileManager.getAll(true);
	}

	private void storeUserId(String sessionId, String userId) {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		playCacheStore.set(context, sessionId, userId);
	}

	@Secure(clients = "TwitterClient")
	public Result twitterLogin() {
		Optional<User> userOps = userService.createOrLogin(getProfiles().get(0));
		if (!userOps.isPresent()) {
			return badRequest("FAILURE");
		}
		storeUserId(session(org.pac4j.core.context.Pac4jConstants.SESSION_ID), String.valueOf(userOps.get().id));
		return ok(protectedIndex.render(getProfiles()));
	}

}
