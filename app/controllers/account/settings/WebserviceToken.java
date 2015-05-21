package controllers.account.settings;

import controllers.Secured;
import models.Token;
import models.User;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.net.MalformedURLException;

/**
 * Created by michal on 21.5.2015.
 */
@Security.Authenticated(Secured.class)
public class WebserviceToken  extends Controller {


    public static Result index() {
        return ok(views.html.account.settings.token.render(User.findByEmail(request().username())));
    }

    /**
     * Send a mail with the reset link.
     *
     * @return password page with flash error or success
     */
    public static Result runToken() {
        User user = User.findByEmail(request().username());
        try {
            user.changeWebserviceToken();
            flash("success", "New confirmation token was succesfully genereted");
            return ok(views.html.account.settings.token.render(user));
        } catch (Exception e) {
            Logger.error("Cannot validate URL", e);
            flash("error", Messages.get("error.technical"));
        }
        return badRequest(views.html.account.settings.token.render(user));
    }

}
