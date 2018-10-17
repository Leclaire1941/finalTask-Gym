package by.epam.gym.tags;

import by.epam.gym.model.user.UserRole;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom tag for display user's user role and show current day.
 *
 * @author Dzmitry Turko
 */
public class WelcomeTag extends TagSupport {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_MESSAGE = "Welcome";
    private UserRole userRole;

    /**
     * Sets user's role.
     *
     * @param userRole the user's role.
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * This method starts tag.
     *
     * @return int constant SKIP_BODY.
     * @throws JspException object if execution of method is failed.
     */
    @Override
    public int doStartTag() throws JspException {
        String date = getCurrentDate();
        String welcomeMessage = getMessage(date);

        try {
            JspWriter writer = pageContext.getOut();
            writer.write(welcomeMessage);
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /**
     * Method defines concrete welcome message and return it.
     *
     * @param date the current date.
     * @return String.
     */
    private String getMessage(String date) {
        String welcomeMessage = DEFAULT_MESSAGE;
        switch (userRole) {
            case CLIENT:
                welcomeMessage = "Welcome dear " + userRole + ". Today is " + date;
                break;
            case TRAINER:
                welcomeMessage = "Hello " + userRole + "! Today is " + date;
                break;
        }
        return welcomeMessage;
    }

    /**
     * Method defines current date in a needed format and returns it as a String.
     *
     * @return String.
     */
    private String getCurrentDate() {
        Date fullDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(fullDate);
    }
}