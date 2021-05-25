package net.xdclass.online_xdclass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.xdclass.online_xdclass.utils.JWTUtils;
import net.xdclass.online_xdclass.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 进入contronller之前的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String acceptToken = request.getHeader("token");
            if (acceptToken == null){
                acceptToken = request.getParameter("token");
            }
            if (StringUtils.isNotBlank(acceptToken)){
                Claims claims = JWTUtils.checkJWT(acceptToken);
                if (claims == null){
                    //告诉登录过期，重新登录
                    sendJsonMessage(response, JsonData.buildError("登录过期，请重新登录"));
                    return false;
                }else {
                    Integer id = (Integer)claims.get("id");
                    String name = (String)claims.get("name");
                    request.setAttribute("user_id",id);
                    request.setAttribute("user_name",name);
                    return true;
                }
            }
        }catch (Exception e){

        }
        sendJsonMessage(response, JsonData.buildError("登录过期，请重新登录"));
        return false;
    }

    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=Utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
