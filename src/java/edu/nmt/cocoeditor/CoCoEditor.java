/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The serverlet itself. Contains all of the interface methods we set up.
 * @note this implementation is unsuited for actual deployment, since it
 *       ignores existing data in the database and assumes it's empty on
 *       startup. It should (design decision) load up all sessions on startup
 *       and use those. We didn't need that since sessions would assumedly die
 *       when the serverlet dies.
 * @author Skyler
 */
public class CoCoEditor extends HttpServlet {

    public static final int KEY_FETCH_TIMEOUT = 500;
    public static final int KEY_SESSION_LENGTH = 24;
    public static final int KEY_USER_LENGTH = 18;
    public static final int KEY_ACTION_LENGTH = 18;
    
    public static CoCoEditor instance;
    public static PrintWriter lastOut;
    
    private Map<String, ActiveSession> sessions;
    private Map<String, User> users;
    private Map<String, UserAction> actions;
    
    //private static void setInstance(CoCoEditor instance) {
    //    CoCoEditor.instance = instance;
    //}
    //public static CoCoEditor getInstance() {
    //    return instance;
    //}
    
    public static CoCoEditor instance() {
        if (instance == null) {
            instance = new CoCoEditor();
            //System.out.println("Making a NEW editor!");
        }
        
        return instance;
    }
    
    public static PrintWriter getLastOut(){
        return lastOut;
    }
    public static void setErrorOutput(PrintWriter p){
        lastOut = p;
    }
    public static void printError(String msg) {
        if (lastOut != null)
            lastOut.println("Encountered error: " + msg);
    }
    
    private static final SecureRandom S_RAND = new SecureRandom();
    private static char[] charset;
    
    private static String generateKey(int length) {
        if (charset == null) {
            charset = new char[62];
            int i = 0;
            for (char ch = '0'; ch < '9'; ch++) {
                charset[i] = ch;
                i++;
            }
            for (char ch = 'a'; ch < 'z'; ch++) {
                charset[i] = ch;
                charset[i+1] = Character.toUpperCase(ch);
                i+=2;
            }
        }
        
        
        String ret = "";
        while (length > 0) {
            ret += charset[S_RAND.nextInt(52)];
            length--;
        }
            
        return ret;
    }
    
    
    
    //Create page and Join page interface

    /**
     * Creates a new session, with no users. The session also has no text,
     * and no history. Returns it's ID
     * @return the session ID
     **/
    public static String createSession() {
        String id = generateKey(KEY_SESSION_LENGTH);
        int i = 0;
        while (DatabaseStatus.instance().hasSession(id)) {
           
            if (i > KEY_FETCH_TIMEOUT) {
                printError("Failed to generate unique session key in time");
                return null;
            }
            id = generateKey(KEY_SESSION_LENGTH);
            i++;
        }
        
        instance().addSession(id);
        
        return id;
    }

    /**
     * Forwards the user to a session, with the alias in the request
     * @param sessionID
     * @param alias the alias to enter the session with
     * @return the new user ID for the added user in the session
     **/
    public static String submit(String sessionID, String alias) {
        //check if session exists, then gen user and return id
        if (!DatabaseStatus.instance().hasSession(sessionID)) {
            printError("Could not locate session from submit id: " + sessionID);
            return null;
        }
        
        String userID = generateKey(KEY_USER_LENGTH);
        if (userID == null) {
            printError("Failed generating user ID");
            return null;
        }
        
        User user = new User(userID);
        user.submit(sessionID, alias);
        
        instance().addUser(userID, user);
        
        return userID;
    }





    //Edit page and statistics page interface

    /**
     * Sets the cursor position for the user.
     * @param session the httpsession for the connection
     * @param pos the new position
     **/
    public static void moveCursor(HttpSession session, int pos) {
        User user = getUser(session);
        if (user == null)
            return; 
        ActiveSession as = getSession(session);
        pos = Math.min(pos, as.getText().length());
        pos = Math.max(pos, 0);
        
        user.setPos(pos);
        //System.out.println("Setting pos to " + pos);
    }

    /**
     * Deletes some number of characters from the text, from the current position.
     * @param session the http session to use
     * @param sizeOfDeletion how many characters to delete. Deletes backwards
     **/
    public static void delete(HttpSession session, int sizeOfDeletion) {
        ActiveSession activesession = getSession(session);
        if (activesession == null)
            return;
        
        User user = getUser(session);
        if (user == null)
            return;        
        
        if (user.getPos() == 0)
            return; //cannot delete at pos 0
        
        StringBuilder dataText = new StringBuilder(activesession.getText());
        dataText.delete(user.getPos() - sizeOfDeletion, user.getPos());
        
        Date now = new Date(Calendar.getInstance().getTimeInMillis());
        //activesession.submit(dataText.toString(), now);
        activesession.updateText(dataText.toString());
        
        String key = generateKey(KEY_ACTION_LENGTH);
        UserAction action = new UserAction(
                key, user.getUserID(), sizeOfDeletion, "", now
        );
        instance().addAction(key, action);
        action.submit(user);
        
        user.addPos(-sizeOfDeletion); //move them back over deletion
    }

    /**
     * Adds text to the hosted text. The text added is as given, from
     * the user's position in the text
     * @param session the http session for the connection
     * @param text the text to add
     **/
    public static void addText(HttpSession session, String text) {
        
        //System.out.println("Adding text: " + text);
        ActiveSession activesession = getSession(session);
        if (activesession == null) {
            return;
        }
        User user = getUser(session);
        if (user == null)
            return;        
        StringBuilder dataText = new StringBuilder(activesession.getText());
        dataText.insert(user.getPos(), text);
        
        Date now = new Date(Calendar.getInstance().getTimeInMillis());
        activesession.updateText(dataText.toString());
        
        String key = generateKey(KEY_ACTION_LENGTH);
        UserAction action = new UserAction(
                key, user.getUserID(), text.length(), text, now
        );
        instance().addAction(key, action);
        action.submit(user);
        
        user.addPos(text.length()); //move them forward some amount of text
    }

    /**
     * Leaves the current session
     * @param session the http session for the connection
     **/
    public static void leave(HttpSession session) {
        
        User user = getUser(session);
        if (user == null)
            return;
        
        user.invalidate();
    }

//    /**
//     * Returns a link to the current session
//     * @return the link, in string form
//     **/
//    public String getLink();

    /**
     * Fetches the total text from the server
     * @param session the http session for the connection
     * @return the text from the server, e.g. the server's most recent version
     **/
    public static String fetchText(HttpSession session) {
        ActiveSession activesession = getSession(session);
        if (activesession == null)
            return null;
        
        return activesession.getText();
    }
//
//    /**
//     * Fetches statistics from the server, and updates the graphs
//     **/
//    public void refreshStatistics();

    
    
    // Intenral methods
    private static ActiveSession getSession(HttpSession session) {
        if (instance().sessions == null)
            instance().sessions = new HashMap<>();
        
        Object sid = session.getAttribute(
                AttributeNames.SESSION_ID.getKey()
        );
        
        if (sid == null || !(sid instanceof String)) {
            CoCoEditor.printError("Null sid attribute");
            return null;
        }
        
        //CoCoEditor.printError("sid: " + (String) sid);
        
        
        ActiveSession activesession = instance().sessions.get((String) sid);
        return activesession;
    }
    
    private static User getUser(HttpSession session) {
        if (instance().users == null)
            instance().users = new HashMap<>();
        
        Object uid = session.getAttribute(
                AttributeNames.USER_ID.getKey()
        );
        
        if (uid == null || !(uid instanceof String)) {
            return null;
        }
        
        User user = instance().users.get((String) uid);
        
        //extra check against invaid user
        if (user != null && !user.isValid())
            return null;
        
        return user; //including iff null
    }
    
    private void addSession(String sessionID) {
        if (sessions == null)
            sessions = new HashMap<>();
        
        ActiveSession session = new ActiveSession(sessionID);
        session.submit("", new Date(Calendar.getInstance().getTime().getTime()));
        sessions.put(sessionID, session);
    }
    
    private void addUser(String userID, User user) {
        if (users == null)
            users = new HashMap<>();
        
        users.put(userID, user);
    }
    
    private void addAction(String actionID, UserAction action) {
        if (actions == null)
            actions = new HashMap<>();
        
        actions.put(actionID, action);
    }
    
    /**
     * Checks whether the given session is valid and active. This
     * includes whether it has a user and session, and whether both
     * are still valid.
     * @param session
     * @return 
     */
    private boolean isValidSession(HttpSession session) {
        ActiveSession asession = getSession(session);
        User user = getUser(session);
        
        if (asession == null || user == null) {
            CoCoEditor.printError((asession == null ? "Session" : "User")
                    + " encountered error");
            return false;
        }
        
        if (!user.isValid()) {
            CoCoEditor.printError("User is invalid!");
            return false;
        }
        
        return true;
    }
    
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //setInstance(this);
        System.err.flush();
        response.setContentType("text/html;charset=UTF-8");
        
        CoCoEditor.setErrorOutput(response.getWriter());
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            //response.getWriter().println("<> <> <> Session is null");
            return;
        }
        
        //System.out.println("Handling request!");
        
        
        String requestedFile = request.getRequestURI();
        int index = requestedFile.lastIndexOf("/");
        requestedFile = requestedFile.substring(index + 1);
        
        //System.out.println("request: >" + requestedFile);
        
        if (requestedFile == null || requestedFile.trim().isEmpty())
            return;
        
        if (!isValidSession(session)) {
            CoCoEditor.printError("Invalid session");
            //System.out.println("Invalid session");
            return;
        }
        
        //System.out.println("valid session!");
        switch (requestedFile) {
            case "addText.xml": handleText(request, response); break;
            case "setPos.xml": handlePos(request, response); break;
            case "getText.xml": handleGetText(request, response); break;
            case "delete.xml": handleDelete(request, response); break;
            case "leave.xml": handleLeave(request, response); break;
            
            default:
                //System.out.println("Invalid request received: " + requestedFile);
                return;
        }
        
        //valid session, so process request depending on file requested
        
        //TODO
        
//        if (session.getAttribute(AttributeNames.USER_ID.getKey()) == null) {
//            //new user. Bounce to join pages
//            //directCreate(request, response);
//            return;
//        }
//        
//        //else, they have a user ID. Try to get their user cache
//        User user = users.get(
//                session.getAttribute(AttributeNames.USER_ID.getKey()).toString());
//        if (!user.isValid()) {
//            //have user, but it's invalid. Bounce to create
//            printError("Invalid user");
//            //directCreate(request, response);
//            return;
//        }
        
        //have valid user id. Move to edit page
        //directEdit(request, response);
        
        
//        try (PrintWriter out = response.getWriter()) {
//            //CoCoEditor.lastOut = out;
//            
//            out.println("Generated key: " + createSession());
//                        
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet CoCoEditor</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet CoCoEditor at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }
    
    private void handleText(HttpServletRequest request, HttpServletResponse response) {
        
        //valid session. Fetch text from request, and perform addText
        Object o = request.getParameter(AjaxAttributes.TEXT_TEXT.getKey());
        if (o == null || !(o instanceof String)) {
//            if (o == null)
//                //System.out.println("Invalid text attribute: null");
//            else
//                //System.out.println("Invalid attribute text: " + o.toString());
            return;
        }
        
        String text = (String) o;
        
        CoCoEditor.addText(request.getSession(), text);
    }
    
    private void handlePos(HttpServletRequest request, HttpServletResponse response) {
        
        //valid session. Fetch pos from request, and perform moveCursor
        Object o = request.getParameter(AjaxAttributes.MOVE_POS.getKey());
        if (o == null || !(o instanceof String)) {
//            if (o == null)
//                //System.out.println("Invalid text attribute: null");
//            else
//                //System.out.println("Invalid attribute pos: " + o.toString());
            return;
        }
        
        String pos = (String) o;
        
        CoCoEditor.moveCursor(request.getSession(), Integer.valueOf(pos));
    }
    
    private void handleGetText(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            response.getWriter().print(CoCoEditor.fetchText(request.getSession()));
        } catch (IOException e) {
            //System.out.println("Encountered IO Exception: ");
            //System.out.println(e.getMessage());
        }
        
    }
    
    private void handleDelete(HttpServletRequest request, HttpServletResponse response) {
        
        Object o = request.getParameter(AjaxAttributes.DELETE_COUNT.getKey());
        if (o == null || !(o instanceof String)) {
//            if (o == null)
//                //System.out.println("Invalid text attribute: null");
//            else
//                //System.out.println("Invalid attribute delete count: " + o.toString());
            return;
        }
        
        String len = (String) o;
        CoCoEditor.delete(request.getSession(), Integer.valueOf(len));
        
    }
    
    private void handleLeave(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        CoCoEditor.leave(session);
    }
    
//    private void directCreate(HttpServletRequest request, HttpServletResponse response) {
//        
////        int index = request.getRequestURL().indexOf("/CoCoEditor/");
////        String requestURL = request.getRequestURL().substring(
////                index + 12 //12 is size of /CoCoEditor/
////        );
////        System.out.println("query: [" + requestURL + "]");
////        //return;
//        
//        //clean up any session attributes we may have
//        HttpSession session = request.getSession();
//        session.removeAttribute(AttributeNames.USER_ID.getKey());
//        
//        String url;
//          
//
//        //request.setAttribute("sid", request.getParameter("sid"));
//        if(session.getAttribute(AttributeNames.SESSION_ID.getKey()) != null) {
//            //This is stored in the session now
//            //response.setAttribute("sid",request.getParameter("sid"));
//
//            //make sure it's an active session
//            if (!DatabaseStatus.instance().hasSession(
//                session.getAttribute(AttributeNames.SESSION_ID.getKey()).toString()
//            )) {
//                //has session key, but it's invalid.
//                //Clean it and bounce to create.
//                session.removeAttribute(AttributeNames.SESSION_ID.getKey());
//                url = "create.jsp";
//                //response.sendRedirect("./create.jsp");
//            } else
//                url = "join.jsp";
//                //response.sendRedirect("./join.jsp");
//        } else {
//            url = "create.jsp";
//            //response.sendRedirect("./create.jsp");
//        }
//        
////        if (requestURL.equalsIgnoreCase(url)) {
////            return;
////        }
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//        
//        try {
//            dispatcher.include(request, response);
//        } catch (ServletException|IOException e) {
//            printError("Failed with redirection.");
//            printError("<a href='./join.jsp'>Click here to be redirected</a>");
//        }
//    }
//    
//    private void directEdit(HttpServletRequest request, HttpServletResponse response) {
//        
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
