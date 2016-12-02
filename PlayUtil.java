package util;

import java.util.Optional;
import play.Configuration;
import play.api.Play;
import play.mvc.Http;

/**
 * This class is abstract with all the methods static but as can be seen on the
 * sources IS USING DEPENDENCY INJECTION.
 * Note: This class has been tested with Play 2.5
 * @author pedro
 */
public abstract class PlayUtil {

    /**
     * Get the Dependency Injected Class
     *
     * @param <T>
     * @param type
     * @return
     */
    public static <T extends Object> T getDIClass(Class<T> type) {
        return Play.current().injector().instanceOf(type);
    }

    /**
     * get configuration value
     * @param key the key
     * @return an optional with the value if it exists, otherwise an Optional empty.
     */
    public static Optional<String> getConfValue(String key) {
        Configuration configuration = getDIClass(Configuration.class);
        
        return Optional.ofNullable(configuration.getString(key));
    }
    
    /**
     * Returns the value to which the specified key is mapped, 
     * or Optional.empty() if this map contains no mapping for the key.
     * @param key
     * @return 
     */
    public static Optional<Object> getValueFromContext(String key) {
        return Optional.ofNullable(Http.Context.current().args.get(key));
    }
}
