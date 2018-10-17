package by.epam.gym.model;

/**
 * Class describes model of page with url and redirect parameter.
 *
 * @author Dzmitry Turko
 * @see AbstractEntity
 */
public class Page {
    private String url;
    private boolean toRedirect;

    /**
     * Instantiates a new Entity.
     *
     * @param url        is url of entity.
     * @param toRedirect is forwarding information of entity.
     */
    public Page(String url, boolean toRedirect) {
        this.url = url;
        this.toRedirect = toRedirect;
    }

    /**
     * Instantiates a new Entity.
     *
     * @param url is url of entity.
     */
    public Page(String url) {
        this.url = url;
    }

    /**
     * Instantiates a new Entity.
     */
    public Page() {
    }

    /**
     * Gets entity's url.
     *
     * @return the entity's url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets entity's url.
     *
     * @param url the entity's url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets entity's boolean parameter if it was redirected.
     *
     * @return the entity's toRedirect.
     */
    public boolean isToRedirect() {
        return toRedirect;
    }

    /**
     * Sets entity's boolean parameter if it was redirected.
     *
     * @param toRedirect the entity's boolean redirect parameter.
     */
    public void setToRedirect(boolean toRedirect) {
        this.toRedirect = toRedirect;
    }

}
