Usefull play 2 code snippets
============================================

<h3>Log Incoming Requests</h3>

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

------------------------------------------------
