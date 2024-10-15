
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressBookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 确保使用UTF-8字符编码
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        List<Contact> contacts = (List<Contact>) session.getAttribute("contacts");

        if ("add".equals(action)) {
            // 添加联系人
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");

            if (contacts == null) {
                contacts = new ArrayList<>();
                session.setAttribute("contacts", contacts);
            }

            // 生成唯一 ID
            String id = String.valueOf(contacts.size() + 1);
            contacts.add(new Contact(id, name, phone));
            response.sendRedirect("index.html");

        } else if ("update".equals(action)) {
            // 修改联系人
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");

            if (contacts != null) {
                for (Contact contact : contacts) {
                    if (contact.getId().equals(id)) {
                        contact.setName(name);
                        contact.setPhone(phone);
                        break;
                    }
                }
            }
            response.sendRedirect("index.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        List<Contact> contacts = (List<Contact>) session.getAttribute("contacts");

        if ("delete".equals(action)) {
            // 删除联系人
            String id = request.getParameter("id");

            if (contacts != null) {
                Iterator<Contact> iterator = contacts.iterator();
                while (iterator.hasNext()) {
                    Contact contact = iterator.next();
                    if (contact.getId().equals(id)) {
                        iterator.remove();
                        break;
                    }
                }
            }
            response.sendRedirect("index.html");

        } else {
            // 获取联系人列表
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("[");
            if (contacts != null) {
                for (int i = 0; i < contacts.size(); i++) {
                    Contact contact = contacts.get(i);
                    out.print("{\"id\":\"" + contact.getId() + "\", \"name\":\"" + contact.getName() + "\", \"phone\":\"" + contact.getPhone() + "\"}");
                    if (i < contacts.size() - 1) {
                        out.print(",");
                    }
                }
            }
            out.print("]");
            out.flush();
        }
    }
}
