package by.epam.gym.command;

import by.epam.gym.command.client.*;
import by.epam.gym.command.common.*;
import by.epam.gym.command.joint.*;
import by.epam.gym.command.trainer.*;

/**
 * Factory class for creation commands.
 *
 * @author Dzmitry Turko
 * @see Command
 */
public class CommandFactory {
    private static final String LOGIN = "common_login";
    private static final String HOME = "common_home";
    private static final String LOG_OUT = "common_logOut";
    private static final String CHANGE_LANGUAGE = "common_changeLanguage";

    private static final String ORDER_INFO = "client_orderInfo";
    private static final String MY_PROGRAM = "client_myProgram";
    private static final String DO_PREPAYMENT = "client_doPrepayment";
    private static final String GO_TO_MAIN = "client_goToMain";
    private static final String LEAVE_FEEDBACK = "client_leaveFeedback";
    private static final String EXTEND_ORDER = "client_extendOrder";

    private static final String EDIT_CLIENT_INFO = "trainer_editClientInfo";
    private static final String GO_TO_ALL_CLIENTS = "trainer_goToAllClients";
    private static final String EDIT_BASIC_INFO = "trainer_editBasicInfo";
    private static final String DO_EDIT_BASIC_INFO = "trainer_doEditBasicInfo";

    private static final String MACHINES_INFO = "joint_machinesInfo";
    private static final String PREPARE_EDIT_COMPLEX = "joint_prepareEditComplex";
    private static final String EDIT_COMPLEX = "joint_editComplex";
    private static final String PREPARE_ADD_COMPLEX = "joint_prepareAddComplex";
    private static final String ADD_COMPLEX = "joint_addComplex";
    private static final String CLEAR_ALL = "joint_clearComplexes";

    /**
     * This method define command and return it's instance.
     *
     * @param action the value of parameter "command" at HttpServletRequest request.
     * @return the defined command.
     */
    public static Command getCommand(String action) {
        if (action == null || action.isEmpty()) {
            return new HomePageCommand();
        }

        switch (action) {
            case LOGIN:
                return new LoginCommand();
            case HOME:
                return new HomePageCommand();
            case MACHINES_INFO:
                return new MachinesInfoCommand();
            case ORDER_INFO:
                return new OrderInfoCommand();
            case MY_PROGRAM:
                return new MyProgramCommand();
            case LOG_OUT:
                return new LogOutCommand();
            case CHANGE_LANGUAGE:
                return new ChangeLanguageCommand();
            case DO_PREPAYMENT:
                return new DoPrepaymentCommand();
            case GO_TO_MAIN:
                return new GoToMainCommand();
            case LEAVE_FEEDBACK:
                return new LeaveFeedbackCommand();
            case EDIT_CLIENT_INFO:
                return new EditClientInfoCommand();
            case GO_TO_ALL_CLIENTS:
                return new GoToAllClientsCommand();
            case EXTEND_ORDER:
                return new ExtendOrderCommand();
            case PREPARE_EDIT_COMPLEX:
                return new PrepareEditComplexCommand();
            case EDIT_COMPLEX:
                return new EditComplexCommand();
            case EDIT_BASIC_INFO:
                return new EditBasicInfoCommand();
            case DO_EDIT_BASIC_INFO:
                return new DoEditBasicInfoCommand();
            case PREPARE_ADD_COMPLEX:
                return new PrepareAddComplexCommand();
            case ADD_COMPLEX:
                return new AddComplexCommand();
            case CLEAR_ALL:
                return new ClearComplexesCommand();

            default:
                throw new IllegalArgumentException("Unknown command in Command factory.");
        }
    }
}