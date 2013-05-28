package uk.co.coen.capsulecrm.client;

import com.thoughtworks.xstream.io.xml.DomReader;
import org.joda.time.DateTime;
import play.libs.F;
import play.libs.WS;

/**
 * @author Mathias Bogaert
 */
public abstract class CapsuleEntity extends SimpleCapsuleEntity {
    public DateTime createdOn;
    public DateTime updatedOn;

    public F.Promise<CCustomFields> listCustomFields() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CCustomFields>() {
                    @Override
                    public CCustomFields apply(WS.Response response) throws Throwable {
                        return (CCustomFields) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> add(final CCustomField customField) {
        if (customField.id != null) {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield/" + customField.id)
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "")
                    .put(xstream.toXML(customField));
        } else {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield")
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "")
                    .post(xstream.toXML(customField)).map(new F.Function<WS.Response, WS.Response>() {
                        @Override
                        public WS.Response apply(WS.Response response) throws Throwable {
                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to custom field " + this + ", status is " + response.getStatus() + " " + response.getStatusText());
                            }
                            customField.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));

                            return response;
                        }
                    });
        }
    }

    public F.Promise<WS.Response> remove(CCustomField customField) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/customfield/" + customField.id)
                .setAuth(capsuleToken, "")
                .delete();
    }

    public F.Promise<CHistory> listHistory() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CHistory>() {
                    @Override
                    public CHistory apply(WS.Response response) throws Throwable {
                        return (CHistory) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> add(final CHistoryItem item) {
        if (item.id != null) {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history/" + item.id)
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "")
                    .put(xstream.toXML(item));
        } else {
            return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history")
                    .setHeader("Content-Type", "text/xml; charset=utf-8")
                    .setAuth(capsuleToken, "")
                    .post(xstream.toXML(item)).map(new F.Function<WS.Response, WS.Response>() {
                        @Override
                        public WS.Response apply(WS.Response response) throws Throwable {
                            // extract ID from location
                            String location = response.getHeader("Location");
                            if (location == null) {
                                throw new RuntimeException("null location, cannot assign id to history item " + this + ", status is " + response.getStatus() + " " + response.getStatusText());
                            }
                            item.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));

                            return response;
                        }
                    });
        }
    }

    public F.Promise<WS.Response> remove(final CHistoryItem item) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/history/" + item.id)
                .setAuth(capsuleToken, "")
                .delete();
    }

    public F.Promise<WS.Response> add(CTag tag) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tag/" + tag.name)
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .post(xstream.toXML(tag));
    }

    public F.Promise<WS.Response> remove(CTag tag) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tag/" + tag.name)
                .setAuth(capsuleToken, "")
                .delete();
    }

    public F.Promise<CTasks> listTasks() {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/tasks")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .get().map(new F.Function<WS.Response, CTasks>() {
                    @Override
                    public CTasks apply(WS.Response response) throws Throwable {
                        return (CTasks) xstream.unmarshal(new DomReader(response.asXml()));
                    }
                });
    }

    public F.Promise<WS.Response> add(final CTask task) {
        return WS.url(capsuleUrl + "/api" + readContextPath() + "/" + id + "/task")
                .setHeader("Content-Type", "text/xml; charset=utf-8")
                .setAuth(capsuleToken, "")
                .post(xstream.toXML(task)).map(new F.Function<WS.Response, WS.Response>() {
                    @Override
                    public WS.Response apply(WS.Response response) throws Throwable {
                        // extract ID from location
                        String location = response.getHeader("Location");
                        if (location == null) {
                            throw new RuntimeException("null location, cannot assign id to task " + this + ", status is " + response.getStatus() + " " + response.getStatusText());
                        }
                        task.id = Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));

                        return response;
                    }
                });
    }
}