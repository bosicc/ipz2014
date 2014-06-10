package com.ipz2014.server.storage;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class DatastoreOperations {
    
    private static final Logger log = Logger.getLogger(DatastoreOperations.class.getName());
    
    private DatastoreService datastore;
    private Gson gson;
    
    public DatastoreOperations() {
        this.datastore =  DatastoreServiceFactory.getDatastoreService();
        this.gson = new Gson();
    }
    
    /**
     * Check if user login present in storage
     * @param login
     * @return
     */
    public boolean isUserExist(String login) {
        Key userKey = KeyFactory.createKey(DataStoreParams.USERS, login);
        boolean result = false;
        try {
            Entity user = datastore.get(userKey);
            result = true;
        } catch (EntityNotFoundException e) {
        }
        return result;
    }

    /**
     * Add new Feedback from user
     * @param email
     * @param feedback
     * @return
     */
    public Key addNewFeedback(String email, String feedback) {
        Date date = new Date();
        Entity entity = new Entity(DataStoreParams.FEEDBACK, email);
        entity.setProperty(DataStoreParams.EMAIL, email);
        entity.setProperty(DataStoreParams.DATA, feedback);
        entity.setProperty(DataStoreParams.DATE, date);
        return datastore.put(entity);
    }

    /**
     * Add new User
     * @param email
     * @return
     */
    public Key addNewUser(String email) {
        Date date = new Date();
        Entity entity = new Entity(DataStoreParams.USERS, email);
        entity.setProperty(DataStoreParams.DATE, date);
        return datastore.put(entity);
    }

    /**
     * Get user's feedback by email
     * @param email
     * @return
     */
    public String getFeedbackByEmail(String email) {
        String result = "";
        Query.Filter filter = new Query.FilterPredicate(DataStoreParams.EMAIL, Query.FilterOperator.EQUAL, email);
        Query query = new Query(DataStoreParams.FEEDBACK).setFilter(filter);
        List<Entity> list = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));

        log.info("getFeedbackByEmail - [list.sizeList=" + list.size() +"]");
        if (list.size() > 0) {
            Entity user = list.get(0);
            log.info("getUserLogin - [getProperties=" + user.getProperties().toString() +"]");
            result = (String) user.getProperty(DataStoreParams.DATA);
        }

        return result;
    }
}
