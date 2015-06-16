package util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import models.BaseEntity;
import play.Logger;
import play.data.Form;
import static play.data.Form.form;
import play.data.validation.ValidationError;
import play.libs.Json;

/**
 *
 * @author pedro
 */
public class JsonUtil {

    public static Optional<ObjectNode> validate(BaseEntity entity) {

        Form<? extends BaseEntity> form = form(entity.getClass()).bind(entity.toJson());
        if (form.hasErrors()) {
            Map<String, List<ValidationError>> errors = form.errors();
            ObjectNode errorsNode = Json.newObject();

            errors.keySet().stream().forEach((key) -> {
                List<ValidationError> listValidationErrors = errors.get(key);
                if (listValidationErrors != null && !listValidationErrors.isEmpty()) {
                    listValidationErrors.stream().forEach((validationError) -> {
                        errorsNode.put(key, validationError.message());
                    });
                }
            });

            //I don't use form.errorsAsJson() because it needs an HTTP Context to work
//            return Optional.of(form.errorsAsJson());
            Logger.error("[" + errorsNode + "] = errorsNode");
            return Optional.of(errorsNode);
        }

        return Optional.empty();
    }

}
