<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
--%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GraduationDesign_KLT_Server</title>
         <style>
            /*
            css
            选择器{声明}

            层级选择器
                > 儿子元素
                空格 后代选择器  关联选择器
            
                + 紧跟着的兄弟元素

                ~ 后面的兄弟元素
                */
                /*div>p>i{color:red;}*/
               /* div p i{color:red;}
                div+p{color:blue;}*/
                div~p{color:green;}
                div~div {color:yellow;}
                
        </style>
    </head>
    <body>
 
    <div>
            <p>GraduationDesign_KLT_Server!</p>
           
        <div>
          GraduationDesign_KLT_Server!
        </div>
   
    <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
    <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
   
    </body>
</html>