Usefull play 2 code snippets
============================================

<h3>JSON Validation</h3>

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
            Logger.error("[" + errorsNode + "] = errorsNode");
            return Optional.of(errorsNode);
        }

        return Optional.empty();
    }

Usage:

in play controller:

    JsonNode jsonBody = request().body().asJson();

    Employee employee = new Employee();
    employee.firstName = jsonBody.findPath("firstName").textValue();
    employee.lastName = jsonBody.findPath("lastName").textValue();

    Optional<ObjectNode> hasValidationErrorOptional = JsonUtil.validate(employee);

    if (hasValidationErrorOptional.isPresent()) {
        //TODO your code here
    }else{
        employee.save();
    }

------------------------------------------------
