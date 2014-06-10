package com.ipz2014.server;

import com.google.gson.Gson;
import com.ipz2014.server.responses.GetUserFeedbackResponse;
import com.ipz2014.server.storage.DatastoreOperations;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class GetUserFeedback extends HttpServlet {

    private static final Logger log = Logger.getLogger(GetUserFeedback.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String email = (String) req.getParameter("email");

        log.info("GetUserFeedback - GET [email=" + email + "]");

        GetUserFeedbackResponse result = new GetUserFeedbackResponse();

        DatastoreOperations bl = new DatastoreOperations();

        if (email != null) {
            String feedback = bl.getFeedbackByEmail(email);
            if (!feedback.equals("")) {
                result.setData(feedback);
                result.success = true;
                result.errorCode = 0;
            } else {
                result.errorCode = 1;
                result.setData("Feedback not found for this Email :-<");
            }
        } else {
            result.errorCode = 2;
            result.setData("You forget send email parameter");
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(result, GetUserFeedbackResponse.class).toString();

        log.info("GetUserFeedback - GET [json=" + jsonString +"]");

        resp.setContentType("application/x-www-form-urlencoded; charset=utf-8");
        resp.getWriter().println(jsonString);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = (String) req.getParameter("email");
        String feedback = (String) req.getParameter("feedback");

        log.info("GetUserFeedback - POST [email=" + email + ", feedback=" + feedback + "]");

        GetUserFeedbackResponse result = new GetUserFeedbackResponse();

        DatastoreOperations bl = new DatastoreOperations();

        if (email != null) {
            if (!bl.isUserExist(email)) {
                bl.addNewFeedback(email, feedback);
                result.setData("We added your feedback! Thank you! :-)");
                result.success = true;
                result.errorCode = 0;
                //Add new user
                bl.addNewUser(email);
            } else {
                result.errorCode = 1;
                result.setData("You cannot leave a feedback twice! Sorry :-<");
            }
        } else {
            result.errorCode = 2;
            result.setData("You forget send email parameter");
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(result, GetUserFeedbackResponse.class).toString();

        log.info("GetUserFeedback - POST [json=" + jsonString +"]");

        resp.setContentType("application/x-www-form-urlencoded; charset=utf-8");
        resp.getWriter().println(jsonString);

    }
}