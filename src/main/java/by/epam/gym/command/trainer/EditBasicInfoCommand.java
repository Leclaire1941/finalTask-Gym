package by.epam.gym.command.trainer;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import by.epam.gym.model.program.ProgramNutrition;
import by.epam.gym.service.OrderService;
import by.epam.gym.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Command to prepare information about user's basic data.
 *
 * @author Dzmitry Turko
 * @see OrderService
 * @see UserService
 */
public class EditBasicInfoCommand implements Command {
    private static final String EDIT_BASIC_INFO_PAGE = "/WEB-INF/jsp/trainer/editBasicInfo.jsp";
    private static final String PROGRAM_ID_ATTRIBUTE = "programId";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";
    private static final String NUTRITION_LIST_ATTRIBUTE = "nutritionList";
    private static final String CURRENT_DATE_ATTRIBUTE = "date";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String MAX_DATE_FOR_PROGRAM_ATTRIBUTE = "maxDateForProgram";
    private static final String DISCOUNT_ATTRIBUTE = "discount";

    /**
     * Implementation of command to to prepare information about user's basic data.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String clientId = request.getParameter(CLIENT_ID_ATTRIBUTE);
        request.setAttribute(CLIENT_ID_ATTRIBUTE, clientId);

        String currentDate = getCurrentDate(request);
        request.setAttribute(CURRENT_DATE_ATTRIBUTE, currentDate);

        String programId = request.getParameter(PROGRAM_ID_ATTRIBUTE);
        request.setAttribute(PROGRAM_ID_ATTRIBUTE, programId);
        Date maxDateForProgram = getOrderExpirationDate(Integer.parseInt(programId));
        request.setAttribute(MAX_DATE_FOR_PROGRAM_ATTRIBUTE, maxDateForProgram);

        List<ProgramNutrition> nutritionList = getAllNutritions();
        request.setAttribute(NUTRITION_LIST_ATTRIBUTE, nutritionList);

        int discount = getClientDiscount(Integer.parseInt(clientId));
        request.setAttribute(DISCOUNT_ATTRIBUTE, discount);

        return new Page(EDIT_BASIC_INFO_PAGE);
    }

    /**
     * The method finds user's discount value by user ID and returns value.
     *
     * @param clientId the client's ID.
     * @return int.
     * @throws ServiceException object if execution of method is failed.
     */
    private int getClientDiscount(int clientId) throws ServiceException {
        UserService userService = new UserService();
        return userService.findClientDiscount(clientId);
    }

    /**
     * The method finds order's expiration date by program ID and returns date.
     *
     * @param programId the program's ID.
     * @return Date.
     * @throws ServiceException object if execution of method is failed.
     */
    private Date getOrderExpirationDate(int programId) throws ServiceException {
        OrderService orderService = new OrderService();

        return orderService.findOrderExpirationDateByProgramId(programId);
    }

    /**
     * The method defines current date and returns it's String.
     *
     * @param request HttpServletRequest object.
     * @return String.
     */
    private String getCurrentDate(HttpServletRequest request) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateFormat.format(date);
    }

    /**
     * The method adds all types of nutrition to list and return it.
     *
     * @return List.
     */
    private List<ProgramNutrition> getAllNutritions() {
        List<ProgramNutrition> nutritionList = new ArrayList<>();
        nutritionList.add(ProgramNutrition.DIET);
        nutritionList.add(ProgramNutrition.MASS_GAINER);
        return nutritionList;
    }

}

