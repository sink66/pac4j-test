package modules;

import com.google.inject.AbstractModule;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.play.ApplicationLogoutController;
import org.pac4j.play.CallbackController;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.play.store.PlaySessionStore;

import play.Configuration;
import play.Environment;

public class SecurityModule extends AbstractModule {

	private final Configuration configuration;

	public SecurityModule(final Environment environment, final Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure() {
		bind(PlaySessionStore.class).to(PlayCacheStore.class);

		// TwitterのAPI KeyとAPI Secretを設定
		TwitterClient twitterClient = new TwitterClient("twID", "twSecret");

		// コールバックURLとクライアントを設定、今回はtwitterのみ
		Clients clients = new Clients("http://localhost:9000/callback", twitterClient);

		Config config = new Config(clients);

		// Authorizer：認証ロジック　複数のAuthorizerをadd可能
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));

		// ActionAdapterの設定
		config.setHttpActionAdapter(new DemoHttpActionAdapter());

		bind(Config.class).toInstance(config);

		// callback
		CallbackController callbackController = new CallbackController();
		callbackController.setDefaultUrl("/");
		bind(CallbackController.class).toInstance(callbackController);

		// logout 正常にログアウトしたら/へ遷移
		ApplicationLogoutController logoutController = new ApplicationLogoutController();
		logoutController.setDefaultUrl("/");
		bind(ApplicationLogoutController.class).toInstance(logoutController);
	}
}