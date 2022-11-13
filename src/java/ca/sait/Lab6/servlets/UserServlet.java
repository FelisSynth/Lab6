package ca.sait.Lab6.servlets;

import ca.sait.Lab6.models.User;
import ca.sait.Lab6.models.Role;
import ca.sait.Lab6.services.UserService;
import ca.sait.Lab6.services.RoleService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author OS
 */
public class UserServlet extends HttpServlet {

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
        UserService userService = new UserService();
        RoleService roleService = new RoleService();

        try {
            List<User> users = userService.getAll();
            List<Role> roles = roleService.getAll();
            request.setAttribute("users",users);
            request.setAttribute("roles",roles);
            String tempEmail = "";
            String tempFirstName = "";
            String tempLastName = "";
            
            String action = request.getParameter("action");
            if (action != null && action.equals("edit")) {
            String userEmail = request.getParameter("user").replaceAll(" ", "+");
                for(int x = 0; x < users.size(); x++){
                    String check = users.get(x).getEmail();
                    if(userEmail.equals(check)){
                        tempEmail = users.get(x).getEmail();
                        tempFirstName = users.get(x).getFirstName();
                        tempLastName = users.get(x).getLastName();
                    }
                }    
            }
            else if(action != null && action.equals("delete")){
                String userEmail = request.getParameter("user").replaceAll(" ", "+");

                userService.delete(userEmail);

                response.sendRedirect("user");
                request.getSession().invalidate();
            }
            request.setAttribute("emailToEdit", tempEmail);
            request.setAttribute("editFirstName", tempFirstName);
            request.setAttribute("editLastName", tempLastName);
            this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request,response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
        UserService userService = new UserService();
        RoleService roleService = new RoleService();
        List<User> users;
        List<Role> roles = null;
        try {
            roles = roleService.getAll();
        }catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String action = request.getParameter("action");
        
        if(action.equals("add"))
            {
                String email = request.getParameter("addEmail");
                String firstName = request.getParameter("addFirst");
                String lastName = request.getParameter("addLast");
                String password = request.getParameter("addPassword");
                String roleName = request.getParameter("addRole");
                Role role = null;
                
                for(int x = 0; x < roles.size(); x++){
                    if(roles.get(x).getRoleName().equals(roleName)){
                        role = roles.get(x);
                    }
                }
                    

                try {
                    userService.insert(email, true, firstName, lastName, password, role);
                }catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        else if(action.equals("update"))
        {
                String email = request.getParameter("editEmail");
                String firstName = request.getParameter("editFirst");
                String lastName = request.getParameter("editLast");
                String password = request.getParameter("editPassword");
                String roleName = request.getParameter("editRole");
                
                Role role = null;
                boolean active;
                User user = null;

                  for(int x = 0; x < roles.size(); x++)
                {
                    if(roles.get(x).getRoleName().equals(roleName))
                    {
                        role = roles.get(x);
                    }
                }
                  
            try {
                user = userService.get(email);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            active = user.getActive();
            
            try {
                userService.update(email, active, firstName, lastName, password, role);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           
        request.getSession().invalidate();
        response.sendRedirect("user");
    }

}
