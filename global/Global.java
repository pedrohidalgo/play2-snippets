
import java.lang.reflect.Method;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;

/**
 *
 * @author pedro
 */
public class Global extends GlobalSettings {

    @Override
    public Action onRequest(Http.Request rqst, Method method) {

        StringBuilder infoToLog = new StringBuilder();

        infoToLog.append(rqst.method())
                .append(" ").append(rqst.path())
                .append(" ").append(rqst.queryString())
                .append(" ").append(rqst.body());

        Logger.info(infoToLog.toString());

        return super.onRequest(rqst, method);
    }

}
