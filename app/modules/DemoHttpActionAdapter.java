package modules;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import play.mvc.Result;

import static play.mvc.Results.*;

public class DemoHttpActionAdapter extends DefaultHttpActionAdapter {

	@Override
	public Result adapt(int code, PlayWebContext context) {
		if (code == HttpConstants.UNAUTHORIZED) {
			// セッションを削除
			context.getJavaContext().session().remove(Pac4jConstants.SESSION_ID);
			return unauthorized("401 UNAUTHORIZED");
		} else if (code == HttpConstants.FORBIDDEN) {
			return forbidden("403 FORBIDDEN");
		} else {
			return super.adapt(code, context);
		}
	}
}